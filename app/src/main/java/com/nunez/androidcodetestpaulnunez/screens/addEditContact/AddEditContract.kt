package com.nunez.androidcodetestpaulnunez.screens.addEditContact

import com.nunez.androidcodetestpaulnunez.entities.Contact
import io.reactivex.Completable
import io.reactivex.Single

interface AddEditContract {

    interface View {
        fun showContact(contact: Contact)
        fun getFieldValues(): Map<String, Any>
        fun getContact(): Contact
        fun showFirstNameError()
        fun showPhoneError()
        fun showEmailError()
    }

    interface Presenter {
        fun requestContact(contactId: String)
        fun onSaveClicked()
        fun validateFields()
    }

    interface Interactor {
        fun deleteContact(contactId: String): Completable
        fun getContact(contactId: String): Single<Contact>
        fun saveContact(contact:Contact): Completable
        fun updateContact(contact: Contact) : Completable
    }
}
