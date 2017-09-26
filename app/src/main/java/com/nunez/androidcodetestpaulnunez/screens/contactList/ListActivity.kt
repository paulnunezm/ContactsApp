package com.nunez.androidcodetestpaulnunez.screens.contactList

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.nunez.androidcodetestpaulnunez.R
import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.repository.LocalRepository
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import com.nunez.androidcodetestpaulnunez.screens.addEditContact.AddEditActivity
import com.nunez.androidcodetestpaulnunez.screens.contactDetails.DetailsActivity
import com.nunez.androidcodetestpaulnunez.screens.searchContact.SearchActivity
import com.nunez.androidcodetestpaulnunez.views.ContactListAdapter
import com.nunez.androidcodetestpaulnunez.views.ListOptionsModal
import io.realm.Realm
import kotlinx.android.synthetic.main.content_contact_list.*
import kotlinx.android.synthetic.main.list_contact_activity.*


class ListActivity : AppCompatActivity(), ListContract.View {

    companion object {
        const val TAG = "ListActivity"
    }

    lateinit var presenter: ListContract.Presenter
    lateinit var interactor: ListContract.Interactor
    lateinit var repository: RepositoryContract

    var isResuming = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_contact_activity)
        setTheme(R.style.AppTheme)


        val realm = Realm.getDefaultInstance()
        repository = LocalRepository(realm)
        interactor = ListInteractor(this, repository)
        presenter = ListPresenter(this, interactor)

        toolbar.apply {
            title = "Search contact"
            setNavigationIcon(R.drawable.ic_search)
            setNavigationOnClickListener { toolbarClickListener() }
            setOnClickListener { toolbarClickListener() }
        }

        fab.setOnClickListener { presenter.onAdContactClicked() }

        contactsRecycler.layoutManager = LinearLayoutManager(this)
        favoritesRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        presenter.requestContacts()
    }

    override fun onResume() {
        super.onResume()
        isResuming = true
//        presenter.requestContacts()
    }

    override fun addContactClickListener() {
        presenter.onAdContactClicked()
    }

    override fun contactClickListener(id: String, imageView: View) {
        presenter.onContactCliked(id, imageView )
    }

    override fun contactLongClickListener(id: String) {
        presenter.onContactLongCliked(id)
    }

    override fun toolbarClickListener() {
        presenter.onToolbarClicked()
    }

    override fun showContacts(contacts: List<Contact>) {
        val favoriteContacts = contacts.filter {
            it.favorite
        }

        val adapter = ContactListAdapter(contacts, false, {
            id, imageView ->
            contactClickListener(id, imageView)
        }, {
            id, transitionName ->
            contactLongClickListener(id)
        })

        val favoritesAdapter = ContactListAdapter(favoriteContacts, true, {
            id, imageView ->
            contactClickListener(id, imageView)
        }, {
            id, transitionName ->
            contactLongClickListener(id)
        })

        contactsRecycler.adapter = adapter
        favoritesRecycler.adapter = favoritesAdapter

        if (!isResuming) {
            contactsRecycler.scheduleLayoutAnimation()
            favoritesRecycler.scheduleLayoutAnimation()
        }

    }

    override fun showEmtpyScreen() {
        emptyListMessage.visibility = View.VISIBLE
        listContainer.visibility = View.GONE
    }

    override fun hideEmptyScreen() {
        emptyListMessage.visibility = View.GONE
        listContainer.visibility = View.VISIBLE
    }

    override fun showOptionsModalBottomSheet(id: String) {
        val modalFrag = ListOptionsModal()
        val bundle = Bundle()
        bundle.putString("id", id)
        modalFrag.arguments = bundle

        modalFrag.editClickListener = {
            id ->
            presenter.onContactEditClicked(id)
            modalFrag.dismiss()
        }

        modalFrag.onDeleteClickListener = {
            id ->
            presenter.onContactDeleteClicked(id)
            modalFrag.dismiss()
        }

        modalFrag.show(supportFragmentManager, "optionsModal")
    }

    override fun showErrorMessage(message: String) {
//        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show()
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
        intent.putExtra(AddEditActivity.EXTRA_CONTACT_ID, contactId)
        startActivity(intent)
    }

    override fun goToDetailsActivity(contactId: String, imageView: View) {
        val intent = Intent(this, DetailsActivity::class.java)

        intent.putExtra(DetailsActivity.EXTRA_CONTACT_ID, contactId)
        intent.putExtra(DetailsActivity.EXTRA_TRANSITION_NAME, imageView.transitionName)

        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, imageView, imageView.transitionName)

        startActivity(intent, options.toBundle())
    }

    override fun goToSearchActivity() {
        val intent = Intent(this, SearchActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, toolbar, "toolbar")
        startActivity(intent, options.toBundle())
    }

}
