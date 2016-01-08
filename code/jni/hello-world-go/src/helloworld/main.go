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

/*
export GOPATH=`pwd`
go build -buildmode=c-shared -o libHelloWorld.dylib helloworld
*/
