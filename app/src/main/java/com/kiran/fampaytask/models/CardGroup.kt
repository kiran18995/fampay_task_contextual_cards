package com.kiran.fampaytask.models

import com.google.gson.annotations.SerializedName

data class CardGroup(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("design_type")
    var designType: DesignType,

    @SerializedName("cards")
    var cards: List<Card>? = null,

    @SerializedName("is_scrollable")
    var isScrollable: Boolean? = null
) {
    enum class DesignType {

        @SerializedName("HC1")
        SMALL_DISPLAY_CARD,

        @SerializedName("HC3")
        BIG_DISPLAY_CARD,

        @SerializedName("HC5")
        IMAGE_CARD,

        @SerializedName("HC6")
        SMALL_CARD_WITH_ARROW,

        @SerializedName("HC9")
        DYNAMIC_WIDTH_CARD
    }
}