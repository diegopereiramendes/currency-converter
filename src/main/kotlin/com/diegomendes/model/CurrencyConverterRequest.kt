package com.diegomendes.model

import com.diegomendes.enuns.Currency

class CurrencyConverterRequest{
    var idUser: Int = 0
    var currencyOrigin : Currency
    var valueOrigin : Float = 0f
    var currencyDestiny : Currency

    constructor(idUser: Int, currencyOrigin: Currency,
                valueOrigin: Float,currencyDestiny : Currency) {
        this.idUser = idUser
        this.currencyOrigin = currencyOrigin
        this.valueOrigin = valueOrigin
        this.currencyDestiny = currencyDestiny
    }
}