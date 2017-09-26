package com.nunez.androidcodetestpaulnunez.screens.searchContact

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.transition.Fade
import android.transition.Slide
import android.view.Gravity
import android.view.View
import com.nunez.androidcodetestpaulnunez.R
import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.repository.LocalRepository
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import com.nunez.androidcodetestpaulnunez.screens.contactDetails.DetailsActivity
import com.nunez.androidcodetestpaulnunez.views.ContactListAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.content_search_contact.*
import kotlinx.android.synthetic.main.search_contact_activity_.*

class SearchActivity : AppCompatActivity(), SearchContract.View {

    lateinit var repository: RepositoryContract
    lateinit var interactor: SearchContract.Interactor
    lateinit var presenter: SearchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_contact_activity_)

        setUpAnimations()
        instantateDepenencies()
        setUpViews()
    }

    private fun instantateDepenencies(){
        repository = LocalRepository(Realm.getDefaultInstance())
        interactor = SearchInteractor(repository)
        presenter = SearchPresenter(this, interactor)
    }

    private fun setUpViews(){
        toolbar.apply {
            inflateMenu(R.menu.menu_search_activity)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                searchInput.setText("")
                true
            }
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onSearchTextChange(p0.toString())
            }
        })

        searchContactsRecycler.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val transition = Slide(Gravity.BOTTOM)
            transition.excludeTarget(android.R.id.statusBarBackground, true)
            transition.excludeTarget(android.R.id.navigationBarBackground, true)
            transition.excludeTarget(R.id.appBarLayout, true)
            window.enterTransition = transition
            window.exitTransition = Fade()
        }
    }

    override fun contactClickListener(id: String) {
        presenter.onContactCliked(id)
    }

    override fun showEmptyScreen() {
        emptyListImage.visibility = View.VISIBLE
        searchContactsRecycler.visibility = View.GONE
    }

    override fun hideEmptyScreen() {
        emptyListImage.visibility = View.GONE
        searchContactsRecycler.visibility = View.VISIBLE
    }

    override fun showContacts(contacts: List<Contact>) {
        searchContactsRecycler.adapter = ContactListAdapter(contacts, false, {
            id ->
            contactClickListener(id)
        }, {
            // long click is not implemented here
        })
    }

    override fun goToDetailsActivity(contactId: String) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EXTRA_CONTACT_ID, contactId)
        startActivity(intent)
    }

    override fun onSearchTextChange(query: String) {
        presenter.searchContacts(query)
    }

}
