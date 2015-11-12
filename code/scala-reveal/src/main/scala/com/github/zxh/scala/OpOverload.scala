package com.github.zxh.scala

class Operators {

    def +() = 0; // $plus
    def -() = 0; // $minus
    def *() = 0; // $times
    def /() = 0; // $div
    def %() = 0; // $percent
    def &() = 0; // $amp
    def |() = 0; // $bar
    def ^() = 0; // $up
    def ~() = 0; // $tilde

    def >() = 0; // $greater
    def <() = 0; // $less
    def !() = 0; // $bang

    def ?=:() = 0; // $qmark$eq$colon
    def @#() = 0; // $at $hash
    def \() = 0; // $bslash
    def $() = 0; // $

}

class MyInt(val x: Int) {

    def +(y: Int) = new MyInt(x + y)
    def -(y: Int) = new MyInt(x - y)
    def +++(y: Int) = new MyInt(x + y * 3)
    def +:(y: Int) = new MyInt(x + y)
    def unary_+ = this
    def unary_- = new MyInt(-x)
    def unary_~ = new MyInt(~x)
    def unary_! = new MyInt(100 - x)

}

class Pair(arr: Array[Int]) {

    def apply(n: Int) = arr(n)
    def update(n: Int, v: Int) = {arr(n) = v}

}

object OpTest {
    
    def main(args: Array[String]) {
        val myInt = new MyInt(1)
        println(myInt.x)
        println((~myInt).x)
    }

}
