package com.bertiland.kori.launcher

import android.app.Application
import api.ttt.orm.db.Tcontext
import api.ttt.orm.db.Tdb
import api.ttt.orm.modeler.TModeler
import com.bertiland.kori.users.models.User

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
                    .dbDir("/tmp/sql")
                    .dbName("test.db")
                    .accept(User().clazz().getPackage())
                    .get()!!
            )
    }
}