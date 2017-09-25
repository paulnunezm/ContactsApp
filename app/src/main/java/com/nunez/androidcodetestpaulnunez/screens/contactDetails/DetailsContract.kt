package com.nunez.androidcodetestpaulnunez.screens.contactDetails

import com.nunez.androidcodetestpaulnunez.entities.Contact
import io.reactivex.Single

interface DetailsContract {

    interface View {
        fun showContact(contact: Contact)
        fun phoneClickListener(number: String)
        fun birthdayClickListener(birthday: String)
        fun addressClickListener(address: String)
        fun favoriteButtonListener()
        fun editButtonListener()
        fun gotoEditActivity()
    }

    interface Presenter {
        fun requestContact(id: String)
        fun onFavoriteClick(id: String)
        fun onPhoneClick(number:String)
        fun onBirthdayClick(birthday: String)
        fun onAddressClick(address: String)
        fun onEditButtonClicked()
    }

    interface Interactor {
        fun getContact(id:String): Single<Contact>
        fun setToFavorites(id: String)
        fun removeFromFavorites(id: String)
    }
}