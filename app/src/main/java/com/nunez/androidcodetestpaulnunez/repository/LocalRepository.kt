package com.nunez.androidcodetestpaulnunez.repository

import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.vicpin.krealmextensions.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.Sort
import java.util.*

class LocalRepository(
        val realm: Realm
) : RepositoryContract {

    override fun create(contact: Contact): Completable {
        return Completable.create {
            contact.id = UUID.randomUUID().toString()

            contact.apply {
                firstName = contact.firstName.toLowerCase()
                lastName = contact.lastName.toLowerCase()
            }

            contact.create()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun read(): Single<List<Contact>> {
        return Single.create {
            emitter ->
            val list = Contact().querySorted("firstName", Sort.ASCENDING)
            list.map {
                it.firstName = it.firstName.capitalize()
                it.lastName = it.lastName.capitalize()
            }
            emitter.onSuccess(list)
        }
    }

    override fun update(contact: Contact): Completable {
        return Completable.create {
            contact.save()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun delete(id: String): Completable {
        return Completable.create {
            emitter ->
            Contact().delete {
                it.equalTo("id", id)
            }

            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getContact(contactId: String): Single<Contact> {
        return Single.create {
            emitter ->
            val contact = Contact().queryFirst { it.equalTo("id", contactId) }

            contact?.let {
                it.firstName = it.firstName.capitalize()
                it.lastName = it.lastName.capitalize()
                emitter.onSuccess(it)
            }
            emitter.onSuccess(Contact())
        }
    }

    override fun search(query: String): Single<List<Contact>> {
        return Single.create {
            val results = realm.where(Contact::class.java)
                    .contains("firstName", query)
                    .or()
                    .contains("lastName", query)
                    .or()
                    .contains("phonenumbers.number", query)
                    .findAllSorted("firstName")


            val list: List<Contact> = realm.copyFromRealm(results)
            list.map {
                it.firstName = it.firstName.capitalize()
                it.lastName = it.lastName.capitalize()
            }
            it.onSuccess(list)
        }
    }
}