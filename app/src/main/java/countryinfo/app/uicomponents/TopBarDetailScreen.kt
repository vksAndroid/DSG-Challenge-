package countryinfo.app.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun TopBarDetailScreen(title: String, iconVector: ImageVector, clickAction: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 64.dp),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Icon(
                iconVector,
                contentDescription = "",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { clickAction.invoke() }
            )
        }
    )
}
