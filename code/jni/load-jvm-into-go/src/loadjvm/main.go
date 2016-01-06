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

// export LD_LIBRARY_PATH=/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/jre/lib/jli/

