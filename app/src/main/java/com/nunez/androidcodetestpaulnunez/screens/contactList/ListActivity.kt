package com.nunez.androidcodetestpaulnunez.screens.contactList

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import com.nunez.androidcodetestpaulnunez.R
import com.nunez.androidcodetestpaulnunez.screens.searchContact.SearchActivity

import kotlinx.android.synthetic.main.list_contact_activity.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_contact_activity)
        setSupportActionBar(toolbar)

        toolbar.apply {
            title = "Search contact"
            setNavigationIcon(R.drawable.ic_search)
            setNavigationOnClickListener {  }
            setOnClickListener {  }
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun goToSearchActivity(){
        val intent = Intent(this, SearchActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, toolbar, "toolbar")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            startActivity(intent, options.toBundle())
        else
            startActivity(intent)
    }

}
