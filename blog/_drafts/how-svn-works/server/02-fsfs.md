## FSFS文件系统目录结构

FSFS是建立在OS文件系统之上的，因此db目录（和子目录）里放的都是普通的文件。
这些文件大部分都可以用文本编辑器打开，但千万不要直接编辑！
按照相关性，子目录和文件大致可以分为四类，下面进行介绍。

### FSFS信息

```
myrepo/
  |-db/
    |-current
    |-format
    |-fsfs.conf
    |-min-unpacked-rev
    |-uuid
```

FSFS相关的一些信息分散在五个文件里。
format文件里放的是FSFS格式号和选项，我们打开它看一下：

```
6
layout sharded 1000
```

6是FSFS格式号。格式号和可以理解它们的svn版本号的对应关系如下表所示，更详细的介绍请看[这里](http://serverfault.com/questions/277441/difference-between-the-format-and-db-format-files-in-a-subversion-repository)和[这里](http://svn.apache.org/repos/asf/subversion/trunk/subversion/libsvn_fs_fs/structure)。

| FSFS格式号 | svn版本号  |
| --------- |:--------- |
| 1         | 1.1+      |
| 2         | 1.4+      |
| 3         | 1.5+      |
| 4         | 1.6+      |
| 5         | 1.7-dev   |
| 6         | 1.8+      |
| 7         | 1.9+      |

格式6只有一个layout选项，可选值是sharded或者linear。
如果是sharded，后面还需要跟一个参数，表示每个shard里最多可以放多少个版本文件。
后面介绍revs和revprops目录的时候，会进一步解释layout选项。
格式7增加了一个addressing选项，可选值是physical或者logical。

fsfs.conf是FSFS配置文件，在里面可以配制缓存等选项。
这个文件里有详细的注释，这里就不解释了。

uuid文件里放的是repository的[UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier)（Universally Unique IDentifier），这个UUID主要是提供给svn客户端使用的。
可以使用`svnlook uuid`命令查看repository的UUID；使用`svnadmin setuuid`命令修改repository的UUID。
关于UUID的更多介绍，请看[svnbook](http://svnbook.red-bean.com/en/1.8/svn.reposadmin.maint.html#svn.reposadmin.maint.uuids)。
[这篇文章](http://tortoisesvn.net/logcacheuuids.html)介绍了TortoiseSVN的日志缓存和UUID是如何配合使用的。
myrepo的UUID是`9f4743fc-0d7f-4055-924f-210f3cf9ed31`。

current文件里放的是repository的当前版本号（revision number，后面简称revnum）。
当前版本号从0开始，每一次提交都会导致该版本号加一。
min-unpacked-rev文件指出，从哪个版本开始，版本文件还没有被pack。
后面介绍shard packing的时候，会进一步解释这个文件。

### Transaction

```
myrepo/
  |-db/
    |-transactions/
    |-txn-protorevs/
    |-txn-current
    |-txn-current-lock
    |-write-lock
```

svn必须保证每次提交都是原子（Atomic）操作。
比如说某次提交修改了3个文件，添加了1个文件，并且删除了2个文件。
svn必须保证对这6个文件的改动要么全部生效，要么全部作废，否则版本库就会乱掉。
为了强调这种原子性，svn把提交过程叫做transaction。

svn会给每次提交（也就是每个transaction）分配一个唯一id，后面简称txnid。
txnid是一个36进制整数（使用了0到9和a到z这36个字符），从0开始递增。
txn-current文件里放的是下一次提交的txnid，刚开始的时候是0。
换句话说，第一次提交的txnid是0。
每次提交开始的时候，svn服务器都会先锁住txn-current-lock文件，把里面的txnid加一，再解锁该文件。
即使某次提交失败，也不会重复使用txnid。

每次提交会在transactions目录下创建一个名叫${revnum}-${txnid}.txn的子目录，并在这个子目录里创建一些文件。
同时，还会在txn-protorevs目录下创建${revnum}-${txnid}.rev和${revnum}-${txnid}.rev-lock两个文件。
假设提交开始前revnum（在current文件里）是3，txnid（在txn-current文件里）是x，那么transactions里创建的子目录就是3-x.txn，txn-protorevs里出现的两个文件分别是3-x.rev和3-x.rev-lock。
这些目录和文件都是临时使用的，提交结束（无论成功与否）后就会被svn清理掉。
它们的用处我们后面再详细讨论。

如果提交可以顺利完成，svn会在revs和revprops目录下各生成一个版本文件，然后修改current文件，把当前版本号加一。
很显然，虽然多个客户端可以同时发起提交，但是最后这一步必须串行进行，否则就会出问题。
这个串行是由write-lock文件保证的。
svn服务器必须先锁住这个文件，完成提交，然后解锁这个文件。

### 版本数据

```
myrepo/
  |-db/
    |-revprops/
      |-0/
        |-0
    |-revs/
      |-0/
        |-0
```

svn在自己的文件系统里存放的是增量数据，也就是版本之间的差异。
每次成功提交都会生成两个文件，文件名就是版本号，没有后缀。
一个文件记录和上一个版本的差异，放在revs目录里。
另一个是属性文件，记录提交日期、注释等信息，放在revprops目录里。
具体的文件内容将在后面介绍。

在格式3（svn1.5）之前，这些文件是直接放在各自目录下面的。
[svn1.5](http://subversion.apache.org/docs/release-notes/1.5.html#fsfs-sharding)进行了优化，引入了分区机制。
format文件里的layout选项指出是否启用分区，sharded表示启用，linear表示不启用。
启用分区之后，版本文件会分文件夹放置。
分区文件夹从0开始编号，一个满了再用另一个。
因为我们的layout选项是`sharded 1000`，还没有进行提交，所以db目录长上面那个样子。
注意，版本0是创建repository的时候自动生成的。
假设已经进行了1000次提交，那么这时db目录应该是下面这样：

```
myrepo/
  |-db/
    |-revprops/
      |-0/
        |-0
          ...
        |-999
      |-1/
        |-1000
    |-revs/
      |-0/
        |-0
          ...
        |999
      |-1/
        |1000
```
