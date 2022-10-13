package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage

@Composable
fun ImageCoatOfArm(imageUrl : String) {

    AsyncImage(
        model = imageUrl,
        contentDescription = "Country Flag",
        modifier = Modifier.layoutId("title")
            .size(40.dp, 40.dp)
            .padding(top = 4.dp)
    )

}