package br.com.games.domain

/**
 * Created by aluiz on 20/02/2019.
 */
data class GameResponse(val _total: Int?, val top: List<Top>?,
        val msgError: String?) {
}