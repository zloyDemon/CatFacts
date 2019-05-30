package com.company.mbtest

import io.realm.RealmObject

public open class FavouriteFact(public open  var id : Int = 0,
                                public open var text : String="") : RealmObject() {

}