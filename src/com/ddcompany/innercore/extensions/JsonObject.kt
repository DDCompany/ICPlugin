package com.ddcompany.innercore.extensions

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

val prettyGson = GsonBuilder().setPrettyPrinting().create()!!

fun JsonObject.getPretty() =
        prettyGson.toJson(this)!!