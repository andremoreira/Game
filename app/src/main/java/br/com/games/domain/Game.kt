package br.com.games.domain

import java.io.Serializable

/**
 * Created by aluiz on 20/02/2019.
 */
data class Game( val _id: Long?, val name: String, val box: Box) : Serializable {
}