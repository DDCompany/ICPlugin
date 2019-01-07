package com.ddcompany.innercore.project.structure.buildConfig

enum class BCType(private val asStr: String) {
    TEXTURES("Textures"),
    GUI("GUI Resources"),
    SRC("Source Code");

    override fun toString() = this.asStr
}