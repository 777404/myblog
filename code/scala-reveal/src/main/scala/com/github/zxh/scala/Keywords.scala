package com.github.zxh.scala
/* a-z 53
abstract assert
boolean break byte
case catch char class continue
default do double
else enum extends
final finally float for
if implements import instanceof int interface
long
native new
package private protected public
return
short static strictfp
super switch synchronized
this throw throws transient try
void volatile
while
const goto
true false null
*/
/*
assert
boolean, byte, short, char, int, long, float, double, void
package, import, class, interface, enum, implements, extends
public, protected, private, abstract, static, final, volatile, transient, synchronized, strictfp, native
try, catch, finally, throw, throws
if, else, do, while, for, switch, case, default, break, continue, return
super, this
new, instanceof
const, goto
true, false, null
*/
/* a-z 39
abstract
case catch class
def do
else extends
false final finally for forSome
if implicit import 
lazy
match
new null
object override 
package private protected
return
sealed super
this throw true trait try type
val var
while with
yield
*/
/*
package, import, class, object, trait, extends, with, type, forSome
private, protected, abstract, sealed, final, implicit, lazy, override
try, catch, finally, throw
if, else, match, case, do, while, for, return, yield
def, val, var 
this, super
new
true, false, null
*/

import scala.annotation.strictfp

class Keywords {

    @transient var t = 1
    @volatile var v = 1

    @strictfp
    def sfp() = {}

    @native
    def n(x: Int): Int;
    
    def sync() = {
        val str = "s"
        str.synchronized {
            val x = 2
        }
    }

    def instanceof(arg: Any) = {
        if (arg.isInstanceOf[String]) {
            val str = arg.asInstanceOf[String]
        }
    }

    object Color extends Enumeration {
        val Red = Value
        val Green = Value
        val Blue = Value
    }

    // 28
    def javaOnlyKeywords = {
        val assert = 1 // Predef.assert()
        val boolean, byte, char, short, int, long, float, double = 1
        val void = 1 // Unit
        val interface, implements = 1 // traits
        val static = 1 // Singleton Objects
        val const, goto = 1
        val native = 1 // @scala.native
        val transient = 1 // @scala.transient
        val volatile = 1 // @scala.volatile
        val strictfp = 1 // @scala.annotation.strictfp
        val public = 1
        val throws = 1 // no checked exceptions
        val synchronized = 1 // AnyRef.synchronized
        val instanceof = 1 // isInstanceOf[]/asInstanceOf[]
        val enum = 1
        val break, continue, default, switch = 1
    }

    // 25
    def javaAndScalaKeywords = {
        // abstract case catch class do else extends false final finally 
        // for if import new null package private protected return super
        // this throw true try while
    }

    // 14
    def scalaOnlyKeywords = { 
        // def forSome implicit lazy match object override sealed trait type 
        // val var with yield

        // def val var override
        // object trait with sealed?
        // implicit lazy
        // yield match
        // forSome type 
    }

}
