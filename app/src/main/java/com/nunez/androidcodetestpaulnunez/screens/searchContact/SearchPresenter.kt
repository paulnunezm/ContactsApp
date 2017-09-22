package com.nunez.androidcodetestpaulnunez.screens.searchContact

import com.nunez.androidcodetestpaulnunez.entities.Contact

class SearchPresenter(
        val view: SearchContract.View,
        val interactor: SearchContract.Interactor
) : SearchContract.Presenter {

    override fun searchContacts(query: String) {
        interactor.getContacts(query).subscribe({
            t: List<Contact>? ->
            t?.let {
                if (t.isNotEmpty()) {
                    view.showContacts(t)
                }else {
                    view.showEmtpyScreen()
                }
            }
        },{
            view.showEmtpyScreen()
        })
    }

    override fun onContactCliked(id: String) {
        view.goToDetailsActivity(id)
    }
}