package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import countryinfo.app.R
import countryinfo.app.theme.OffWhite
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.idCountry


@OptIn(ExperimentalTextApi::class)
@Composable
fun CountryNameCard(title: String, value: String) {

    Card(
        elevation = 1.5.dp,
        shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_10)),
        backgroundColor = OffWhite,
        modifier = Modifier.testTag("country_name_card")
            .layoutId(idCountry)
            .wrapContentHeight(Alignment.Bottom)
            .fillMaxWidth(0.6f)
            .wrapContentWidth(Alignment.Start)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(getDP(dimenKey = R.dimen.dp_15)
                    , end = getDP(dimenKey = R.dimen.dp_15),
                    top = getDP(dimenKey = R.dimen.dp_4),
                    bottom = getDP(dimenKey = R.dimen.dp_8)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title, fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 1,
                fontSize = 22.sp,
                style = MaterialTheme.typography.body1.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )

            AutoResizeText(
                text = value,
                maxLines = 2,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium,
                fontSizeRange = FontSizeRange(
                    min = 12.sp,
                    max = 16.sp,
                ),
                style = MaterialTheme.typography.body1.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                 overflow = TextOverflow.Ellipsis,
            )
//            Text(
//                text = value, color = Color.DarkGray,
//                overflow = TextOverflow.Ellipsis,
//                maxLines = 2,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.padding(),
//                style = MaterialTheme.typography.body1.copy(
//                    platformStyle = PlatformTextStyle(
//                        includeFontPadding = false
//                    )
//                )
//            )
        }
    }
}
