package com.kiran.fampaytask.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kiran.fampaytask.models.CardGroupObject
import com.kiran.fampaytask.network.APIClient
import com.kiran.fampaytask.network.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardRepository {
    private var apiInterface: APIInterface? = null
    private var cardGroupMutableLiveData: MutableLiveData<CardGroupObject?>?

    init {
        cardGroupMutableLiveData = MutableLiveData<CardGroupObject?>()
        apiInterface = APIClient.instance.apiInterface
    }

    fun fetchCardGroups() {
        cardGroupMutableLiveData = MutableLiveData<CardGroupObject?>()
        apiInterface?.getCardGroups()
            ?.enqueue(object : Callback<CardGroupObject?> {
                override fun onResponse(
                    call: Call<CardGroupObject?>,
                    response: Response<CardGroupObject?>
                ) {
                    if (response.body() != null) {
                        cardGroupMutableLiveData!!.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<CardGroupObject?>, t: Throwable) {
                    cardGroupMutableLiveData!!.postValue(null)
                }
            })
    }

    fun getCardGroupList(): LiveData<CardGroupObject?>? {
        return cardGroupMutableLiveData
    }

    private fun onError() {
        cardGroupMutableLiveData!!.postValue(null)
    }
}