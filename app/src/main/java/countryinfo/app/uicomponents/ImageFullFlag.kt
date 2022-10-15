package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage
import countryinfo.app.R

@Composable
fun ImageFullFlag(flagImageUrl: String) {
    AsyncImage(
        model = flagImageUrl,
        placeholder = painterResource(id = R.drawable.default_loading),
        contentDescription = "Country Flag",
        contentScale = ContentScale.Crop,
        modifier = Modifier.testTag("image_full_flag")
            .fillMaxWidth()
            .height(220.dp)
            .layoutId("top_flag")

    )
}