---
layout: post
title:  "JNI笔记之三：用Go启动Java虚拟机"
date:   2016-01-06
---

# 测试环境
* Mac OS X 10.11
* JDK 1.8.0_60
* Go 1.5.1

# 第1步
在./src/loadjvm目录下创建main.go：

```go
package main

// #cgo CFLAGS: -I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include
// #cgo CFLAGS: -I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include/darwin
// #cgo LDFLAGS: -L/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jli -ljli
//
// #include <stdbool.h>
// #include <stdio.h>
// #include <stdlib.h>
// #include <jni.h>
// 
// void loadJVM() {
//     JavaVM *jvm;       /* denotes a Java VM */
//     JNIEnv *env;       /* pointer to native method interface */
//     JavaVMInitArgs vm_args; /* JDK/JRE 6 VM initialization arguments */
//     JavaVMOption* options = malloc(1 * sizeof(JavaVMOption));
//     options[0].optionString = "-Djava.class.path=.";
//     vm_args.version = JNI_VERSION_1_6;
//     vm_args.nOptions = 1;
//     vm_args.options = options;
//     vm_args.ignoreUnrecognized = false;
//     /* load and initialize a Java VM, return a JNI interface
//      * pointer in env */
//     JNI_CreateJavaVM(&jvm, (void**)&env, (void*)&vm_args);
//     // delete options;
//     /* invoke the Main.test method using the JNI */
//     jclass cls = (*env)->FindClass(env, "HelloWorld");
//     jmethodID mid = (*env)->GetStaticMethodID(env, 
//         cls, "main", "([Ljava/lang/String;)V");
//    
//     (*env)->CallStaticVoidMethod(env, cls, mid);
//     // /* We are done. */
//     (*jvm)->DestroyJavaVM(jvm);
// }
import "C"

func main() {
    C.loadJVM()
}
```

# 第2步
编译main.go：

```shell
export GOPATH=`pwd`
go install loadjvm
```

# 第3步
把[HelloWorld.class和libHelloWorld.dylib](/myblog/2016/01/05/jni-helloworld.html)
拷贝到当前目录，然后运行loadjvm：

```shell
export LD_LIBRARY_PATH=/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jli/
./bin/loadjvm
```

# 第4步
用Go重写loadJVM()：

```go
package main

// #cgo CFLAGS: -I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include
// #cgo CFLAGS: -I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include/darwin
// #cgo LDFLAGS: -L/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jli -ljli
//
// #include <stdbool.h>
// #include <stdio.h>
// #include <stdlib.h>
// #include <jni.h>
// 
// jint createJavaVM(JavaVM **pvm, JNIEnv **penv, JavaVMInitArgs *args) {
//     return JNI_CreateJavaVM(pvm, (void **)penv, (void *)args);
// }
// jclass envFindClass(JNIEnv *env, const char *name) {
//     return (*env)->FindClass(env, name);
// }
// jmethodID envGetStaticMethodID(JNIEnv *env, jclass clazz, const char *name, const char *sig) {
//     return (*env)->GetStaticMethodID(env, clazz, name, sig);
// }
// void envCallStaticVoidMethod(JNIEnv *env, jclass cls, jmethodID methodID) {
//     (*env)->CallStaticVoidMethod(env, cls, methodID);
// }
// jint vmDestroyJavaVM(JavaVM *vm) {
//     return (*vm)->DestroyJavaVM(vm);
// }
import "C"

func main() {
    loadJVM()
}

func loadJVM() {
    var jvm *C.JavaVM
    var env *C.JNIEnv
    var vm_args C.JavaVMInitArgs
    var options = []C.JavaVMOption{{}}
    options[0].optionString = C.CString("-Djava.class.path=.")
    vm_args.version = C.JNI_VERSION_1_6
    vm_args.nOptions = 1
    vm_args.options = &options[0]
    vm_args.ignoreUnrecognized = C.false
    C.createJavaVM(&jvm, &env, &vm_args)

    var cls C.jclass = C.envFindClass(env, C.CString("HelloWorld"))
    var mid C.jmethodID = C.envGetStaticMethodID(env, cls, 
        C.CString("main"), C.CString("([Ljava/lang/String;)V"))
    C.envCallStaticVoidMethod(env, cls, mid)
    
    C.vmDestroyJavaVM(jvm)
}
```

# 第5步
重写编译和运行loadjvm.go：

```shell
go install loadjvm
./bin/loadjvm
```

# 参考资料
* http://blog.golang.org/c-go-cgo
* https://golang.org/cmd/cgo/
* https://github.com/golang/go/wiki/cgo
* https://github.com/abneptis/GoJVM