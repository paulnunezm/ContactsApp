package com.nunez.androidcodetestpaulnunez.screens.searchContact

import com.nunez.androidcodetestpaulnunez.entities.Contact
import io.reactivex.Single

interface SearchContract {

    interface View {
        fun contactClickListener(id: String)
        fun showEmtpyScreen()
        fun showContacts(contacts: List<Contact>)
        fun goToDetailsActivity(contactId: String)
        fun onSearchTextChange(query: String)
    }

    interface Presenter {
        fun searchContacts(query: String)
        fun onContactCliked(id: String)
    }

    interface Interactor {
        fun getContacts(query:String): Single<List<Contact>>
    }
}