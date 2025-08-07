package com.bertiland.kori._models

import api.ttt.orm.field.advanced.ListField
import api.ttt.orm.field.advanced.TimeField
import api.ttt.orm.field.common.TextField
import api.ttt.orm.model.TMignore
import api.ttt.orm.model.TModel
import api.ttt.orm.ms.Tms

class Client : TModel<Client>() {
    var name = TextField()
    var email = TextField()
    var dob = TimeField().format(TimeField.Format.DATE)
    var friends = ListField(Client::class)

    @TMignore var name_ by name
    @TMignore var email_ by email
    //@TMignore var dob_ by dob
    @TMignore var friends_ by friends

    companion object {
        val tms = Tms(Client::class)
    }
}