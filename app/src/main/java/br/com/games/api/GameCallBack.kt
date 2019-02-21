package br.com.games.api

/**
 * Created by aluiz on 20/02/2019.
 */
interface GameCallBack<T> {

    fun onSuccess(response: T)

    fun onError(response: T?)
}