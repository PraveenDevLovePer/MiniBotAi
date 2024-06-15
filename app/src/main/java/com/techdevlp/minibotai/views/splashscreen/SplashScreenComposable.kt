package com.techdevlp.minibotai.views.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.techdevlp.minibotai.BuildConfig
import com.techdevlp.minibotai.R
import com.techdevlp.minibotai.datastore.StoredDataPreference
import com.techdevlp.minibotai.more.spSizeResource
import com.techdevlp.minibotai.navigation.ScreenNames
import com.techdevlp.minibotai.ui.theme.sfProTextBold
import com.techdevlp.minibotai.ui.theme.sfProTextMedium
import com.techdevlp.minibotai.ui.theme.sfProTextRegular
import kotlinx.coroutines.delay

@Composable
fun SplashScreenComposable(
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val storedData = StoredDataPreference(context = context)
        delay(2000)
        if (storedData.getOnBoardingToken()) {
            navController.navigate(ScreenNames.HomeScreen.route) {
                popUpTo(route = ScreenNames.SplashScreen.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        } else {
            navController.navigate(ScreenNames.OnBoardingScreen.route) {
                popUpTo(route = ScreenNames.SplashScreen.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier.run {
            fillMaxSize()
                .padding(bottom = dimensionResource(id = R.dimen.dp20))
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "",
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.dp120))
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp100)))
                .align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp5)))
        Text(
            text = context.getString(R.string.app_name),
            modifier = Modifier
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(sfProTextBold),
            fontSize = spSizeResource(id = R.dimen.sp30)
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp5)))
        Text(
            text = "Version ${BuildConfig.VERSION_NAME}",
            modifier = Modifier
                .fillMaxWidth(),
            fontFamily = FontFamily(sfProTextRegular),
            fontSize = spSizeResource(id = R.dimen.sp11),
            textAlign = TextAlign.Center,
            color = Gray
        )
        Text(
            text = "by TechDevlp",
            modifier = Modifier
                .fillMaxWidth(),
            fontFamily = FontFamily(sfProTextMedium),
            fontSize = spSizeResource(id = R.dimen.sp14),
            textAlign = TextAlign.Center,
            color = Gray
        )
    }
}