package com.nunez.androidcodetestpaulnunez.screens.contactList

import com.nunez.androidcodetestpaulnunez.entities.Contact
import io.reactivex.Completable
import io.reactivex.Single

interface ListContract {

    interface View {
        fun contactClickListener()
        fun contactLongClickListener()
        fun toolbarClickListener()
        fun showContacts(contacts: List<Contact>)
        fun showContactDetails(id: String)
        fun showOptionsModalBottomSheet(id: String)
        fun showErrorMessage()
        fun dismissModal()
        fun goToAddActivity()
        fun goToEditActivity(contactId:String)
        fun goToDetailsActivity(contactId: String)
    }

    interface Presenter {
        fun showErrorMessage()
        fun requestContacts()
        fun addContactClicked()
        fun onContactCliked(id: String)
        fun onContactLongCliked(id: String)
        fun onContactDeleteClicked(id: String)
        fun onContactEditClicked(id: String)
    }

    interface Interactor {
        fun getContacts(): Single<List<Contact>>
        fun deleteContact(id: String): Completable
        fun addFakeContacts(): Single<List<Contact>>
    }

}