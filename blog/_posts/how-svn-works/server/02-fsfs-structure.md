## Repository文件系统

### db目录结构

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

### 文件系统

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
