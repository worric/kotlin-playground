package me.worric.kotlinplayground.domain.commands

interface Command<out T> {
    fun execute(): T
}