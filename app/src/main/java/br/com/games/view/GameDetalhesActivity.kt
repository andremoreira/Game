package br.com.games.view

import android.os.Bundle
import android.view.View
import br.com.games.R
import br.com.games.base.BaseActivity
import br.com.games.base.GameConstant
import br.com.games.domain.Top
import br.com.games.utils.ImageUtil
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import kotlinx.android.synthetic.main.activity_detalhes.*
import java.lang.Exception

/**
 * Created by aluiz on 20/02/2019.
 */
class GameDetalhesActivity : BaseActivity() {

    private val top by lazy { intent.getSerializableExtra(GameConstant.TOP) as Top }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_detalhes)

        loadView()
    }

    private fun loadView() {

        top.game?.apply {
            ImageUtil.loadImage(box.large, context, false).listener(object : RequestListener<String, GlideDrawable> {
                override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                    tv_carregando.visibility = View.INVISIBLE
                    return false
                }

                override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                    tv_carregando.visibility = View.INVISIBLE
                    return false
                }
            }).into(img)

            txtName.text = name
        }

        tv_canais.text = "Canal...: "+top.channels
        tv_visualizacao.text = "Visualizações...: "+top.viewers

    }
}