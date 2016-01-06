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
    // /* We are done. */
    C.vmDestroyJavaVM(jvm)
}
