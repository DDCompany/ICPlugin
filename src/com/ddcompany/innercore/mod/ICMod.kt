package com.ddcompany.innercore.mod

import com.ddcompany.innercore.extensions.getMainModule
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement

class ICMod : ICLibMod() {
    private val librariesMods = ArrayList<ICLibMod>()

    override fun find(func: (file: VirtualFile, element: PsiElement, module: ICSrcModule?, mod: ICLibMod) -> Unit) {
        super.find(func)

        this.librariesFile?.children?.forEach {
            visit(it, null, func)
        }

        ModuleRootManager.getInstance(project.getMainModule()).orderEntries().forEachLibrary {
            it.getFiles(OrderRootType.CLASSES).forEach { virtualFile ->
                var mod = librariesMods.find { mod -> virtualFile.path == mod.modFile.path }
                if (mod == null) {
                    mod = ICLibMod()
                    mod.init(project, virtualFile)
                }

                if (mod.valid) {
                    this.librariesMods.add(mod)
                    mod.find { file, element, module, icMod ->
                        func(file, element, module, icMod)
                    }
                }
            }
            true
        }
    }
}