package br.com.games

import android.app.Application
import android.util.Log
import java.lang.IllegalStateException

/**
 * Created by aluiz on 20/02/2019.
 */
class GamesApplicationApplication : Application() {
    private val TAG_GAME = "GamesApplication"

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: GamesApplicationApplication? = null

        fun getInstance(): GamesApplicationApplication {
            if (appInstance == null) {
                throw IllegalStateException("AndroidManifest.xml")
            }
            return appInstance!!
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG_GAME, "onTerminate()")
    }
}