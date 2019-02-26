package com.ddcompany.innercore.mod

import com.google.gson.JsonObject
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile

class ICResModule private constructor(val file: VirtualFile, val name: String) {
    companion object {
        fun build(projectDir: VirtualFile, jsonObj: JsonObject): ICResModule? {
            val file = projectDir.findChild(jsonObj.get("path").asString)
            return if (file != null)
                return ICResModule(file, file.name) else null
        }
    }

    fun collectFilesRecurse(dir: String): List<VirtualFile> {
        return VfsUtil.collectChildrenRecursively(file.findOrCreateChildData(this, dir))
    }
}