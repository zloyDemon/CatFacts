package com.company.mbtest

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_favourites.view.*

class FavouritesFragment : Fragment(){

    private lateinit var mRealm: Realm
    private lateinit var favFactsRV: RecyclerView
    private lateinit var onItemClicked: OnItemClicked

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(context!!.applicationContext)
        mRealm = Realm.getDefaultInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_favourites, container, false)
        favFactsRV = view.favFactsRV
        ReadFromDB()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    public fun SetItemClickListener(onItemClicked: OnItemClicked){
        this.onItemClicked = onItemClicked;
    }

    private fun ReadFromDB(){
        var results = mRealm.where(FavouriteFact::class.java).findAll()
        favFactsRV.layoutManager = LinearLayoutManager(this.context)
        favFactsRV.addItemDecoration(DividerItemDecoration(context, ClipDrawable.HORIZONTAL))
        favFactsRV.adapter = FavoriteFactAdapter(results, object : OnItemClicked {
            override fun onClickItem(catFactModel: CatFactModel, index: Int) {
                onItemClicked.onClickItem(catFactModel, index)
            }
        })
    }


}