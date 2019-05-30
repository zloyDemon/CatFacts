package com.company.mbtest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import kotlinx.android.synthetic.main.item_cat_fact.view.*

interface OnItemClicked{
    fun onClickItem(catFactModel: CatFactModel, index : Int)
}

class CatAdapter (private val cats : MutableList<CatFactModel>?, onClickItemListener : OnItemClicked) : RecyclerView.Adapter<CatViewHolder>(){

    private var onClickItemListener = onClickItemListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): CatViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cat_fact, viewGroup, false)
        return CatViewHolder(itemView, onClickItemListener)
    }

    override fun getItemCount(): Int {
        return cats!!.size
    }

    override fun onBindViewHolder(catViewHolder: CatViewHolder, position: Int) {
        cats?.get(position)?.let { catViewHolder.bindData(it, position) }
    }
}

class CatViewHolder(itemView : View, onClickItemListener: OnItemClicked) : RecyclerView.ViewHolder(itemView){

    private var onClickItemListener = onClickItemListener
    private var catFactText : TextView = itemView.catFact
    private lateinit var currentCatFact : CatFactModel

    init {
        itemView.setOnClickListener {this.onClickItemListener.onClickItem(currentCatFact, adapterPosition) }
    }

    fun bindData(catFactModel: CatFactModel, position: Int){
        catFactText.text = catFactModel.text;
        currentCatFact = catFactModel
    }
}

