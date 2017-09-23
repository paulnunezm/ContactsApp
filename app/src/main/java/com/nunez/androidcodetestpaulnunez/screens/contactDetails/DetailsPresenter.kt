package com.nunez.androidcodetestpaulnunez.screens.contactDetails

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsPresenter(
        val view: DetailsContract.View,
        val interactor: DetailsContract.Interactor
): DetailsContract.Presenter {

    override fun requestContact(id: String) {
        interactor.getContact(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showContact(it)
                },{
                    // show error
                })
    }

    override fun onFavoriteClick(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPhoneClick(number: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBirthdayClick(birthday: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddressClick(address: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}