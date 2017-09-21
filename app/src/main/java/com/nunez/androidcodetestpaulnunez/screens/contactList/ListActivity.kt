package com.nunez.androidcodetestpaulnunez.screens.contactList

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import com.nunez.androidcodetestpaulnunez.R
import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.screens.addEditContact.AddEditActivity
import com.nunez.androidcodetestpaulnunez.screens.contactDetails.DetailsActivity
import com.nunez.androidcodetestpaulnunez.screens.searchContact.SearchActivity
import kotlinx.android.synthetic.main.list_contact_activity.*

class ListActivity : AppCompatActivity(), ListContract.View {


    lateinit var presenter: ListContract.Presenter
    lateinit var interactor: ListContract.Interactor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_contact_activity)
        setSupportActionBar(toolbar)

        toolbar.apply {
            title = "Search contact"
            setNavigationIcon(R.drawable.ic_search)
            setNavigationOnClickListener { toolbarClickListener() }
            setOnClickListener { toolbarClickListener() }
        }

        fab.setOnClickListener { }


    }

    override fun addContactClickListener() {
        presenter.onAdContactClicked()
    }

    override fun contactClickListener(id: String) {
        presenter.onContactCliked(id)
    }

    override fun contactLongClickListener(id: String) {
        presenter.onContactLongCliked(id)
    }

    override fun toolbarClickListener() {
        presenter.onToolbarClicked()
    }

    override fun showContacts(contacts: List<Contact>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showOptionsModalBottomSheet(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorMessage(message: String) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun dismissModal() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun goToAddActivity() {
        val intent = Intent(this, AddEditActivity::class.java)
        startActivity(intent)
    }

    override fun goToEditActivity(contactId: String) {
        val intent = Intent(this, AddEditActivity::class.java)
        startActivity(intent)
    }

    override fun goToDetailsActivity(contactId: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    override fun goToSearchActivity(contactId: String) {
        val intent = Intent(this, SearchActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, toolbar, "toolbar")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            startActivity(intent, options.toBundle())
        else
            startActivity(intent)
    }

}
