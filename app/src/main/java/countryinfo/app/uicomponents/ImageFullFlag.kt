package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import countryinfo.app.R
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.idTopFlag

@Composable
fun ImageFullFlag(flagImageUrl: String) {

     AsyncImage(
        model = flagImageUrl,
        placeholder = painterResource(id = R.drawable.default_placeholder),
        contentDescription = stringResource(id = R.string.country_flag),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .testTag("image_full_flag")
            .fillMaxWidth()
            .layoutId(idTopFlag)
            .height(getDP(dimenKey = R.dimen.dp_220))

    )
}