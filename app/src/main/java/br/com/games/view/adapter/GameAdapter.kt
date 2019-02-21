package br.com.games.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import br.com.games.R
import br.com.games.domain.Top
import br.com.games.extensions.inflate
import br.com.games.utils.ImageUtil
import br.com.games.utils.PaginationAdapterCallback
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import kotlinx.android.synthetic.main.list_adapter_item.view.*
import kotlinx.android.synthetic.main.progress_bar.view.*
import java.lang.Exception

/**
 * Created by aluiz on 20/02/2019.
 */
class GameAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 0
    private var list: MutableList<Top> = ArrayList()
    private var isLoadingAdded = false
    private var retryPageLoad = false
    private var errorMsg: String? = null
    private val LOADING = 1

    override fun onCreateViewHolder(vGroup: ViewGroup, id: Int): RecyclerView.ViewHolder {
        when (id) {

            ITEM -> {
                return GameViewHolder(vGroup)

            }

            LOADING -> {
                return LoadingViewHolder(vGroup)
            }

            else ->

                return LoadingViewHolder(vGroup)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val top: Top = list[position]

        when (getItemViewType(position)) {
            ITEM -> {
                holder as GameViewHolder
                holder.bind(top)
            }
            LOADING -> {
                holder as LoadingViewHolder
                holder.bind()
            }
        }
    }

    private val mCallback: PaginationAdapterCallback = context as PaginationAdapterCallback

    override fun getItemViewType(position: Int): Int {
        if (position == list.size - 1 && isLoadingAdded)

            return LOADING

        return ITEM
    }

    override fun getItemCount(): Int = list.size

    private inner class GameViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.list_adapter_item)) {

        private val tvNome = itemView.tvNome
        private val imgGame = itemView.img
        private val gameProgress = itemView.progress_bar


        fun bind(top: Top) {
            top.game?.apply {
                tvNome.text = name
                ImageUtil.loadImage(box.large, context, true).listener(object : RequestListener<String, GlideDrawable> {
                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        gameProgress.visibility = View.GONE
                        return false
                    }

                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        gameProgress.visibility = View.GONE
                        return false
                    }
                }).into(imgGame)

                super.itemView.setOnClickListener {
                    Log.d("itemView", "onclick")
                    mCallback.onClick(top)
                }
            }
        }
    }

    private inner class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.progress_bar)), View.OnClickListener {

        val progressBar = itemView.progressBar
        val btnAtualiza = itemView.btnAtualiza
        val tvError = itemView.tvError
        val errorLayout = itemView.linear_layout

        init {
            btnAtualiza.setOnClickListener(this)
            errorLayout.setOnClickListener(this)
        }

        fun bind() {
            if (retryPageLoad) {
                errorLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                tvError.text = errorMsg ?: "erro nao encontrado!"
            } else {
                errorLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        }

        override fun onClick(view: View?) {
            when (view?.id) {
                R.id.btnAtualiza, R.id.linear_layout -> {
                    showRetry(false, null)
                    mCallback.pageLoad()
                }
            }
        }
    }

    private fun getItem(position: Int): Top {
        return list[position]
    }

    fun showRetry(show: Boolean, error: String?) {
        retryPageLoad = show
        notifyItemChanged(list.size - 1)

        this.errorMsg = error ?: errorMsg
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = list.size - 1
        getItem(position).let {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addAll(list: List<Top>) {
        for (item in list) {
            add(item)
        }
    }

    fun add(t: Top) {
        list.add(t)
        notifyItemInserted(list.size - 1)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Top(null, "", ""))
    }
}
