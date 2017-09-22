package com.nunez.androidcodetestpaulnunez.screens.contactDetails

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.nunez.androidcodetestpaulnunez.R

import kotlinx.android.synthetic.main.details_activity.*

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_CONTACT_ID = "contact_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
