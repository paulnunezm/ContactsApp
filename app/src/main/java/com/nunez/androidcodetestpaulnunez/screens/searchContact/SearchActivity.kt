package com.nunez.androidcodetestpaulnunez.screens.searchContact

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import com.nunez.androidcodetestpaulnunez.R
import kotlinx.android.synthetic.main.search_contact_activity_.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_contact_activity_)
        setSupportActionBar(toolbar)

        toolbar.apply {
            inflateMenu(R.menu.menu_search_activity)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {  }
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val transition = Slide(Gravity.BOTTOM)
            transition.excludeTarget(android.R.id.statusBarBackground, true)
            transition.excludeTarget(android.R.id.navigationBarBackground, true)
            transition.excludeTarget(R.id.appBarLayout, true)
            window.enterTransition = transition
            window.exitTransition = Fade()
        }
    }
}
