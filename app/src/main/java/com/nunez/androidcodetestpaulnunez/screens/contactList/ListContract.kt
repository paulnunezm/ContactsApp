package com.nunez.androidcodetestpaulnunez.screens.contactList

import com.nunez.androidcodetestpaulnunez.entities.Contact
import io.reactivex.Completable
import io.reactivex.Single

interface ListContract {

    interface View {
        fun addContactClickListener()
        fun contactClickListener(id: String, imageView: android.view.View)
        fun contactLongClickListener(id: String)
        fun toolbarClickListener()
        fun showEmtpyScreen()
        fun hideEmptyScreen()
        fun showContacts(contacts: List<Contact>)
        fun showOptionsModalBottomSheet(id: String)
        fun showErrorMessage(message: String)
        fun dismissModal()
        fun goToAddActivity()
        fun goToEditActivity(contactId: String)
        fun goToDetailsActivity(contactId: String, imageView: android.view.View)
        fun goToSearchActivity()
    }

    interface Presenter {
        fun requestContacts()
        fun onAdContactClicked()
        fun onContactCliked(id: String, imageView: android.view.View)
        fun onContactLongCliked(id: String)
        fun onContactDeleteClicked(id: String)
        fun onContactEditClicked(id: String)
        fun onToolbarClicked()
    }

    interface Interactor {
        fun getContacts(): Single<List<Contact>>
        fun deleteContact(id: String): Completable
        fun addFakeContacts(): Single<List<Contact>>
    }

}