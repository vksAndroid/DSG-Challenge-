package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage
import countryinfo.app.R

@Composable
fun ImageCoatOfArm(imageUrl : String) {

    AsyncImage(
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.default_loading),
        contentDescription = "Country Flag",
        modifier = Modifier.layoutId("title")
            .size(40.dp, 40.dp)
            .padding(top = 4.dp)
    )

}