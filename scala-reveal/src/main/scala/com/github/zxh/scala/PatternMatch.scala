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
