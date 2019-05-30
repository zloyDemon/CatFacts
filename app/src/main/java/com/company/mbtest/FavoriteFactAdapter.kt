package com.company.mbtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import io.realm.RealmChangeListener
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_cat_fact.view.*


class FavoriteFactAdapter (private val cats : RealmResults<FavouriteFact>, onClickItemListener : OnItemClicked) :
    RecyclerView.Adapter<FavoriteFactHolder>(), RealmChangeListener<RealmResults<FavouriteFact>> {

    private var onClickItemListener = onClickItemListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): FavoriteFactHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cat_fact, viewGroup, false)
        cats.addChangeListener(this)
        return FavoriteFactHolder(itemView, onClickItemListener)
    }

    override fun getItemCount(): Int {
        return cats.size
    }

    override fun onBindViewHolder(favoriteFactHolder: FavoriteFactHolder, position: Int) {
        cats.get(position)?.let { favoriteFactHolder.bindData(it, position) }
    }

    override fun onChange(t: RealmResults<FavouriteFact>) {
        notifyDataSetChanged()
    }
}

class FavoriteFactHolder(itemView : View, onClickItemListener: OnItemClicked) : RecyclerView.ViewHolder(itemView){

    private var onClickItemListener = onClickItemListener
    private var catFactText : TextView = itemView.catFact
    private lateinit var currentCatFact : FavouriteFact

    init {
        itemView.setOnClickListener {
            val catFactM = CatFactModel(currentCatFact.id, currentCatFact.text)
            this.onClickItemListener.onClickItem(catFactM, adapterPosition) }
    }

    fun bindData(favouriteFact : FavouriteFact, position: Int){
        catFactText.text = favouriteFact.text
        currentCatFact = favouriteFact
    }
}

