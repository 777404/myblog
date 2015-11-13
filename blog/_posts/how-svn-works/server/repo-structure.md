## Repository目录结构

### 创建测试目录

svn软件分客户端和服务器两部分，相应的，svn管理的文件也分为两部分。
由服务器管理的目录结构叫做repository，由客户端管理的目录结构叫做working copy。
我们先创建下面这样一个目录结构，以方便后面的讨论。

```
how-svn-works
  |-client/
  |-server/
```

### 创建repository

`cd`到server目录，在这里执行`svnadmin create myrepo`命令创建一个repository。
命令执行完之后，server目录便下会出现一个叫做myrepo的目录结构，如下所示：

```
how-svn-works
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

下面我们仔细讨论一下这个目录结构。
