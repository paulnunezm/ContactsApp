package com.nunez.androidcodetestpaulnunez.screens.contactDetails

import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsInteractor(
        val repository: RepositoryContract
) : DetailsContract.Interactor {

    override fun getContact(id: String): Single<Contact> {
        return Single.create {
            emitter ->
            repository.getContact(id).subscribe({
                emitter.onSuccess(it)
            }, {
                emitter.onError(it)
            })
        }
    }

    override fun setToFavorites(id: String): Completable {
        return Completable.create({
            emitter ->
            repository.getContact(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        contact ->
                        contact.favorite = true
                        repository.update(contact).subscribe {
                            emitter.onComplete()
                        }
                    }, {
                        emitter.onError(it)
                    })
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeFromFavorites(id: String): Completable {
        return Completable.create({
            emitter ->
            repository.getContact(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        contact ->
                        contact.favorite = false
                        repository.update(contact).subscribe {
                            emitter.onComplete()
                        }
                    }, {
                        emitter.onError(it)
                    })
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}