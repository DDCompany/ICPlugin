package com.ddcompany.innercore.index

import com.ddcompany.innercore.ICIcons
import com.ddcompany.innercore.ICService
import com.ddcompany.innercore.mod.ResourceType
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.indexing.*
import com.intellij.util.io.DataExternalizer
import com.intellij.util.io.EnumeratorStringDescriptor
import java.io.DataInput
import java.io.DataOutput

abstract class TexturesIndex : FileBasedIndexExtension<String, TextureIcon>() {
    companion object {
        val FOR_ITEMS = ID.create<String, TextureIcon>("innerCore.TexturesIndex.Items")
        val FOR_BLOCKS = ID.create<String, TextureIcon>("innerCore.TexturesIndex.Blocks")

        fun getIcon(name: String, type: ResourceType, project: Project) =
                this.getTexture(name, type, project)?.scaledIcon ?: ICIcons.INVALID_16

        fun getTexture(name: String, type: ResourceType, project: Project): TextureIcon? {
            val values =
                    FileBasedIndex.getInstance().getValues(type.id, name, GlobalSearchScope.allScope(project))
            return if (values.isEmpty())
                null
            else values.first()!!
        }

        fun eachTexture(id: ID<String, TextureIcon>, project: Project, func: (key: String, texture: TextureIcon) -> Unit) {
            FileBasedIndex.getInstance().getAllKeys(id, project).forEach { key ->
                FileBasedIndex.getInstance().processValues(id, key, null, { _, texture ->
                    func(key, texture)
                    true
                }, GlobalSearchScope.allScope(project))
            }
        }
    }

    override fun getValueExternalizer(): DataExternalizer<TextureIcon> {
        return object : DataExternalizer<TextureIcon> {
            override fun save(out: DataOutput, icon: TextureIcon?) {
                if (icon != null)
                    out.writeUTF(icon.path)
            }

            override fun read(input: DataInput) =
                    TextureIcon.get(input.readUTF())
        }
    }

    override fun getVersion() = 0

    override fun dependsOnFileContent() = true

    override fun getInputFilter(): FileBasedIndex.InputFilter {
        return FileBasedIndex.InputFilter {
            it.name.endsWith(".png")
        }
    }

    override fun getKeyDescriptor() = EnumeratorStringDescriptor.INSTANCE!!

    class Blocks : TexturesIndex() {
        private val indexer = Indexer(ResourceType.TERRAIN_TEXT)

        override fun getIndexer() = indexer

        override fun getName() = FOR_BLOCKS
    }

    class Items : TexturesIndex() {
        private val indexer = Indexer(ResourceType.ITEMS_TEXT)

        override fun getIndexer() = indexer

        override fun getName() = FOR_ITEMS
    }

    class Indexer(private val type: ResourceType) : DataIndexer<String, TextureIcon, FileContent> {
        override fun map(content: FileContent): MutableMap<String, TextureIcon> {
            val file = content.file
            val mod = ICService.get(content.project).mod
            if (mod.isInResourcesDir(file, type)) {
                val texture = TextureIcon.get(file)
                return hashMapOf(texture.name + "_" + texture.meta to texture)
            }

            return hashMapOf()
        }
    }
}