package com.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.theme.ui.theme.ComposeThemeSwitchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.auto(
//
//            )
//        )
//        WindowCompat.getInsetsController(window, window.decorView)
//        WindowCompat.setDecorFitsSystemWindows(window,false)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            ComposeThemeSwitchTheme(darkTheme = darkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .safeDrawingPadding()
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        darkTheme = darkTheme,
                        onThemeUpdated = { darkTheme = !darkTheme }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(darkTheme: Boolean, onThemeUpdated: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable(onClick = onThemeUpdated),
        contentAlignment = Alignment.Center
    ) {
        val durationMillis = 1000
        val thumbOffset by animateDpAsState(
            label = "Thumb Offset",
            animationSpec = tween(durationMillis = durationMillis),
            targetValue = if (darkTheme) 104.dp else 0.dp
        )
        val rightXCloudsOffset by animateDpAsState(
            label = "Thumb Offset",
            animationSpec = tween(durationMillis = durationMillis),
            targetValue = if (darkTheme) 170.dp else 85.dp
        )
        val rightYCloudsOffset by animateDpAsState(
            label = "Thumb Offset",
            animationSpec = tween(durationMillis = durationMillis),
            targetValue = if (darkTheme) 0.dp else 20.dp
        )
        val leftXCloudsOffset by animateDpAsState(
            label = "Thumb Offset",
            animationSpec = tween(durationMillis = durationMillis),
            targetValue = if (darkTheme) (-100).dp else 18.dp
        )
        val leftYCloudsOffset by animateDpAsState(
            label = "Thumb Offset",
            animationSpec = tween(durationMillis = durationMillis),
            targetValue = if (darkTheme) 0.dp else 30.dp
        )
        val starsOffset by animateDpAsState(
            label = "Thumb Offset",
            animationSpec = tween(durationMillis = durationMillis),
            targetValue = if (darkTheme) 0.dp else (-65).dp
        )

        val bg = if (darkTheme) R.drawable.bg_dark else R.drawable.bg_light
        val icon = if (darkTheme) R.drawable.ic_thumb_dark else R.drawable.ic_thumb_light

        Box(
            modifier = Modifier
                .width(169.dp)
                .height(65.dp)
                .clip(shape = RoundedCornerShape(50))
                .paint(painterResource(id = bg), contentScale = ContentScale.Crop),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(55.dp)
                    .offset(y = starsOffset),
                painter = painterResource(id = R.drawable.ic_stars), contentDescription = null
            )
            Image(
                modifier = Modifier
                    .width(97.dp)
                    .height(62.dp)
                    .rotate(-4f)
                    .offset(x = leftXCloudsOffset, y = leftYCloudsOffset),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.ic_cloud_left), contentDescription = null
            )
            Image(
                modifier = Modifier
                    .width(97.dp)
                    .height(62.dp)
                    .rotate(-4f)
                    .offset(x = rightXCloudsOffset, y = rightYCloudsOffset),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.ic_cloud_right), contentDescription = null
            )

            Image(
                modifier = Modifier
                    .size(65.dp)
                    .offset(x = thumbOffset),
                painter = painterResource(id = icon), contentDescription = null
            )
//            AnimatedContent(targetState = darkTheme) {
//                val icon = if (it) R.drawable.ic_thumb_dark else R.drawable.ic_thumb_light
//            }
//            Image(
//                modifier = Modifier
//                    .size(65.dp)
//                    .offset(x = thumbOffset),
//                painter = painterResource(id = icon), contentDescription = null
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    val darkTheme = false
    ComposeThemeSwitchTheme(darkTheme = darkTheme) {
        MainScreen(darkTheme) {

        }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}