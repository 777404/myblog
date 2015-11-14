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

### repository内容

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
