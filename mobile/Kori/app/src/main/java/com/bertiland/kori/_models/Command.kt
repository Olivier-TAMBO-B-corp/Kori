package com.bertiland.kori._models

import api.ttt.orm.field.advanced.ModelField
import api.ttt.orm.model.TMignore
import api.ttt.orm.model.TModel
import api.ttt.orm.ms.Tms

class Command : TModel<Command>() {
    var client = ModelField(Client::class)
    var product = ModelField(Product::class)

    @TMignore var client_ by client
    @TMignore var product_ by product

    companion object {
        val tms = Tms(Command::class)
    }
}