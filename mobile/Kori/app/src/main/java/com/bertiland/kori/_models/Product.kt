package com.bertiland.kori._models

import api.ttt.orm.field.common.FloatField
import api.ttt.orm.field.common.TextField
import api.ttt.orm.model.TMignore
import api.ttt.orm.model.TModel
import api.ttt.orm.ms.Tms

class Product : TModel<Product>() {
    var name = TextField()
    var price = FloatField().defaults(0.0)

    @TMignore var name_ by name
    @TMignore var price_ by price

    companion object {
        val tms = Tms(Product::class)
    }
}