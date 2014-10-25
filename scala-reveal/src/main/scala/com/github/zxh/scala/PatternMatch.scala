package com.github.zxh.scala

case class CC(x: Int, y: Int)

class Patterns {

    def wildcard(arg: Any) = arg match {
        case _ => 
    } 

    def constant1(arg: Int): Int = arg match {
        case 1 => 1
        case 7 => 2
    }

    def constant2(arg: Any): Int = arg match {
        case 1 => 1
        case 7 => 2
        case false => 3
        case "str" => 4
    }

    def variable(arg: Any): String = arg match {
        case 1 => "1"
        case x => "x:" + x
    }

    def constructor(arg: Any): Int = arg match {
        case CC(1, 2) => 2
        case CC(x, 2) => 3
        case CC(x, y) => 4
    }

}


object PMTest {

    def main(args: Array[String]) {
        val cc = CC

        cc(1, 2)
        //println(cc)
    }

}

/*

public class CC implements scala.Product, scala.Serializable {
    
    private final int x;
    private final int y;

    public CC(int x, int y) {
        this.x = x;
        this.y = y;
        scala.Product$class.$init$(this);
    }

    public int x() {
        return x;
    }
    public int y() {
        return y;
    }

    public int hashCode() {...}
    public boolean equals(Object) {...}
    public String toString() {...}

    // ?
    public boolean canEqual(Object obj) {
        return obj instanceof CC;
    }

    public CC copy(int x, int y) {
        return new CC(x, y);
    }

    // Product
    // def productArity: Int
    // def productElement(n: Int): Any
    // def productIterator: Iterator[Any]
    // def productPrefix = ""
    
    public int productArity() {
        return 2;
    }
    public Object productElement(int n) {
        switch (n) {
            case 0: return x();
            case 1: return y();
            default: throw new IndexOutOfBoundsException();
        }
    }
    public scala.collection.Iterator productIterator() {
        return scala.runtime.ScalaRunTime$.MODULE$.typedProductIterator(this);
    }
    public String productPrefix() {
        return "CC";
    }

    public static CC apply(int x, int y) {
        return CC$.MODULE$.apply(x, y);
    }
    public static scals.Option<scala.Tuple2<Object, Object>> unapply(CC cc) {
        return CC$.MODULE$.unapply(cc);
    }
    public scala.Function1 static tupled() {
        return CC$.MODULE$.tupled();
    }
    public scala.Function1 static curried() {
        return CC$.MODULE$.curried();
    }
    
}

public final class CC$ extends scala.runtime.AbstractFunction2 
        implements scala.Serializable {
    
    public static final CC$ MODULE$;

    static {
        new CC$();
    }

    private CC$() {
        MODULE$ = this;
    }

    public Object readResolve() {
        return MODULE$;
    }

    public String toString() {
        return "CC";
    }

    public CC apply(int x, int y) {
        return new CC(x, y);
    }

    public scala.Option<scala.Tuple2<Object, Object>> unapply(CC cc) {
        if (cc == null) {
            return scala.None$.MODULE$;
        }
        return new scala.Some(new scala.Tuple2$mcll$sp(cc.x, cc.y))
    } 

}

*/