package com.ddcompany.innercore.extensions

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.LocalFileSystem

fun Project.getMainModule(): Module {
    val projectFile = LocalFileSystem.getInstance().findFileByPath(this.basePath!!)
    val fileIndex = ProjectRootManager.getInstance(this).fileIndex
    return fileIndex.getModuleForFile(projectFile!!)!!
}