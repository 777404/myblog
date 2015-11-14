## Repository目录结构

### 创建测试目录

svn软件分客户端和服务器两部分，相应的，svn管理的文件也分为两部分。
由服务器管理的目录结构叫做repository，由客户端管理的目录结构叫做working copy。
我们先创建下面这样一个目录结构，以方便后面的讨论。

```
how-svn-works/
  |-client/
  |-server/
```

### 创建repository

`cd`到server目录，在这里执行`svnadmin create myrepo`命令创建一个repository。
命令执行完之后，server目录便下会出现一个叫做myrepo的目录结构，如下所示：

```
how-svn-works/
  |-client/
  |-server/
    |-myrepo/
      |-conf/
      |-db/
      |-hooks/
      |-locks/
      |-format
      |-README.txt
```

### Repository内容

README.txt告诉我们myrepo是一个svn repository，不要直接修改这个目录里面的文件，否则可能会把它搞坏！
> This is a Subversion repository; use the 'svnadmin' and 'svnlook' 
> tools to examine it.  Do not add, delete, or modify files here 
> unless you know how to avoid corrupting the repository.
>
> Visit http://subversion.apache.org/ for more information.

format文件里放的是repository的版本号，这个版本号规定了repository长什么样子。

* 版本号0、1、2在svn1.0之前使用
* 版本号3由svn1.0到1.3使用
* 版本号4只在开发中用过，从未正式使用
* 版本号5是svn1.4引入的，一直使用到现在（1.9）

因为我用的是的svn1.8，所以format文件里放的是数字5。
注意，db目录下也有一个format文件，但里面放的是svn文件系统的版本号（后面会介绍），千万不要把这两个版本号搞混了。
关于这两个format文件区别的详细回答，请参考[这个问题](http://serverfault.com/questions/277441/difference-between-the-format-and-db-format-files-in-a-subversion-repository)。

locks目录下有两个文件，db.lock和db-logs.lock。
这个目录是为了兼容svn1.2或更老的版本才放在这里的，从svn1.3开始就没用了。
两个lock文件的内容一模一样：
> This file is not used by Subversion 1.3.x or later.
> However, its existence is required for compatibility with
> Subversion 1.2.x or earlier.

hooks目录里放的是hook脚本模版，conf目录里主要放的是svnserve相关的配置文件，我们暂时先不讨论这两个目录。

### Repository文件系统

myrepo里还剩下一个目录没有介绍，db，我们展开来看一下这个目录。

```
myrepo/
  |-db/
    |-revprops/
    |-revs/
    |-transactions/
    |-txn-protorevs/
    |-current
    |-format
    |-fs-type
    |-fsfs.conf
    |-min-unpacked-rev
    |-txn-current
    |-txn-current-lock
    |-uuid
    |-write-lock
```

操作系统使用文件系统（File System）来存放文件和目录，于此类似，svn也有自己的文件系统。
本文后面提到的文件系统，在没有特殊说明的情况下，均指svn reposotiry filesystem，请不要把它和操作系统文件系统搞混。

svn文件系统是个抽象的概念，具体可以有不同的实现。
最开始的时候，文件系统是用Berkeley DB实现的，简写做BDB。
[svn1.1](https://subversion.apache.org/docs/release-notes/1.1.html)
实现了一个新的文件系统，这个实现不是基于数据库的，而是直接使用操作系统的文件系统，所以简写成FSFS。
[svn1.2](https://subversion.apache.org/docs/release-notes/1.2.html#fsfs)
把FSFS变成了默认的文件系统实现。
也就是说，在使用`svnadmin create`命令创建repository的时候，默认创建出来的就是FSFS类型的repository。
可以加上`--fs-type bdb`选项来创建BDB类型的repository。
[svn1.8](https://subversion.apache.org/docs/release-notes/1.8.html#bdb-deprecated)
给BDB打上了deprecated标签，会慢慢停止维护。
[svn1.9](https://subversion.apache.org/docs/release-notes/1.9.html#fsx)
引入了一个新的文件系统实现，叫做FSX，但是还不稳定，不建议用在生产环境中。

fs-type这个文件记录了repository用的是哪种类型的文件系统，可以打开看一下，里面写的是fsfs。
