---
layout: post
title:  "JNI笔记之二：Invocation API"
date:   2016-01-05
---

# 测试环境
* Mac OS X 10.11
* JDK 1.8.0_60

# 第1步
创建LoadJVM.c：

```c
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <jni.h>

void loadJVM();

int main() {
    loadJVM();
    return 0;
}

void loadJVM() {
    JavaVM *jvm;       /* denotes a Java VM */
    JNIEnv *env;       /* pointer to native method interface */
    JavaVMInitArgs vm_args; /* JDK/JRE 6 VM initialization arguments */
    JavaVMOption* options = malloc(1 * sizeof(JavaVMOption));
    options[0].optionString = "-Djava.class.path=.";
    vm_args.version = JNI_VERSION_1_6;
    vm_args.nOptions = 1;
    vm_args.options = options;
    vm_args.ignoreUnrecognized = false;
    /* load and initialize a Java VM, return a JNI interface
     * pointer in env */
    JNI_CreateJavaVM(&jvm, (void**)&env, (void*)&vm_args);
    // delete options;
    /* invoke the Main.test method using the JNI */
    jclass cls = (*env)->FindClass(env, "HelloWorld");
    jmethodID mid = (*env)->GetStaticMethodID(env, 
        cls, "main", "([Ljava/lang/String;)V");

    jobjectArray args = (*env)->NewObjectArray(env, 5, 
        (*env)->FindClass(env, "java/lang/String"),
        (*env)->NewStringUTF(env, "")); 

    (*env)->CallStaticVoidMethod(env, cls, mid, args);
    // /* We are done. */
    (*jvm)->DestroyJavaVM(jvm);
}
```

# 第2步
编译LoadHVM.c：

```shell
gcc "-I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include" \
    "-I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include/darwin" \
    "-L/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/server" \
    -o LoadJVM LoadJVM.c -ljvm
```

# 第3步
把[HelloWorld.class和libHelloWorld.dylib](jni-helloworld.html)
拷贝到当前目录，然后运行LoadJVM：

```shell
export LD_LIBRARY_PATH=/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/server/
./LoadJVM
```

但是运行会报下面这个错误：

> 您需要安装旧 Java SE 6 运行环境才能打开“此 Java 应用程序”。

# 第4步
重新编译LoadHVM.c：

```shell
gcc "-I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include" \
    "-I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include/darwin" \
    "-L/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jli" \
    -o LoadJVM LoadJVM.c -ljli
```

注意：区别在于不使用libjvm.dylib而是使用libjli.dylib。

# 第5步
重新运行LoadJVM：

```shell
export LD_LIBRARY_PATH=/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jli/
./LoadJVM
```

# 参考资料
* http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/invocation.html
* http://www.petesh.com/archives/2013/12/launching-a-jvm-from-c-on-mac-os-x/
* http://www.rmnd.net/calling-java-from-c-on-mac-osx-mavericks-with-jni-and-java-8/
* https://github.com/originell/jpype/issues/160
