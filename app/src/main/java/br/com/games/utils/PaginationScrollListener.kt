package br.com.games.utils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView


/**
 * Created by aluiz on 20/02/2019.
 */
abstract class PaginationScrollListener

(private var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    abstract val totalPageCount: Int

    abstract fun isLastPage() : Boolean

    abstract fun isLoading() : Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }

    }

    protected abstract fun loadMoreItems()

}
