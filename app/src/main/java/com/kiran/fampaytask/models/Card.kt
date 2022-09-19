package com.kiran.fampaytask.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("formatted_title")
    var formattedTitle: FormattedTitle? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("formatted_description")
    var formattedDescription: FormattedDescription? = null,

    @SerializedName("url")
    var url: String? = null,

    @SerializedName("bg_image")
    var bgImage: BgImage? = null,

    @SerializedName("bg_color")
    var bgColor: String? = null,

    @SerializedName("icon")
    var icon: Icon? = null,

    @SerializedName("cta")
    var cta: List<Cta>? = null,

    @SerializedName("height")
    var height: Int? = null
)