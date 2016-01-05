#include <jni.h>
#include <stdio.h>
#include "HelloWorld.h"
 
JNIEXPORT void JNICALL 
Java_HelloWorld_print(JNIEnv *env, jobject o) {
    printf("Hello, World!\n");
    return;
}

/*
gcc "-I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include" \
    "-I/Library/Java/JavaVirtualMachines/jdk1.8.0_60.jdk/Contents/Home/include/darwin" \
    -c HelloWorld.c

gcc -dynamiclib -o libHelloWorld.dylib HelloWorld.o
*/
