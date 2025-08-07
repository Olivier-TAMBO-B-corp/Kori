package com.bertiland.kori._models

import api.ttt.orm.field.advanced.ModelField
import api.ttt.orm.field.common.FloatField
import api.ttt.orm.field.common.TextField
import api.ttt.orm.model.TMignore
import api.ttt.orm.model.TModel
import api.ttt.orm.ms.Tms

class Payment : TModel<Payment>() {
    var phone = TextField()
    var name = TextField()
    var mode = TextField().defaults("Mobile Money")
    var amount = FloatField()
    var code = TextField()
    var ref = TextField().defaults("Payment d une commande")
    var command = ModelField(Command::class)

    @TMignore var phone_ by phone
    @TMignore var name_ by name
    @TMignore var mode_ by mode
    @TMignore var amount_ by amount
    @TMignore var code_ by code
    @TMignore var ref_ by ref
    @TMignore var command_ by command

    companion object {
        val tms = Tms(Payment::class)
    }
}