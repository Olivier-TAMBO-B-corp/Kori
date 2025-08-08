package com.bertiland.kori.launcher

import android.app.Application
import api.ttt.orm.db.Tcontext
import api.ttt.orm.db.Tdb
import api.ttt.orm.modeler.TModeler
import api.ttt.orm.ms.ext.prepare
import com.bertiland.kori.common.models.IntroState

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        tmInit();
    }

    fun tmInit() {
        val context = Tcontext(this)

        TModeler
            .start()
            .init(
                Tdb.Builder()
                    .type(Tdb.Type.SQLITE)
                    .context(context)
                    .dbDir("db/")
                    .dbName("system_meta.db")
                    .accept(IntroState().clazz().getPackage())
                    .get()!!
            )

        IntroState.tms.prepare()
    }
}