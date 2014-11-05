package com.github.zxh.scala

class MyObj {
    var intVar = 1
    final val strVal = "str"
}

abstract class AbstractObj {
    var intVar: Int
    val strVal: String
}

class MySubObj extends MyObj {
    //override val strVal = "substr"
    //def intVal = 2
}

class PrivateFields {
    private var intVar = 1
    private val strVal = "str"
}

class ObjectPrivateFields {
    private[this] var intVar = 1
    private[this] val strVal = "str"
}

import scala.beans.BeanProperty
class ScalaBean {
    @BeanProperty var intVar = 1
    @BeanProperty val strVal = "str"
}
