package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import countryinfo.app.R
import countryinfo.app.data.model.CountryData
import countryinfo.app.theme.countryBasicDetailTitle
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.idBasicDetail

/**
 * @param countryData for rendering country related basic detail Region,Sub Region,capital
 */
@Composable
fun CountryBasicDetail(countryData: CountryData) {

    Card(
        elevation = getDP(dimenKey = R.dimen.dp_2),
        modifier = Modifier
            .testTag("country_basic_details_card")
            .layoutId(idBasicDetail)
            .padding(horizontal = getDP(dimenKey = R.dimen.dp_12)),
        shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_10))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            repeat(3) {

                Box(
                    modifier = Modifier
                        .weight(1f),
                ) {

                    when (it) {
                        0 -> {
                            ShowItem(stringResource(id = R.string.region), countryData.region)
                        }
                        1 -> {
                            ShowItem(stringResource(id = R.string.subregion), countryData.subregion)
                        }
                        else -> {
                            val capital =
                                if (countryData.capital.isNotEmpty()) countryData.capital[0] else ""
                            ShowItem(stringResource(id = R.string.capital), capital)
                        }
                    }
                }

                if (it < 2) {
                    Divider(
                        modifier = Modifier
                            .width(getDP(dimenKey = R.dimen.dp_1))
                            .height(getDP(dimenKey = R.dimen.dp_30)),
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}


@Composable
fun ShowItem(title: String, value: String) {

    Column(
        modifier = Modifier
            .padding(
                vertical = getDP(dimenKey = R.dimen.dp_10),
                horizontal = getDP(dimenKey = R.dimen.dp_2)
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        DsgTextView(
            value = title,
            style = countryBasicDetailTitle,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center

        )

        AutoResizeText(
            text = value,
            maxLines = 1,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            fontSizeRange = FontSizeRange(
                min = 12.sp,
                max = 15.sp,
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(all = getDP(R.dimen.dp_5))
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun GetCountryBasicDetail() {
    CountryBasicDetail(CountryData())
}