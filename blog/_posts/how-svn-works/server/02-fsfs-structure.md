## FSFS文件系统目录结构

FSFS是建立在OS文件系统之上的，因此db目录（和子目录）里放的都是普通的文件。
这些文件大部分都可以用文本编辑器打开，但千万不要直接编辑！
按照相关性，子目录和文件大致可以分为四类，下面进行介绍。

### FSFS信息

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

fsfs.conf是FSFS配置文件，
min-unpacked-rev
current
uuid

### Transaction

transactions/
txn-protorevs/
txn-current
txn-current-lock
write-lock

### 版本数据
revs
revprops

### svn锁
locks