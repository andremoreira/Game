package br.com.games.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by aluiz on 20/02/2019.
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}