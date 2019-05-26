package com.company.mbtest

import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.internal.GsonBuildConfig
import kotlinx.android.synthetic.main.fragment_facts.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FactsFragment : Fragment() {

    private val DebugTag = "FactsFragment"
    private var baseUrl = "https://cat-fact.herokuapp.com"
    private lateinit var mRetrofit: Retrofit
    private lateinit var factsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_facts, container, false);
        factsRecyclerView = view.catFactsRV;
        factsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        FactsRequest()
        return view
    }

    private fun FactsRequest() {
        mRetrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        var r = mRetrofit.create(CatFactRequest::class.java)
        r.getCatFacts().enqueue(object : Callback<ListFacts> {
            override fun onFailure(call: Call<ListFacts>, t: Throwable) {

            }

            override fun onResponse(call: Call<ListFacts>, response: Response<ListFacts>) {
                var list = response.body()?.facts
                Log.d("Test", list.toString())
                createFactsList(list)
            }

        })
    }

    private fun createFactsList(list: List<CatFactModel>?) {
        val adapter = CatAdapter(list)
        factsRecyclerView.addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
        factsRecyclerView.adapter = adapter
    }

    private fun log(message: String) {
        Log.d(DebugTag, message)
    }

}