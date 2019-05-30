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
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.internal.GsonBuildConfig
import kotlinx.android.synthetic.main.fragment_facts.*
import kotlinx.android.synthetic.main.fragment_facts.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FactsFragment : Fragment() {

    private val DebugTag = "FactsFragment"
    private var baseUrl = "https://api.myjson.com/"
    private lateinit var mRetrofit: Retrofit
    private lateinit var factsRecyclerView: RecyclerView
    private lateinit var onItemClicked: OnItemClicked;
    private lateinit var progressBar : ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_facts, container, false);
        factsRecyclerView = view.catFactsRV;
        factsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        progressBar = view.progressBar
        FactsRequest()
        return view
    }

    fun SetItemClickListener(onItemClicked: OnItemClicked){
        this.onItemClicked = onItemClicked;
    }

    private fun FactsRequest() {
        factsRecyclerView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        mRetrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        var r = mRetrofit.create(CatFactRequest::class.java)
        r.getCatFacts().enqueue(object : Callback<List<CatFactModel>> {
            override fun onFailure(call: Call<List<CatFactModel>>, t: Throwable) {
                Log.e("RequestError: ", t.message)
            }

            override fun onResponse(call:  Call<List<CatFactModel>>, response: Response<List<CatFactModel>>) {
                var list = response.body()
                if(list != null){
                    progressBar.visibility = View.GONE
                    factsRecyclerView.visibility = View.VISIBLE
                    createFactsList(list)
                }

            }
        })
    }

    private fun createFactsList(list: List<CatFactModel>?) {
        val adapter = CatAdapter(list?.toMutableList(), object : OnItemClicked {
            override fun onClickItem(catFactModel: CatFactModel, index: Int) {
                onItemClicked.onClickItem(catFactModel, index)
            }
        })

        factsRecyclerView.addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
        factsRecyclerView.adapter = adapter
    }

    private fun log(message: String) {
        Log.d(DebugTag, message)
    }
}