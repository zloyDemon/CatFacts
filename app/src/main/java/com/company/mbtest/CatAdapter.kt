package com.company.mbtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import kotlinx.android.synthetic.main.item_cat_fact.view.*

class CatAdapter (private val cats : List<CatFactModel>?) : RecyclerView.Adapter<CatViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CatViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cat_fact, viewGroup, false)
        return CatViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return cats!!.size
    }

    override fun onBindViewHolder(catViewHolder: CatViewHolder, position: Int) {
        cats?.get(position)?.let { catViewHolder.bindData(it) }
    }

}

class CatViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    private var  catFactText : TextView

    init {
        catFactText = itemView.catFact;
    }

    public fun bindData(catFactModel: CatFactModel){
        catFactText.text = catFactModel.text;
    }
}