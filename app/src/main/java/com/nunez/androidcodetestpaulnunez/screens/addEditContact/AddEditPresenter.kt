package com.nunez.androidcodetestpaulnunez.screens.addEditContact

import android.util.Log
import com.nunez.androidcodetestpaulnunez.entities.Address
import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.entities.Email
import com.nunez.androidcodetestpaulnunez.entities.PhoneNumber
import io.realm.RealmList

class AddEditPresenter(
        val view: AddEditContract.View,
        val interactor: AddEditContract.Interactor
) : AddEditContract.Presenter {

    var onEditMode = false


    private fun phonesToRealmList(phoneValues: ArrayList<String>): RealmList<PhoneNumber> {
        val phoneList = RealmList<PhoneNumber>()
        phoneValues.mapTo(phoneList) { PhoneNumber(it) }
        return phoneList
    }

    private fun emailsToRealmList(emailValues: ArrayList<String>): RealmList<Email> {
        val emailList = RealmList<Email>()
        emailValues.mapTo(emailList) { Email(it) }
        return emailList
    }

    private fun addressesToRealmList(addressValues: ArrayList<String>): RealmList<Address> {
        val addressList = RealmList<Address>()
        addressValues.mapTo(addressList) { Address(it) }
        return addressList
    }

    override fun requestContact(contactId: String) {
        onEditMode = true
        interactor.getContact(contactId).subscribe({
            t: Contact? ->
            t?.let {
                view.showContact(t)
            }
        }, {
            // show error message
        })
    }

    override fun onSaveClicked() {

        // TODO: get favorite

        val contact = Contact(
                "",
                view.getFirstNameValue(),
                view.getLastNameValue(),
                false,
                view.getBirthDayValue(),
                phonesToRealmList(view.getPhoneValues()),
                addressesToRealmList(view.getAddressValues()),
                emailsToRealmList(view.getAddressValues())
        )

        interactor.saveContact(contact).subscribe({
            view.showContactSaved()
        }, {
            // show error message
        })
    }

    override fun onDeleteClicked(contactId: String) {
        Log.d("AddEditPresenter", "onDeleteClicked")
        interactor.deleteContact(contactId).subscribe({
            Log.d("AddEditPresenter", "Going To closeView")
            view.closeView(true)
        }, {
            Log.d("AddEditPresenter", "On error")
        })
    }

    override fun validateFields() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}