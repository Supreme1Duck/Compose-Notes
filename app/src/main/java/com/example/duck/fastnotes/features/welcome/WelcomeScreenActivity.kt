package com.example.duck.fastnotes.features.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.duck.fastnotes.R
import com.example.duck.fastnotes.ui.theme.FastNotesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class WelcomeScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FastNotesTheme {
                WelcomeScreen()
            }
        }

    }

    companion object {
        fun open(context: Context){
            val intent = Intent(context, WelcomeScreenActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Composable
fun WelcomeScreen(){
    val context = LocalContext.current
    Box(
        Modifier
            .fillMaxSize()) {

        Text(text = context.resources.getString(R.string.welcome_screen),
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 16.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            SignUpQuestion()
            SignUpText()
            StartedButton()
        }
    }
}

@Composable
fun StartedButton() {
    val context = LocalContext.current

    Button(onClick = { },
        Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 20.dp)
            .height(45.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        Text(text = context.getString(R.string.welcome_screen_action),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp),
            textAlign = TextAlign.Center, letterSpacing = 0.5.sp)
    }
}

@Composable
fun SignUpQuestion(){
    val context = LocalContext.current
    Text(text = context.getString(R.string.welcome_screen_online_functions),
        style = TextStyle(fontSize = 12.sp,
        textAlign = TextAlign.Center),
        modifier = Modifier
            .padding(bottom = 2.dp)
            .fillMaxWidth())
}

@Composable
fun SignUpText(){
    val context = LocalContext.current
    Text(text = context.getString(R.string.welcome_screen_sign_up),
        style = TextStyle(fontSize = 12.sp,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline),

        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .clickable {

            }
    )
}



