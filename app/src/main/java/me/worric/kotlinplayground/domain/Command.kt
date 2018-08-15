package me.worric.kotlinplayground.domain

interface Command<out T> {
    fun execute(): T
}