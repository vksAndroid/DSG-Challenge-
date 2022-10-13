package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage

@Composable
fun ImageFullFlag(flagImageUrl: String) {
    AsyncImage(
        model = flagImageUrl,
        contentDescription = "Country Flag",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
              .height(180.dp)
            .layoutId("top_flag")

    )
}