package com.example.com.presentation.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule

object GlideLoader {

    fun load(
        context: Context,
        url: String,
        targetView: ImageView
    ) {
        Glide.with(context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(targetView)
    }
}


@GlideModule
class GlideModule : AppGlideModule()