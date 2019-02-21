package br.com.games.base

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by aluiz on 20/02/2019.
 */
open class BaseActivity : AppCompatActivity() {

    protected val context: Context get() = this

    fun toast(message: CharSequence, length: Int = Toast.LENGTH_SHORT) =
            Toast.makeText(this, message, length).show()
}