package me.worric.kotlinplayground.extensions

import android.content.Context
import androidx.core.content.ContextCompat

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)