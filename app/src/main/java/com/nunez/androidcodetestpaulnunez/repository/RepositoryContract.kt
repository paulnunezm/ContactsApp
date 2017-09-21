package com.nunez.androidcodetestpaulnunez.repository

import com.nunez.androidcodetestpaulnunez.entities.Contact

interface RepositoryContract {
    fun create(contact: Contact)

    fun read(): List<Contact>

    fun update(contact: Contact)

    fun delete(id:String)

    fun getContact(contactId: String): Contact

    fun search(query: String): List<Contact>
}
