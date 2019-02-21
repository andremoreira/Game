package br.com.games.api

import android.content.Context
import android.net.ConnectivityManager
import br.com.games.GamesApplicationApplication
import br.com.games.R
import br.com.games.domain.GameResponse
import br.com.games.retrofit.GameREST
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 * Created by aluiz on 20/02/2019.
 */


object GameService {

    private const val SUCESS = 200
    private var service: GameREST
    private const val LIMIT = 10

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY;

        val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(GameApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        service = retrofit.create(GameREST::class.java)
    }

    private fun isNetworkConnected(): Boolean {
        val isNet = GamesApplicationApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager?

        return isNet!!.activeNetworkInfo != null
    }

    fun getGames(callBackGeneric: GameCallBack<GameResponse>, offset: Int) {
        val call = service.getGames(LIMIT, offset)
        call.enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>?, response: Response<GameResponse>) {
                if (response.code() == SUCESS) {
                    val resp: GameResponse? = response.body()
                    callBackGeneric.onSuccess(resp!!)
                }
            }

            override fun onFailure(call: Call<GameResponse>?, t: Throwable?) {
                t?.printStackTrace()
                val error = fetchErrorMessage(t!!)
                callBackGeneric.onError(GameResponse(null, null, error))
            }
        })

    }


    private fun fetchErrorMessage(throwable: Throwable): String {
        var errorMsg = GamesApplicationApplication.getInstance().getResources().getString(R.string.msg_unknown)

        if (!isNetworkConnected()) {
            errorMsg = GamesApplicationApplication.getInstance()
                    .getResources().getString(R.string.error_internet)
        } else if (throwable is TimeoutException) {
            errorMsg = GamesApplicationApplication.getInstance().getResources().getString(R.string.msg_timeout)
        }

        return errorMsg
    }


}