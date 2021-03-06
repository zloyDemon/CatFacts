package com.company.mbtest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var factsFragment =  FactsFragment();
    private var favouritesFragment =  FavouritesFragment();

    private lateinit var currentFragment : Fragment

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.new_cat_fact -> {
                currentFragment = factsFragment
                changeFragment(factsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.favorites -> {
                changeFragment(favouritesFragment)
                currentFragment = favouritesFragment
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createListeners()
        changeFragment(factsFragment)
    }

    /**
     * Create a view listeners
     */
    private fun createListeners(){
        bottomNavView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        factsFragment.SetItemClickListener(object : OnItemClicked{
            override fun onClickItem(catFactModel: CatFactModel, index: Int) {
                onItemClicked(catFactModel, index)
            }
        })

        favouritesFragment.SetItemClickListener(object : OnItemClicked{
            override fun onClickItem(catFactModel: CatFactModel, index: Int) {
                onItemClicked(catFactModel, index)
            }
        })
    }

    private fun onItemClicked(catFactModel: CatFactModel, index : Int){
        var intent = Intent(this, FactDetailActivity::class.java)
        intent.putExtra(FactDetailActivity.CAT_FACT_MODEL, catFactModel)
        startActivity(intent)
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}
