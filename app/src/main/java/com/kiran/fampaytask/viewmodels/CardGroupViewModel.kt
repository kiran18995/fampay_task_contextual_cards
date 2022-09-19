package com.kiran.fampaytask.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.kiran.fampaytask.models.CardGroupObject
import com.kiran.fampaytask.repositories.CardRepository

class CardGroupViewModel(application: Application) : AndroidViewModel(application) {
    private var cardRepository: CardRepository? = null
    private var cardGroupLiveData: LiveData<CardGroupObject?>? = null
    fun init() {
        cardRepository = CardRepository()
        fetchCardGroupList()
        cardGroupLiveData = cardRepository!!.getCardGroupList()
    }

    fun fetchCardGroupList() {
        cardRepository?.fetchCardGroups()
    }

    fun getCardGroupLiveData(): LiveData<CardGroupObject?>? {
        return cardGroupLiveData
    }
}