package com.bertiland.kori.common.models

import api.ttt.orm.field.advanced.BoolField
import api.ttt.orm.model.TMignore
import api.ttt.orm.model.TModel
import api.ttt.orm.ms.Tms

class IntroState : TModel<IntroState>() {
    var isLoading =  BoolField().defaults(true)
    var onLogin =  BoolField().defaults(false)
    var onSignup =  BoolField().defaults(false)

    @TMignore var isLoading_ by isLoading
    @TMignore var onLogin_ by onLogin
    @TMignore var onSignup_ by onSignup

    companion object{
        val tms = Tms(IntroState::class)
    }
}