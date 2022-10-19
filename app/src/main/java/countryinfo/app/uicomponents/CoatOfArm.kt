package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage
import countryinfo.app.R
import countryinfo.app.uicomponents.scaffold_comp.getDP
 import countryinfo.app.utils.contentDescriptionCoatImage

/**
*@param imageUrl for show image
 */
@Composable
fun ImageCoatOfArm(imageUrl: String) {

    AsyncImage(
        model = imageUrl,
        placeholder = painterResource(id = R.drawable.default_placeholder),
        contentDescription = contentDescriptionCoatImage,
        modifier = Modifier
            .layoutId("title")
            .size(getDP(R.dimen.dp_60), getDP(R.dimen.dp_60))
            .padding(top = getDP(R.dimen.sp_6))
    )

}