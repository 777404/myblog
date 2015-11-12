package com.github.zxh.scala

class Primitives {

    def test() = {
        var x = 1
        println(x.toString)
        3.to(8).foreach {
            i => println(i)
        }

    }

    def collect(x: Int, y: Int): Array[Int] = {
        Array(x, y)
    }
    /*
    public int[] collect(int x, int y) {
        return new int[] {x, y};
    }
    */

    // BoxesRuntime
    def boxToXXX() = {
        val x = 1
        val str = x.toString()
        val hc = x.hashCode()
    }
    /*
    int x = 1;
    Integer boxedX = scala.runtime.BoxesRuntime.boxToInteger(x);
    boxedX.toString();
    boxedX.hashCode();
    */
    /*
    public static java.lang.Integer boxToInteger(int i) {
        return java.lang.Integer.valueOf(i);
    }
    */

    // Implicit Conversions
    // Predef
    def implicitConversion() = {
        val x = 1
        val y = 2
        x.compareTo(y)
    }
    /*
    int x = 1;
    int y = 2;
    Integer boxedX = scala.Predef$.MODULE$.int2Integer(x);
    Integer boxedY = scala.Predef$.MODULE$.int2Integer(y);
    boxedX.compareTo(boxedY);
    */
    
    // RichInt
    def richXXX() = {
        val x = -1
        x.abs
        //3.max(2)
    }
    /*
    int x = -1;
    RichInt richX = scala.Predef$.MODULE$.intWrapper(x);
    richX.abs();
    //scala.runtime.RichInt$.MODULE$
    */
    /*
    object Predef extends LowPriorityImplicits {...}
    private[scala] abstract class LowPriorityImplicits {
        @inline implicit def intWrapper(x: Int) = new runtime.RichInt(x)
    }
    */

    def bigXXX() = {
        val x: BigInt = 237
        val y = (x * 7) pow 95
    }
    /*
    int x = 237;
    BigInt bigX = BigInt$.MODULE$.int2bigInt(x);
    BigInt y = bigX.$times(7).pow(95);
    */

    def box3() = {
        val x = 1
        x.getClass()
    }
    /*
    int x = 1;
    Integer boxedX = scala.runtime.BoxesRuntime.boxToInteger(x);
    scala.reflect.ClassTag$.MODULE$.Int()...
    */

    def box4() = {
        val x = 1
        x.toByte
    }
    /*
    int x = 1;
    return (byte) x;
    */

    def testAny(a: Any) = {

    }

}
