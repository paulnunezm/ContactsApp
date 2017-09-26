package com.nunez.androidcodetestpaulnunez.screens.contactDetails

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsPresenter(
        val view: DetailsContract.View,
        val interactor: DetailsContract.Interactor
) : DetailsContract.Presenter {

    var isFavorite = false

    override fun requestContact(id: String) {
        interactor.getContact(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showContact(it)
                    isFavorite = it.favorite
                    if(it.favorite) view.setFavorite()
                }, {
                    // show error
                })
    }

    override fun onEditButtonClicked() {
        view.gotoEditActivity()
    }

    override fun onFavoriteClick(id: String) {
        isFavorite = !isFavorite

        if(isFavorite){
            view.setFavorite()
            interactor.setToFavorites(id)
                    .subscribe()
        }else{
            view.unsetFavorite()
            interactor.removeFromFavorites(id)
                    .subscribe()
        }
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