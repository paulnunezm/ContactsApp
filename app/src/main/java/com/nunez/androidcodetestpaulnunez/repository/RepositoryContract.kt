package com.nunez.androidcodetestpaulnunez.repository

import com.nunez.androidcodetestpaulnunez.entities.Contact
import io.reactivex.Completable
import io.reactivex.Single

interface RepositoryContract {
    fun create(contact: Contact): Completable

    fun read(): Single<List<Contact>>

    fun update(contact: Contact): Completable

    fun delete(id:String): Completable

    fun getContact(contactId: String): Single<Contact>

    fun search(query: String): Single<List<Contact>>
}
