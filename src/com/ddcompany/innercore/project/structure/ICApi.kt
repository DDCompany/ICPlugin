package com.ddcompany.innercore.project.structure

enum class ICApi(val asText: String) {
    CE("CoreEngine"),
    ADAPTED("AdaptedScript");

    override fun toString() = this.asText
}