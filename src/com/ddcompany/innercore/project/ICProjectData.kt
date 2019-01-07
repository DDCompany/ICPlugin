package com.ddcompany.innercore.project

import com.ddcompany.innercore.project.structure.buildConfig.BCItem
import com.ddcompany.innercore.project.structure.ICApi

class ICProjectData(
        val name: String,
        val version: String,
        val author: String,
        val description: String,
        val libsDir: String,
        val api: ICApi,
        val items: List<BCItem>)
