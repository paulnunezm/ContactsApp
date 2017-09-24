package com.nunez.androidcodetestpaulnunez.screens.addEditContact

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.nunez.androidcodetestpaulnunez.R
import kotlinx.android.synthetic.main.add_edit_activity.*

class AddEditActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CONTACT_ID = "contact_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit_activity)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_close_white))
        }

        val contactId = intent.getStringExtra(EXTRA_CONTACT_ID)

        if (contactId == null) {
            supportActionBar?.title = "Add contact"
        } else {
            supportActionBar?.title = "Edit contact"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_edit,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
