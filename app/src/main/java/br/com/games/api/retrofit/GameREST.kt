package br.com.games.retrofit

import br.com.games.api.GameApi
import br.com.games.domain.GameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by aluiz on 20/02/2019.
 */
interface GameREST {



    @Headers(GameApi.ID,GameApi.JSON)

    @GET(GameApi.TOP)

    fun getGames(@Query(GameApi.LIMIT) limit: Int, @Query(GameApi.OFF) offset: Int): Call<GameResponse>
}