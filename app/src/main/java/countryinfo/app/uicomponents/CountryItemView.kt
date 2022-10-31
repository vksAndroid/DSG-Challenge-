package countryinfo.app.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import countryinfo.app.R
import countryinfo.app.theme.dsgTitleBlackBold
import countryinfo.app.theme.dsgTitleDarkGrayMedium
import countryinfo.app.theme.dsgTitleGrayMedium
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.EMPTY_STRING


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryItemView(
    countryFlag: String?,
    commonName: String?,
    officialName: String?,
    capitalName: String,
    onItemClicked: () -> Unit
) {


    Card(
        modifier = Modifier
            .testTag("country_item_view")
            .fillMaxWidth()
            .padding(getDP(dimenKey = R.dimen.dp_12)),
        onClick = { onItemClicked.invoke() },
        elevation = getDP(dimenKey = R.dimen.dp_2),
        shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_12))

    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(getDP(dimenKey = R.dimen.dp_4))

        ) {

            val painterModel = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(countryFlag).crossfade(true).diskCachePolicy(CachePolicy.ENABLED)
                    .placeholder(R.drawable.default_placeholder).build()
            )

            Image(
                painter = painterModel, contentDescription = "",
                modifier = Modifier
                    .size(
                        width = getDP(dimenKey = R.dimen.dp_100),
                        height = getDP(dimenKey = R.dimen.dp_65)
                    )
                    .padding(getDP(dimenKey = R.dimen.dp_8)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(
                        vertical = getDP(dimenKey = R.dimen.dp_10),
                        horizontal = getDP(dimenKey = R.dimen.dp_10)
                    )
            ) {
                DsgTextView(
                    value = commonName ?: EMPTY_STRING, style = dsgTitleBlackBold
                )
                DsgTextView(
                    value = officialName ?: EMPTY_STRING, style = dsgTitleDarkGrayMedium
                )
                DsgTextView(
                    value = capitalName, style = dsgTitleGrayMedium
                )
            }
        }

    }

}

