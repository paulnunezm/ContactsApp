package com.nunez.androidcodetestpaulnunez.screens.addEditContact

import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddEditInteractor(
        val repository: RepositoryContract
) : AddEditContract.Interactor {

    override fun deleteContact(contactId: String): Completable {

        return Completable.create {
            emitter ->
            repository.delete(contactId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        emitter.onComplete()
                    })
        }
    }

    override fun getContact(contactId: String): Single<Contact> {
        return Single.create {
            emitter ->
            repository.getContact(contactId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        emitter.onSuccess(it)
                    }, {
                        // on error
                    })
        }
    }

    override fun saveContact(contact: Contact): Completable {
        return Completable.create {
            emitter ->
            repository.create(contact).subscribe {
                emitter.onComplete()
            }
        }
    }

    override fun updateContact(contact: Contact): Completable {
        return Completable.create {
            emitter ->
            repository.update(contact).subscribe {
                emitter.onComplete()
            }
        }
    }

}