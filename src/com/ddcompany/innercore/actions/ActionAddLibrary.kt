package com.ddcompany.innercore.actions

import com.ddcompany.innercore.extensions.getMainModule
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootModificationUtil
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.ResourceUtil
import icons.JavaScriptLanguageIcons


class ActionAddLibrary : AnAction(JavaScriptLanguageIcons.Library.JsLibrary) {
    companion object {
        const val IC_LIB = "IC Library"

        private fun getLibraryDir(): VirtualFile {
            val url = ResourceUtil.getResource(this::class.java, "scripts/api", "Debug.ts")
            return VfsUtil.findFileByURL(url)?.parent!!
        }

        fun apply(project: Project) {
            ApplicationManager.getApplication().runWriteAction {
                val table = LibraryTablesRegistrar.getInstance().getLibraryTable(project)
                val library = table.createLibrary(IC_LIB)
                val model = library.modifiableModel

                model.addRoot(this.getLibraryDir(), OrderRootType.CLASSES)
                model.commit()
                ModuleRootModificationUtil.addDependency(project.getMainModule(), library)
            }
        }
    }

    override fun actionPerformed(event: AnActionEvent) {
        ApplicationManager.getApplication().runWriteAction {
            val project = event.project
            val table = LibraryTablesRegistrar.getInstance().getLibraryTable(project!!)
            val library = table.getLibraryByName(IC_LIB)

            if (library == null) {
                apply(event.project!!)
            } else table.removeLibrary(library)
        }
    }

    override fun update(event: AnActionEvent) {
        if (LibraryTablesRegistrar.getInstance().getLibraryTable(event.project!!).getLibraryByName(IC_LIB) != null) {
            event.presentation.text = "Delete InnerCore Library"
        } else event.presentation.text = "Add InnerCore Library"
    }
}
