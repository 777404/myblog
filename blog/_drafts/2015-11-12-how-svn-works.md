---
layout: post
title:  "SVN是如何工作的？"
date:   2015-11-12
---

# 目录

* 服务器篇
    * Repository目录结构
    * FSFS文件系统目录结构
* 客户端篇

```
提示：这篇文章并不是要介绍svn的用法，而是想探讨一下svn的内部工作原理。
我的测试环境是：
* Mac OS X 10.11
* Subversion 1.8.13
```

***
# 服务器

{% include_relative how-svn-works/server/01-repo.md %}
{% include_relative how-svn-works/server/02-fsfs.md %}
{% include_relative how-svn-works/server/03-revs.md %}

***
# 客户端