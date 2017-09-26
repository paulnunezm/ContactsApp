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

    companion object {
        val TAG = "AddEditPresenter"
    }
    var onEditMode = false
    var isFavorite = false
    var contactId = ""


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
        this.contactId = contactId
        onEditMode = true
        interactor.getContact(contactId).subscribe({
            t: Contact? ->
            t?.let {
                isFavorite = t.favorite
                view.showContact(t)
            }
        }, {
            // show error message
        })
    }

    override fun onSaveClicked() {

        // do not save if validation fails
        if (!validateFields())
            return Unit  // Return unit is like return nothing

        val contact = Contact(
                contactId,
                view.getFirstNameValue(),
                view.getLastNameValue(),
                isFavorite,
                view.getBirthDayValue(),
                phonesToRealmList(view.getPhoneValues()),
                addressesToRealmList(view.getAddressValues()),
                emailsToRealmList(view.getEmailValues())
        )

        if(onEditMode){
            interactor.updateContact(contact).subscribe({
                view.showContactSaved() // message
                Log.d(TAG, "Updated")
            }, {
                // show error message
                Log.d(TAG, "Update error $it")
            })

        }else{
            interactor.saveContact(contact).subscribe({
                view.showContactSaved() // message
            }, {
                // show error message
            })
        }
    }

    override fun onDeleteClicked(contactId: String) {
        interactor.deleteContact(contactId).subscribe({
            view.closeView()
        }, {
            Log.d("AddEditPresenter", "On error")
        })
    }

    override fun validateFields(): Boolean {
        if (view.getFirstNameValue().isEmpty() || // No name set
                view.getPhoneValues().size < 1 || // At least one phone numbers set
                view.getEmailValues().size < 1) { // At least one email set

            // As Emails and Phone fields are custom field groups
            // the errors are set by the class itself when the getFieldValue method
            // is called.
            if (view.getFirstNameValue().isEmpty()) view.showFirstNameError()

            return false
        }

        return true
    }
}