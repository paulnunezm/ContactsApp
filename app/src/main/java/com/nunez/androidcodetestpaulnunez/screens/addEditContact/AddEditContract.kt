package com.nunez.androidcodetestpaulnunez.screens.addEditContact

import com.nunez.androidcodetestpaulnunez.entities.Contact
import io.reactivex.Completable
import io.reactivex.Single

interface AddEditContract {

    interface View {
        fun showContact(contact: Contact)
        fun getFirstNameValue():String
        fun getLastNameValue(): String
        fun getPhoneValues(): ArrayList<String>
        fun getEmailValues(): ArrayList<String>
        fun getAddressValues(): ArrayList<String>
        fun getBirthDayValue(): String
        fun showFirstNameError()
        fun hideFirstNameError()
        fun showContactSaved()
        fun saveButtonClickListener()
        fun deleteButtonClickListener()
        fun birthdayClickListener()
        fun closeView()
    }

    interface Presenter {
        fun requestContact(contactId: String)
        fun onSaveClicked()
        fun onDeleteClicked(contactId: String)
        fun validateFields(): Boolean
    }

    interface Interactor {
        fun deleteContact(contactId: String): Completable
        fun getContact(contactId: String): Single<Contact>
        fun saveContact(contact:Contact): Completable
        fun updateContact(contact: Contact) : Completable
    }
}
