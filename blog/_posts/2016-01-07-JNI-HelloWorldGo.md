---
layout: post
title:  "JNI笔记之四：用Go实现Java本地方法"
date:   2016-01-06
---

# 测试环境
* Mac OS X 10.11
* JDK 1.8.0_60
* Go 1.5.1

# 第1步
编写HelloWorld.java，用javac编译，然后用javah生成头文件。
具体请看[这里](/myblog/2016/01/05/jni-helloworld.html)。

# 第2步
用Go实现本地方法。在./src/helloworld目录下创建main.go：

```go
package main

// #cgo CFLAGS: -I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include
// #cgo CFLAGS: -I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include/darwin
// #include <jni.h>
import "C"

//export Java_HelloWorld_print
func Java_HelloWorld_print(env *C.JNIEnv, obj C.jobject) {
    println("Hello, World!")
}

func main() {}
```

# 第3步
把main.go编译成动态链接库：

```shell
export GOPATH=`pwd`
go build -buildmode=c-shared -o libHelloWorld.dylib helloworld
```

# 第4步
运行HelloWorld程序：

```shell
java HelloWorld
```

# 参考资料
* https://blog.dogan.io/2015/08/15/java-jni-jnr-go/
