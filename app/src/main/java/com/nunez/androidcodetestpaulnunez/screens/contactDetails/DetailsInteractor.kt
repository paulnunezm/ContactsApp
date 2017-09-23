package com.nunez.androidcodetestpaulnunez.screens.contactDetails

import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import io.reactivex.Single

class DetailsInteractor(
        val repositoryContract: RepositoryContract
): DetailsContract.Interactor {

    override fun getContact(id: String): Single<Contact> {
        return Single.create {
            emitter ->
            repositoryContract.getContact(id).subscribe({
                emitter.onSuccess(it)
            },{
                emitter.onError(it)
            })
        }
    }

    override fun setToFavorites(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeFromFavorites(id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}