package com.kiran.fampaytask.models

import com.google.gson.annotations.SerializedName

data class CardGroupObject(
    @SerializedName("card_groups")
    var cards: List<CardGroup>? = null,

)