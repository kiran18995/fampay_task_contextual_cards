package com.kiran.fampaytask.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Entity(
    @SerializedName("text")
    var text: String? = null,

    @SerializedName("color")
    var color: String? = null,
)