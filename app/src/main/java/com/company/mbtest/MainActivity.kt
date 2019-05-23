package com.company.mbtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var imageUrl = "https://i.pinimg.com/236x/d3/70/ba/d370bad2408c44e7596504f3796be999--fairies-fairy-tail.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewId.setOnClickListener {view -> onTextClick(view) }
    }

    private fun onTextClick(view: View){
        (view as TextView).text = "Clicked"
        var iv = imageViewId;
        Glide.with(this).load(imageUrl).into(iv)
    }
}
