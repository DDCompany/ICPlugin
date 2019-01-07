package com.ddcompany.innercore.extensions

fun String.appendIf(str: String): String =
        if (!this.endsWith(str)) this + str else this

fun String.delIf(str: String): String =
        if (this.endsWith(str)) this.substring(0, this.length - str.length) else this
