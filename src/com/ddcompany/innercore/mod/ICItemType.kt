package com.ddcompany.innercore.mod

enum class ICItemType(private val typeName: String) {
    SIMPLE("Simple"),
    FOOD("Food");

    override fun toString(): String {
        return typeName
    }
}