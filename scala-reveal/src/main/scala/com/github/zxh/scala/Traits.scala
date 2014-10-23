package com.github.zxh.scala

class ScalaObject {
    def interface(): Int = {
        val interface = 1
        interface + 1
    }
}

trait TraitA {

}

trait TraitB {
    def m1(): Int
    def m2(arg: Int): Int
}

trait TraitC {
    def m3(arg: Int): Int = 1
}

trait TraitD {
    var f1: Int = 1
}

class MixedIn extends TraitA
    with TraitB with TraitC with TraitD {

    def m1: Int = 0
    def m2(arg: Int) = arg
}
