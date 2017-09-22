package com.nunez.androidcodetestpaulnunez.screens.contactList

import android.content.Context
import com.github.javafaker.Faker
import com.nunez.androidcodetestpaulnunez.R
import com.nunez.androidcodetestpaulnunez.entities.Address
import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.entities.Email
import com.nunez.androidcodetestpaulnunez.entities.PhoneNumber
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import io.reactivex.Completable
import io.reactivex.Single
import io.realm.RealmList
import java.util.concurrent.TimeUnit

class ListInteractor(
        val context: Context,
        val repository: RepositoryContract
) : ListContract.Interactor {

    fun getFakePhoneNumbers(faker: Faker): RealmList<PhoneNumber> {
        val phoneNumbers = RealmList<PhoneNumber>()
        with(faker) {
            val numbers1 = PhoneNumber()
            val numbers2 = PhoneNumber()
            numbers1.number = phoneNumber().cellPhone()
            numbers2.number = phoneNumber().phoneNumber()

            phoneNumbers.add(numbers1)
            phoneNumbers.add(numbers2)
        }
        return phoneNumbers
    }

    fun getFakeEmails(name: String, lastName: String): RealmList<Email> {
        val emails = RealmList<Email>()
        val email1 = Email()
        val email2 = Email()
        email1.email = name + lastName + "@gmail.com"
        email2.email = name + "_" + lastName + "@facebook.com"

        emails.add(email1)
        emails.add(email2)

        return emails
    }

    fun getFakeAddresses(faker: Faker): RealmList<Address> {
        val addresses = RealmList<Address>()
        val ad1 = Address()
        val ad2 = Address()
        ad1.address = faker.address().fullAddress()
        ad2.address = faker.address().fullAddress()

        addresses.add(ad1)
        addresses.add(ad2)

        return addresses

    }

    fun getFakeContact(faker: Faker, isFavorite: Boolean = false): Contact {

        var contact = Contact()

        with(faker) {
            val name = name().firstName()
            val lastName = name().lastName()

             contact = Contact(
                    "",
                    name,
                    lastName,
                    isFavorite,
                    date().past(15 * 360, TimeUnit.DAYS).toString(),
                    getFakePhoneNumbers(faker),
                    getFakeAddresses(faker),
                    getFakeEmails(name, lastName)
             )
        }

        return contact
    }

    override fun getContacts(): Single<List<Contact>> {
        val prefString = context.getString(R.string.pref_initialized)
        val pref = context.getSharedPreferences("prefs", 0)
        val hasBeenInitialized = pref.getBoolean(prefString, false)

        if (hasBeenInitialized)
            return repository.read()
        else {
            val editor = pref.edit()
            editor.putBoolean(prefString, true)
            editor.apply()

            return addFakeContacts()
        }

    }

    override fun deleteContact(id: String): Completable {
        return repository.delete(id)
    }

    override fun addFakeContacts(): Single<List<Contact>> {
        val faker = Faker()
        val contacts = ArrayList<Contact>()

        for (i in 0..10) {
            val contact = getFakeContact(faker, false)
            repository.create(contact)
            contacts.add(contact)
        }

        for (i in 0..6) {
            val contact = getFakeContact(faker, true)
            repository.create(contact)
            contacts.add(contact)
        }
        return Single.just(contacts)
    }

}