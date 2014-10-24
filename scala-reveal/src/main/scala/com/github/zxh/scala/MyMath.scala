package com.github.zxh.scala

class MyMath {
    def foo = {}
    def bar = {}
}

object MyMath {

    val PI = 3.14

    def abs(a: Int): Int = {
        if (a < 0) -a else a
    }
    
}

object MyMathTest {

    def main(args: Array[String]) {
        println("PI is " + MyMath.PI)
        val x = -18
        val y = MyMath.abs(x)
    }

}

/*

public final class MyMath$ {
    
    public static final MyMath$ MODULE$;
    private final double PI;

    static {
        new MyMath$();
    }

    private MyMath$() {
        MyMath$.MODULE$ = this;
        this.PI = 3.14;
    }

    public double PI() {
        return this.PI;
    }

    public int abs(int a) {
        return return (a < 0) ? -a : a;
    }

}

public final class MyMath {
    
    public static double PI() {
        return MyMath$.MODULE$.PI();
    }

    public static int abs(int a) {
        return MyMath$.MODULE$.abs(a);
    }

}

*/