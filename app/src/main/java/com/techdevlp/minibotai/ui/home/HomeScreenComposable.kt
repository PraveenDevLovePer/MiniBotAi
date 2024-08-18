package com.techdevlp.minibotai.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.techdevlp.minibotai.R
import com.techdevlp.minibotai.more.CheckForUpdates
import com.techdevlp.minibotai.more.SetNavigationBarColor
import com.techdevlp.minibotai.more.SetStatusBarColor
import com.techdevlp.minibotai.more.isNetworkAvailable
import com.techdevlp.minibotai.more.showToast
import com.techdevlp.minibotai.more.spSizeResource
import com.techdevlp.minibotai.ui.theme.AppThemeColor
import com.techdevlp.minibotai.ui.theme.DisabledColor
import com.techdevlp.minibotai.ui.theme.SecondaryColor
import com.techdevlp.minibotai.ui.theme.sfProTextBold
import com.techdevlp.minibotai.ui.theme.sfProTextRegular
import kotlinx.coroutines.delay

@Composable
fun HomeScreenComposable(myViewModel: ChatBotViewModel = viewModel(factory = GenerativeViewModelFactory)) {
    SetStatusBarColor(color = Color.Transparent, isIconLight = !isSystemInDarkTheme())
    SetNavigationBarColor(color = Color.Transparent, isIconLight = isSystemInDarkTheme())

    val activity = LocalContext.current as Activity
    val chatUiState by myViewModel.uiState.collectAsState()
    var isCheckUpdate by remember { mutableStateOf(false) }
    val appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(activity)
    LaunchedEffect(Unit) {
        delay(1000)
        isCheckUpdate = true
    }
    if (isCheckUpdate) CheckForUpdates(appUpdateManager = appUpdateManager)

    Scaffold(
        modifier = Modifier.imePadding(),
        bottomBar = {
            MessageInput(
                onSendMessage = { inputText ->
                    myViewModel.sendMessage(inputText)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Header
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp10)))
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(sfProTextBold),
                fontSize = spSizeResource(id = R.dimen.sp20)
            )
            Text(
                text = "The Power of AI. For Brief Encounters.",
                modifier = Modifier
                    .fillMaxWidth(),
                fontFamily = FontFamily(sfProTextRegular),
                fontSize = spSizeResource(id = R.dimen.sp13),
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            // Messages List
            ChatList(chatUiState.messages, activity)
        }
    }
}

@Composable
fun ChatList(
    chatMessages: List<ChatMessage>,
    activity: Activity
) {
    LazyColumn(
        reverseLayout = false
    ) {
        items(chatMessages.size) { message ->
            ChatBubbleItem(chatMessages[message], activity)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ChatBubbleItem(
    chatMessage: ChatMessage,
    activity: Activity
) {
    val clipboardManager: androidx.compose.ui.platform.ClipboardManager =
        LocalClipboardManager.current
    Column(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.dp5),
                end = dimensionResource(id = R.dimen.dp5)
            )
            .fillMaxWidth()
    ) {
        if (chatMessage.participant.name != "YOU") {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = chatMessage.participant.name,
                fontSize = spSizeResource(id = R.dimen.sp12),
                fontFamily = FontFamily(sfProTextRegular),
                modifier = Modifier
                    .padding(
                        end = dimensionResource(id = R.dimen.dp5),
                        start = dimensionResource(id = R.dimen.dp5)
                    )
                    .weight(1f),
                color = if (chatMessage.participant.name == "YOU") SecondaryColor else Color.Gray
            )
                Image(
                    painter = painterResource(id = R.drawable.copy_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterVertically)
                        .padding(end = dimensionResource(id = R.dimen.dp10))
                        .size(dimensionResource(id = R.dimen.dp16))
                        .clickable {
                            clipboardManager.setText(AnnotatedString(chatMessage.text))
                            Toast
                                .makeText(activity, "Copied to clipboard", Toast.LENGTH_SHORT)
                                .show()
                        },
                    colorFilter = ColorFilter.tint(color = if (isSystemInDarkTheme()) Color.White else Color.Black)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(
                    all = if (chatMessage.participant.name == "YOU") dimensionResource(id = R.dimen.dp5) else 0.dp
                )
                .background(
                    color = if (chatMessage.participant.name == "YOU") AppThemeColor else Color.Transparent,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dp10))
                )
        ) {
            CompositionLocalProvider(
                value = LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = Color.Gray,
                    backgroundColor = Color.LightGray.copy(alpha = 0.5f),
                )
            ) {
                SelectionContainer {
                    Text(
                        text = chatMessage.text,
                        fontSize = spSizeResource(id = R.dimen.sp16),
                        fontFamily = FontFamily(sfProTextRegular),
                        modifier = Modifier.padding(
                            all = dimensionResource(id = R.dimen.dp7)
                        ),
                        color = if (chatMessage.participant.name == "YOU" || isSystemInDarkTheme()) Color.White else Color.Black
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dp10)))
        if (chatMessage.isPending) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(all = dimensionResource(id = R.dimen.dp16))
                    .size(dimensionResource(id = R.dimen.dp22))
            )
        }
    }
}

@Composable
fun MessageInput(
    onSendMessage: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    var userMessage by rememberSaveable { mutableStateOf("") }

    Column {
        Row(
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(id = R.dimen.dp10),
                    start = dimensionResource(id = R.dimen.dp16)
                )
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = userMessage,
                label = {
                    Text(
                        stringResource(R.string.chat_label),
                        fontSize = spSizeResource(id = R.dimen.sp13)
                    )
                },
                onValueChange = {
                    userMessage = it
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .weight(0.85f)
                    .heightIn(max = dimensionResource(id = R.dimen.dp150)),
                textStyle = TextStyle(fontSize = spSizeResource(id = R.dimen.sp15)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = DisabledColor,
                    errorTextColor = Color.Red,
                )
            )
            IconButton(
                onClick = {
                    if (isNetworkAvailable(context)) {
                        if (userMessage.isEmpty()) {
                            showToast(context = context, message = "Please enter your question")
                        } else {
                            keyboardController?.hide()
                            onSendMessage(userMessage)
                            userMessage = ""
                        }
                    } else {
                        showToast(context = context, message = "No Internet Connection")
                    }
                },
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.dp10))
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .weight(0.15f)
            ) {
                Icon(
                    Icons.Default.Send,
                    contentDescription = stringResource(R.string.action_send),
                    modifier = Modifier,
                    tint = AppThemeColor
                )
            }
        }
        Text(
            text = "This may display inaccurate info, including about people, so double-check its responses.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(id = R.dimen.dp7),
                    end = dimensionResource(id = R.dimen.dp7),
                    bottom = dimensionResource(id = R.dimen.dp15)
                ),
            fontFamily = FontFamily(sfProTextRegular),
            fontSize = spSizeResource(id = R.dimen.sp11),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            lineHeight = 12.sp,
        )
        Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.dp36)))
    }
}