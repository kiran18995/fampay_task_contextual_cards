package com.kiran.fampaytask

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kiran.fampaytask.adapters.ContextualCardsAdapter
import com.kiran.fampaytask.viewmodels.CardGroupViewModel

class MainActivity : AppCompatActivity(), ContextualCardsAdapter.OnItemRemoveListener {
    private var contextualCardsAdapter: ContextualCardsAdapter? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var cardGroupViewModel: CardGroupViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loadingTv = findViewById<TextView>(R.id.loading_tv)
        loadingTv.visibility = View.VISIBLE
        contextualCardsAdapter = ContextualCardsAdapter()
        cardGroupViewModel = ViewModelProvider(this)[CardGroupViewModel::class.java]
        cardGroupViewModel!!.init()
        cardGroupViewModel!!.getCardGroupLiveData()?.observe(
            this
        ) { cardParent ->
            if (cardParent != null) {
                loadingTv.visibility = View.GONE
                contextualCardsAdapter!!.setCardGroupList(cardParent.cards)
            } else {
                loadingTv.visibility = View.VISIBLE
                loadingTv.text = getString(R.string.no_data)
            }
        }
        val recyclerView = findViewById<RecyclerView>(R.id.contextual_cards_rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contextualCardsAdapter
        contextualCardsAdapter!!.setOnItemRemoveListener(this)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener { refreshList() }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshList() {
        cardGroupViewModel?.fetchCardGroupList()
        contextualCardsAdapter = ContextualCardsAdapter()
        contextualCardsAdapter!!.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onItemRemoved(position: Int) {
        contextualCardsAdapter?.setOnItemRemoveListener(this)
    }
}