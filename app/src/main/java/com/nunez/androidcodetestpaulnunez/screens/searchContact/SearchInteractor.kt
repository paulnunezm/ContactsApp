package com.nunez.androidcodetestpaulnunez.screens.searchContact

import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import io.reactivex.Single

class SearchInteractor(
        val repository: RepositoryContract
) : SearchContract.Interactor {

    override fun getContacts(query: String): Single<List<Contact>> {
        return repository.search(query)
    }
}