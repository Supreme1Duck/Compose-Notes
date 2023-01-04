package com.example.duck.fastnotes.features.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.WelcomeTheme

@Preview
@Composable
fun SignUpScreen() {

    Column(
        Modifier
            .fillMaxSize()
            .padding(WelcomeTheme.spacing.default),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MainContent()

        AdditionalMethods()
    }
}

@Composable
fun MainContent() {
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ImageLogo(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = WelcomeTheme.spacing.small)
                .height(70.dp)
                .width(120.dp)
        )

        SignUpLogo(modifier = Modifier.padding(top = WelcomeTheme.spacing.larger))

        SignUpTitle()

        EmailInput(modifier = Modifier.padding(top = WelcomeTheme.spacing.extraLarger), emailInput) {
            emailInput = it
        }

        PasswordInput(modifier = Modifier.padding(top = WelcomeTheme.spacing.default), passwordInput) {
            passwordInput = it
        }
    }
}

@Composable
fun AdditionalMethods() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = WelcomeTheme.spacing.default)
    ) {
        ContinueWithoutRegText(
            modifier = Modifier.padding(
                top = WelcomeTheme.spacing.large,
                bottom = WelcomeTheme.spacing.bottom
            )
        )

        SignInText(modifier = Modifier)
    }
}

@Composable
fun SignUpLogo(modifier: Modifier) {
    Icon(
        modifier = modifier.size(140.dp),
        painter = painterResource(id = R.mipmap.ic_sign_up_foreground),
        contentDescription = null
    )
}

@Composable
fun SignUpTitle() {
    Text(
        modifier = Modifier,
        text = stringResource(id = R.string.sign_up_screen_title),
        style = WelcomeTheme.typography.h3.copy(
            fontWeight = FontWeight.Normal,
        )
    )
}

@Composable
fun EmailInput(modifier: Modifier, text: String, isError: Boolean = false, onValueChange: (String) -> Unit) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(65.dp)
        .clip(RoundedCornerShape(18.dp, 18.dp, 18.dp, 18.dp))
        .background(WelcomeTheme.colors.secondary)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterStart),
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(text = stringResource(id = R.string.sign_up_screen_email_hint))              
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Person, contentDescription = null)
            }
        )
    }
}

@Composable
fun PasswordInput(
    modifier: Modifier,
    text: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .clip(RoundedCornerShape(18.dp, 18.dp, 18.dp, 18.dp))
            .background(WelcomeTheme.colors.secondary)
    ) {
        TextField(
            modifier = Modifier.fillMaxSize().align(Alignment.CenterStart),
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            placeholder = {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.sign_up_screen_password_hint),
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
            }
        )
    }
}

@Composable
fun ContinueWithoutRegText(modifier: Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Divider(
            modifier = Modifier.weight(1f),
            color = WelcomeTheme.colors.onPrimary,
            thickness = 1.dp
        )
        Text(
            modifier = Modifier.weight(3f),
            text = stringResource(id = R.string.sign_up_screen_continue_without_reg),
            textAlign = TextAlign.Center,
            style = WelcomeTheme.typography.caption.copy(color = WelcomeTheme.colors.onPrimary, fontSize = 20.sp)
        )
        Divider(
            modifier = Modifier.weight(1f),
            color = WelcomeTheme.colors.onPrimary,
            thickness = 1.dp
        )
    }
}

@Composable
fun SignInText(modifier: Modifier) {
    Row(modifier) {
        Text(
            text = stringResource(id = R.string.sign_up_already_have_acc),
            style = WelcomeTheme.typography.caption.copy(color = WelcomeTheme.colors.onPrimary)
        )

        Text(
            modifier = Modifier.padding(start = WelcomeTheme.spacing.smaller),
            text = stringResource(id = R.string.sign_up_screen_action_sign_in),
            style = WelcomeTheme.typography.caption.copy(color = WelcomeTheme.colors.tertiary),
            textDecoration = TextDecoration.Underline
        )
    }
}