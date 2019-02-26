package com.ddcompany.innercore.mod

import com.ddcompany.innercore.index.TextureIcon
import com.ddcompany.innercore.index.TexturesIndex
import com.intellij.util.indexing.ID

enum class ResourceType(val dir: String, val id: ID<String, TextureIcon>) {
    TERRAIN_TEXT("terrain-atlas", TexturesIndex.FOR_BLOCKS),
    ITEMS_TEXT("items-opaque", TexturesIndex.FOR_ITEMS)
}