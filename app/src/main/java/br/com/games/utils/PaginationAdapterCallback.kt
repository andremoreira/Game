package br.com.games.utils

import br.com.games.domain.Top
/**
 * Created by aluiz on 20/02/2019.
 */
interface PaginationAdapterCallback {

    fun pageLoad()

    fun onClick(top: Top)
}