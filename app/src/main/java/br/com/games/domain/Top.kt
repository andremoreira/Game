package br.com.games.domain

import java.io.Serializable

/**
 * Created by aluiz on 20/02/2019.
 */
data class Top(val game: Game?, val viewers: String, val channels: String) : Serializable {
}