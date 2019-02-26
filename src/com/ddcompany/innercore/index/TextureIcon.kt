package com.ddcompany.innercore.index

import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import java.awt.Image
import java.nio.file.Paths
import java.util.regex.Pattern
import javax.swing.ImageIcon

class TextureIcon private constructor(val file: VirtualFile) {
    companion object {
        //ingot_copper_0, gold_nugget_22
        val WITH_INDEX_REGEX = Pattern.compile(".+_[0-9]+").toRegex()

        fun get(path: String): TextureIcon? {
            val file = VfsUtil.findFile(Paths.get(path), true)
            return if (file != null)
                this.get(file)
            else null
        }

        fun get(file: VirtualFile) =
                TextureIcon(file)
    }

    val path = file.path
    private val originalIcon = ImageIcon(path)
    val width = originalIcon.iconWidth
    val height = originalIcon.iconHeight
    val scaledIcon = ImageIcon(originalIcon.image.getScaledInstance(16, 16, Image.SCALE_SMOOTH))
    val name: String

    val meta: String

    init {
        val nameAndMeta = this.getNameAndMeta()
        this.name = nameAndMeta[0]
        this.meta = nameAndMeta[1]
    }

    private fun getNameAndMeta(): Array<String> {
        val fileName = this.file.nameWithoutExtension
        if (fileName.matches(WITH_INDEX_REGEX)) {
            val underlinePos = fileName.lastIndexOf("_")
            val name = fileName.substring(0, underlinePos)
            val meta = fileName.substring(underlinePos + 1)

            return arrayOf(name, meta)
        }

        return arrayOf(name, "0")
    }
}