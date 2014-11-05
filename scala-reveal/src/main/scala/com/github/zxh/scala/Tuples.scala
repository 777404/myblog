package com.github.zxh.scala

class Tuples {

    def f() = {
        for(x <- 1 to 8; y <- 2 to 9) {
            println(x + y)
        }
    }

    def void() = {
        val void = 1
    }

    def unitIsVoid(x: Int, y: Int): Unit = {
        val z = x + y
    }

    // scala.runtime.BoxedUnit
    def unitIsNotVoid(nothing: Unit) = {
        val unit = ()
        // if (unit == nothing) {
        //     println("unit == nothing")
        // }
    }

    def tuple1() = {
        val x = ("a")
        val y = Tuple1("b")
    }

    def tuple2() = {
        val x = ("a", "b")
        val y = Tuple2("c", "d")
    }

    def tuple23() = {
        //val t23 = (1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,"21","22","23")
    }

    def nameAndAge() = {
        ("zxh", 32)
    }

    def assignToVars() = {
        val (name, age) = nameAndAge()
        println(name + " is " + age + " old")
    }

    def arrowAssoc() = {
        //val t = "a" -> 1
    }

    def map() = {
        //val m = Map("a" -> 1, "b" -> 2)
    }
    /*
    object Predef {
        val Map = scala.collection.immutable.Map
        implicit final class ArrowAssoc[A](private val self: A) extends AnyVal {
            @inline def -> [B](y: B): Tuple2[A, B] = Tuple2(self, y)
            def →[B](y: B): Tuple2[A, B] = ->(y)
        }
    }
    */
    /*
        //scala.collection.immutable.Map$ Map = scala.Predef$.MODULE$.Map()
        //scala.Predef$ArrowAssoc$.MODULE$
        Tuple2[] ts = new Tuple2[2];
        ts[0] = scala.Predef$ArrowAssoc$.MODULE$.$minus$greater$extension("a", 1); // ->
        ts[0] = scala.Predef$ArrowAssoc$.MODULE$.$minus$greater$extension("b", 2);
        scala.collection.immutable.Map$.apply(ts);

    */

    // specialized



}

// object AA {
//     implicit final class BB(val self: Int) {
//         def -> (y: Int) = Tuple2(self, y)
//     }
// }

object AATest {

    implicit final class AA[A](val self: A) extends AnyVal {
        def -> (y: Int) = Tuple2(self, y)
    }

}