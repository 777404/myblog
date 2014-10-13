package com.github.zxh.scala

// Singleton Object
object MyMath {

    val PI = 3.14

    def randomInt() = {
        new java.util.Random().nextInt()
    }
    
}

object Test {

    def main(args: Array[String]) {
        println(MyMath.PI)
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

    public int randomInt() {
        return new java.util.Random().nextInt();
    }

}

public final class MyMath {
    
    public static double PI() {
        return MyMath$.MODULE$.PI();
    }

    public static int randomInt() {
        return MyMath$.MODULE$.randomInt();
    }

}

*/