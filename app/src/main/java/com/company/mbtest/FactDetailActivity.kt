package com.company.mbtest

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.CircularProgressDrawable
import android.transition.Visibility
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_fact_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FactDetailActivity : AppCompatActivity() {

    interface AddDeleteClick{
        fun onClick(catFactModel: CatFactModel, clickType: ClickType)
    }

    public enum class ClickType {
        ADDED,
        DELETED
    }

    companion object{
        val CAT_FACT_MODEL = "cat_fact_model"
    }

    private lateinit var clickType : ClickType
    private lateinit var mRetrofit: Retrofit
    val baseUrl = "https://aws.random.cat/"
    private lateinit var currentCfm : CatFactModel
    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fact_detail)
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.fact_detail_activity)
        Realm.init(this)
        createListenres()
        mRealm = Realm.getDefaultInstance()
        currentCfm = intent.getSerializableExtra(CAT_FACT_MODEL) as CatFactModel
        catFactDetailText.text = currentCfm.text
        changeButtonType()
        RandomImgRequest()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    private fun RandomImgRequest() {
        mRetrofit = Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build()
        val r = mRetrofit.create(CatRandomImg::class.java)
        r.getRandomImgData().enqueue(object : Callback<RandomImage> {
            override fun onFailure(call: Call<RandomImage>, t: Throwable) {
                Log.e("", t.printStackTrace().toString())
            }

            override fun onResponse(call: Call<RandomImage>, response: Response<RandomImage>) {
                Log.d("Det", response.body().toString())
                getImgByUrl(response.body()?.imgUrl!!)
            }
        })
    }

    private fun getImgByUrl(url : String){
        imgLoadProgressBar.visibility = View.VISIBLE
        Glide.with(this).load(url).listener(object : RequestListener<Drawable?>{
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                imgLoadProgressBar.visibility = View.GONE
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?,isFirstResource: Boolean           ): Boolean {
                imgLoadProgressBar.visibility = View.GONE
                return false
            }
        }).into(randomImg)
    }

    private fun createListenres(){
        AddDelBtn.setOnClickListener { b -> AddDelClickBtn() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun checkFactInDB(id: Int) : Boolean{
        var result = mRealm.where(FavouriteFact::class.java).equalTo("id", currentCfm.id).findFirst()
        return result != null
    }

    private fun changeButtonType(){
        if(!checkFactInDB(currentCfm.id)){
            clickType = ClickType.ADDED
            AddDelBtn.text = getString(R.string.add_btn)
        }else{
            clickType = ClickType.DELETED
            AddDelBtn.text = getString(R.string.delete_btn)
        }
    }

    private fun AddDelClickBtn(){
        mRealm.beginTransaction()
        if(clickType == ClickType.ADDED){
            var factFavourite = mRealm.createObject(FavouriteFact::class.java)
            factFavourite.id = currentCfm.id
            factFavourite.text = currentCfm.text
        }else if(clickType == ClickType.DELETED){
            var results = mRealm.where(FavouriteFact::class.java).equalTo("id", currentCfm.id).findAll()
            results.deleteAllFromRealm()
        }
        changeButtonType()
        mRealm.commitTransaction()
    }
}
