package com.nunez.androidcodetestpaulnunez.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Contact(
        @PrimaryKey var id: String = "",
        var firstName: String = "",
        var lastName: String = "",
        var phonenumbers: RealmList<Phonenumber> = RealmList(),
        var adresses: RealmList<Address> = RealmList()
) : RealmObject()

open class Phonenumber(
        var number: String = ""
) : RealmObject()

open class Address(
        var address: String = ""
): RealmObject()