package com.ddcompany.innercore.extensions

import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.openapi.util.TextRange

fun JSLiteralExpression.rangeWithoutQuarts() =
        TextRange(1, this.textLength - 1)