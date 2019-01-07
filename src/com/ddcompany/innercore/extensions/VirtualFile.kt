package com.ddcompany.innercore.extensions

import com.intellij.openapi.vfs.VirtualFile
import java.nio.charset.Charset

fun VirtualFile.createChild(path: String, content: String): VirtualFile {
    val file = this.createChildData(this, path)
    file.setBinaryContent(content.toByteArray(Charset.forName("UTF-8")))
    return file
}