package com.kiran.fampaytask.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BgImage(
    @SerializedName("image_type")
    var imageType: String? = null,

    @SerializedName("image_url")
    var imageUrl: String? = null,

    @SerializedName("aspect_ratio")
    var aspectRatio: Double? = null

)