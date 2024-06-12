package com.techdevlp.minibotai.more

import android.widget.ImageView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.techdevlp.minibotai.R

@Composable
fun LoadingView(url: Int, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(
            modifier = modifier
                .size(dimensionResource(id = R.dimen.dp70)),
            factory = { context ->
                val imageView = ImageView(context)
                Glide.with(context)
                    .asGif()
                    .load(url)
                    .apply(RequestOptions().centerCrop())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
                imageView
            }
        )
    }
}