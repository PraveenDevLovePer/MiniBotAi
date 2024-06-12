package com.techdevlp.minibotai.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import com.techdevlp.minibotai.R
import com.techdevlp.minibotai.ui.theme.AppThemeColor
import com.techdevlp.minibotai.ui.theme.sfProTextMedium

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    title: String,
    isLoading: Boolean,
    onClick: (Boolean) -> Unit,
    btnTextColor: Color = Color.White,
    btnColors: List<Color> = listOf(AppThemeColor, AppThemeColor),
    cornerRadius: Int = R.dimen.dp50,
    isDisabled: Boolean = false
) {

    val context = LocalContext.current

    Box(modifier = modifier
        .fillMaxWidth()
        .height(dimensionResource(id = R.dimen.dp50))
        .background(
            brush = Brush.horizontalGradient(btnColors),
            shape = RoundedCornerShape(dimensionResource(id = cornerRadius))
        )
        .clip(shape = RoundedCornerShape(dimensionResource(id = cornerRadius)))
        .clickable(
            indication = rememberRipple(),
            interactionSource = remember { MutableInteractionSource() },
            enabled = !isDisabled
        ) {
            onClick(!isLoading)
        },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            val imageLoader = ImageLoader.Builder(context = context)
                .components {
                    add(ImageDecoderDecoder.Factory())
                }
                .build()

            Image(
                painter = rememberAsyncImagePainter(
                    model = R.drawable.btn_loading_gif,
                    imageLoader = imageLoader
                ),
                contentDescription = "Button Loading",
                modifier = Modifier
                    .fillMaxSize()
                    .scale(0.8f)
            )
        } else {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = FontFamily(sfProTextMedium),
                    fontSize = spSizeResource(id = LoadingButtonSize),
                    color = btnTextColor
                )
            )
        }
    }
}