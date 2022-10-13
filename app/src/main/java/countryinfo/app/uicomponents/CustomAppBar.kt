package countryinfo.app.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomAppBar(
    title: String,
    isBackNavAvailable: Boolean,
    clickAction: () -> Unit
) {
    if (!isBackNavAvailable) {
        TopAppBar(
            navigationIcon = null,
            contentColor = Color.Black,
            title = {
                Text(
                    text = title, modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp), textAlign = TextAlign.Center, fontSize = 16.sp
                )
            },
            elevation = 0.dp
        )
    } else {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    textAlign = TextAlign.Center , fontSize = 16.sp
                )
            },
            contentColor = Color.Black,
            navigationIcon = {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { clickAction.invoke() }
                )
            }
        )
    }
}