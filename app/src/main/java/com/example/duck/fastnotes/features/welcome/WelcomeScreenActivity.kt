package com.example.duck.fastnotes.features.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.WelcomeScreenTheme
import com.example.duck.fastnotes.ui.theme.WelcomeTheme
import com.example.duck.fastnotes.utils.Dimens
import com.example.duck.fastnotes.utils.ViewUtils.noRippleClickable
import com.example.duck.fastnotes.utils.ViewUtils.roundRectShadow
import com.example.duck.fastnotes.utils.ui.CustomShadowParams

class WelcomeScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WelcomeScreenTheme {
                WelcomeScreen()
            }
        }
    }

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, WelcomeScreenActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Preview
@Composable
fun WelcomeScreen(){
    val context = LocalContext.current
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(WelcomeTheme.colors.background),
    ) {
        val (title, appName, imageSign, userInfo, userAgreement, buttonCnt) = createRefs()

        var isAgreementChecked by remember { mutableStateOf(false) }

        WelcomeText(text = context.resources.getString(R.string.welcome_screen_hello),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, Dimens.WELCOME_SCREEN_WELCOME_MARGIN)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
        })

        AppName(text = context.resources.getString(R.string.welcome_screen_title),
            modifier = Modifier.constrainAs(appName) {
                top.linkTo(title.top, margin = Dimens.WELCOME_SCREEN_TITLE_MARGIN)
                start.linkTo(title.start)
                end.linkTo(title.end)
            },
        )

        ImageLogo(
            modifier = Modifier.constrainAs(imageSign) {
                top.linkTo(appName.bottom, margin = 80.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        UserInfo(text = stringResource(id = R.string.welcome_screen_terms_info),
            modifier = Modifier.constrainAs(userInfo) {
                bottom.linkTo(userAgreement.top, 5.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
        })

        UserAgreement(
            modifier = Modifier.constrainAs(userAgreement) {
                start.linkTo(buttonCnt.start, 8.dp)
                end.linkTo(buttonCnt.end)
                bottom.linkTo(buttonCnt.top, 20.dp)
            },
            text = stringResource(id = R.string.welcome_screen_terms_checkbox),
            isChecked = isAgreementChecked,
            onCheckedChange = {
                isAgreementChecked = it
            }
        )

        StartedButton(text = context.getString(R.string.welcome_screen_action),
            enabled = isAgreementChecked,
            modifier = Modifier.constrainAs(buttonCnt) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            })
    }
}

@Composable
fun WelcomeText(modifier: Modifier, text: String) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = text,
        style = WelcomeTheme.typography.subtitle1,
        textAlign = TextAlign.Center
    )
}

@Composable
fun AppName(modifier: Modifier, text: String) {
    Text(
        modifier = modifier.wrapContentWidth(),
        style = WelcomeTheme.typography.h1.copy(
            fontWeight = FontWeight.Normal,
            shadow = Shadow(
                color = WelcomeTheme.colors.onPrimary.copy(alpha = 0.3f),
                offset = Offset(2f, 7f),
                blurRadius = 6f
            )
        ),
        text = text,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ImageLogo(modifier: Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(85.dp)
            .rotate(-1f)
    )
}

@Composable
fun UserInfo(modifier: Modifier, text: String) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(horizontal = WelcomeTheme.spacing.default)
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = WelcomeTheme.colors.secondaryVariant
    ) {
        Text(modifier = Modifier.padding(WelcomeTheme.spacing.default),
            textAlign = TextAlign.Center,
            style = WelcomeTheme.typography.caption,
            text = text
        )
    }
}

@Composable
fun UserAgreement(modifier: Modifier, text: String, isChecked: Boolean, onCheckedChange: (isChecked: Boolean) -> Unit) {
    Row(modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(style = WelcomeTheme.typography.subtitle1, text = text)

        Checkbox(
            modifier = Modifier
                .size(48.dp)
                .padding(start = 10.dp),
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = WelcomeTheme.colors.secondaryVariant,
                uncheckedColor = WelcomeTheme.colors.secondaryVariant
            )
        )
    }
}

@Composable
fun StartedButton(modifier: Modifier, text: String, enabled: Boolean) {
    Button(
        onClick = {},
        modifier = modifier
            .padding(horizontal = 100.dp)
            .padding(bottom = 20.dp)
            .height(45.dp)
            .roundRectShadow(
                CustomShadowParams.defaultButtonShadow(),
                13.dp
            )
            .fillMaxWidth(),
        elevation = ButtonDefaults.elevation(
            0.dp
        ),
        enabled = enabled,
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = WelcomeTheme.colors.testBlack,
            disabledBackgroundColor = WelcomeTheme.colors.secondary
        ),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        Text(
            text = text,
            style = WelcomeTheme.typography.button,
            color = Color.White,
            textAlign = TextAlign.Center,
            letterSpacing = 0.5.sp
        )
    }
}
