package com.nunez.androidcodetestpaulnunez.screens.contactList

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListPresenter(
        val view: ListContract.View,
        val interactor: ListContract.Interactor
) : ListContract.Presenter {

    fun showError() {
        view.showErrorMessage("An error just occurred")
    }

    override fun requestContacts() {
        interactor.getContacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
            if(it.isNotEmpty()){
                view.showContacts(it)
                view.hideEmptyScreen()
            }else
                view.showEmtpyScreen()
        }, {
            showError()
        })
    }

    override fun onAdContactClicked() {
        view.goToAddActivity()
    }

    override fun onContactCliked(id: String) {
        view.goToDetailsActivity(id)
    }

    override fun onContactLongCliked(id: String) {
        view.showOptionsModalBottomSheet(id)
    }

    override fun onContactDeleteClicked(id: String) {
        interactor.deleteContact(id).subscribe({
            requestContacts()
        },{
            showError()
        })
    }

    override fun onContactEditClicked(id: String) {
        view.goToEditActivity(id)
    }

    override fun onToolbarClicked() {
        view.goToSearchActivity()
    }
}