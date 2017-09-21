package com.nunez.androidcodetestpaulnunez.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Contact(
        @PrimaryKey var id: String = "",
        var firstName: String = "",
        var lastName: String = "",
        var favorite: Boolean = false,
        var birthday: String = "",
        var phonenumbers: RealmList<PhoneNumber> = RealmList(),
        var adresses: RealmList<Address> = RealmList(),
        var emails: RealmList<Email> = RealmList()
) : RealmObject()

open class PhoneNumber(var number: String = "") : RealmObject()

open class Address(var address: String = "") : RealmObject()

open class Email(var email: String = "") : RealmObject()