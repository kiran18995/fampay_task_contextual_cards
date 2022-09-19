package com.kiran.fampaytask.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FormattedTitle(
    @SerializedName("text")
    var text: String? = null,

    @SerializedName("entities")
    var entities: List<Entity>? = null
)