package me.worric.kotlinplayground.data

class Person(var name: String, var surname: String) {

    fun add(x: Int, y: Int): Int = x + y

    fun getNameFullName(): String = "$name + $surname"

    var skod: String = ""
        get() = field.toUpperCase()
        set(value) {
            field = "name: $value"
        }

}