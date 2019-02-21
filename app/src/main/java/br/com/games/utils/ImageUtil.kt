package br.com.games.utils

import android.content.Context
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by aluiz on 20/02/2019.
 */


// tratamento de imagem
object ImageUtil {

    fun loadImage(posterPath: String, context: Context, isCrop: Boolean): DrawableRequestBuilder<String> {
        return if (isCrop)
            Glide
                    .with(context)
                    .load(posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .crossFade()
        else
            Glide
                    .with(context)
                    .load(posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
    }
}