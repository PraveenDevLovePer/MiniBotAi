package com.techdevlp.minibotai.views.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.techdevlp.minibotai.R
import com.techdevlp.minibotai.datastore.StoredDataPreference
import com.techdevlp.minibotai.more.LoadingButton
import com.techdevlp.minibotai.more.SetNavigationBarColor
import com.techdevlp.minibotai.more.SetStatusBarColor
import com.techdevlp.minibotai.more.spSizeResource
import com.techdevlp.minibotai.navigation.ScreenNames
import com.techdevlp.minibotai.navigation.canNavigate
import com.techdevlp.minibotai.ui.theme.AppThemeColor
import com.techdevlp.minibotai.ui.theme.DisabledColor
import com.techdevlp.minibotai.ui.theme.SecondaryColor
import com.techdevlp.minibotai.ui.theme.sfProTextBold
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingComposable(
    navController: NavHostController
) {
    SetStatusBarColor(color = Color.Transparent, isIconLight = true)
    SetNavigationBarColor(color = Color.Transparent, isIconLight = true)

    val context = LocalContext.current
    val itemsList: List<OnBoardingItems> = OnBoardingItems.getData()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val pagerState: PagerState = rememberPagerState(initialPage = 0, pageCount = { itemsList.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp40)))
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.dp30)),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) { page ->
            val scale: Float by animateFloatAsState(
                targetValue = if (page == pagerState.currentPage) 1f else 0.9f,
                animationSpec = tween(durationMillis = 300),
                label = "Label"
            )
            OnBoardingItemsComposable(
                item = itemsList[page],
                scale = scale,
                pageOffset = page.toFloat() - pagerState.currentPage.toFloat()
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp20)))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box {
                Indicators(size = itemsList.size, index = pagerState.currentPage)
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp20)))
        LoadingButton(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.dp31)),
            title = "Get Started",
            isLoading = false,
            onClick = {
                coroutineScope.launch {
                    val storeData = StoredDataPreference(context = context)
                    storeData.saveOnBoardingToken(token = true)
                }
                if (navController.canNavigate) {
                    navController.popBackStack()
                    navController.navigate(route = ScreenNames.HomeScreen.route) {
                        popUpTo(route = ScreenNames.OnBoardingScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp20)))
    }
}

@Composable
fun OnBoardingItemsComposable(
    item: OnBoardingItems,
    scale: Float,
    pageOffset: Float
) {
    val colorMatrix = remember { ColorMatrix() }
    LaunchedEffect(key1 = scale) {
        if (pageOffset != 0.0f) {
            colorMatrix.setToSaturation(0.5f)
        } else {
            colorMatrix.setToSaturation(1f)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp25))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppThemeColor)
            ) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp20)))
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.45f),
                    colorFilter = ColorFilter.colorMatrix(colorMatrix)
                )
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp20)))
                Text(
                    text = item.title,
                    style = TextStyle(
                        fontFamily = FontFamily(sfProTextBold),
                        fontSize = spSizeResource(id = R.dimen.sp18),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(id = R.dimen.dp30),
                            end = dimensionResource(id = R.dimen.dp30)
                        ),
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp30)))
            }
        }
    }
}

@Composable
fun BoxScope.Indicators(
    size: Int,
    index: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.dp5)),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size) {
            Indicator(isSelected = it == index)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) dimensionResource(id = R.dimen.dp8) else dimensionResource(id = R.dimen.dp8),
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = ""
    )

    Box(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.dp8))
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) SecondaryColor else DisabledColor
            )
    )
}

class OnBoardingItems(
    val image: Int,
    val title: String
) {
    companion object {
        fun getData(): List<OnBoardingItems> {
            return listOf(
                OnBoardingItems(
                    R.drawable.app_icon,
                    "Organize Documents"
                ),
                OnBoardingItems(
                    R.drawable.app_icon,
                    "Manage Events"
                ),
                OnBoardingItems(
                    R.drawable.app_icon,
                    "Manage Notes"
                )
            )
        }
    }
}