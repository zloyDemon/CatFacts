package com.company.mbtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
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

    }

    private fun onTextClick(view: View){

    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }
}
