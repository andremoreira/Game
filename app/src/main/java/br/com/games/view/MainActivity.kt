package br.com.games.view

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import br.com.games.R
import br.com.games.api.GameCallBack
import br.com.games.api.GameService
import br.com.games.base.BaseActivity
import br.com.games.base.GameConstant
import br.com.games.domain.GameResponse
import br.com.games.domain.Top
import br.com.games.utils.PaginationAdapterCallback
import br.com.games.utils.PaginationScrollListener
import br.com.games.view.adapter.GameAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error_layout.*
import org.jetbrains.anko.startActivity

/**
 * Created by aluiz on 21/02/2019.
 */

class MainActivity : BaseActivity(), PaginationAdapterCallback {

    private val TAG_MAIN = "MainActivity"

    private var isLoading = false
    private var isLastPage = false

    private val START = 1
    private var current = START
    private val TOTAL = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        firstPage()
    }

    private lateinit var adapter: GameAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager

    private fun init() {
        initAdapterView()

        btn_atualizar.setOnClickListener { firstPage() }

        swipeRefreshLayout.setOnRefreshListener {

            Log.d(TAG_MAIN, "onRefreshListener")

            current = START
            isLoading = false
            isLastPage = false

            initAdapterView()

            firstPage()

            swipeRefreshLayout.isRefreshing = false;
        }
    }

    private fun initAdapterView() {
        adapter = GameAdapter(this@MainActivity)

        linearLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override val totalPageCount: Int = TOTAL

            override fun loadMoreItems() {

                isLoading = true
                current += 1

                loadPage()
            }
        })
    }

    private fun loadPage() {
        Log.d(TAG_MAIN, "load: " + current)

        getNextGames()
    }



    private fun firstPage() {
        Log.d(TAG_MAIN, "FirstPage: ")

        errorView()

        getFirstGames()
    }

    private fun getFirstGames() {
        val callBack = object : GameCallBack<GameResponse> {
            override fun onSuccess(response: GameResponse) {
                errorView()
                progress.visibility = View.GONE

                adapter.addAll(response.top!!)

                if (current <= TOTAL)
                    adapter.addLoadingFooter()
                else
                    isLastPage = true
            }

            override fun onError(response: GameResponse?) {
                errorView(response)
            }
        }
        GameService.getGames(callBack, 10)
    }

    private fun getNextGames() {
        val callBack = object : GameCallBack<GameResponse> {
            override fun onSuccess(response: GameResponse) {
                adapter.removeLoadingFooter()
                isLoading = false
                adapter.addAll(response.top!!)

                if (current != TOTAL)
                    adapter.addLoadingFooter()
                else
                    isLastPage = true

            }

            override fun onError(response: GameResponse?) {
                adapter.showRetry(true, response!!.msgError)
            }
        }
        GameService.getGames(callBack, current * 10)
    }



    override fun pageLoad() {
        loadPage()
    }


    private fun errorView(response: GameResponse?) {
        if (linearLayoutErro.visibility == View.GONE) {
            linearLayoutErro.visibility = View.VISIBLE
            progress.visibility = View.GONE
            tvErro.text = response!!.msgError
        }
    }

    private fun errorView() {
        if (linearLayoutErro.visibility == View.VISIBLE) {

            linearLayoutErro.visibility = View.GONE

            progress.visibility = View.VISIBLE
        }
    }

    override fun onClick(top: Top) {

        startActivity<GameDetalhesActivity>(GameConstant.TOP to top)
    }
}

