package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import countryinfo.app.R
import countryinfo.app.data.model.CountryData
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
                .padding(
                    start = getDP(dimenKey = R.dimen.dp_12),
                    end = getDP(dimenKey = R.dimen.dp_12)
                )
                .fillMaxWidth(),

            Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            ShowItem(stringResource(id = R.string.region), countryData.region)

            Divider(
                modifier = Modifier
                    .width(getDP(dimenKey = R.dimen.dp_1))
                    .height(getDP(dimenKey = R.dimen.dp_30)),
                color = Color.LightGray
            )

            ShowItem(stringResource(id = R.string.subregion), countryData.subregion ?: "")

            Divider(
                modifier = Modifier
                    .width(getDP(dimenKey = R.dimen.dp_1))
                    .height(getDP(dimenKey = R.dimen.dp_30)),
                color = Color.LightGray
            )

            val capital = if (countryData.capital.isNotEmpty()) countryData.capital[0] else ""
            ShowItem(stringResource(id = R.string.capital), capital)
        }
    }
}


@Composable
fun ShowItem(title: String, value: String) {

    Column(
        modifier = Modifier
            .padding(bottom = getDP(dimenKey = R.dimen.dp_8), top = getDP(dimenKey = R.dimen.dp_8)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title, fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            fontSize = 16.sp,
            modifier = Modifier.padding(end = getDP(R.dimen.dp_10))
        )
        Text(
            text = value, color = Color.DarkGray,
            fontWeight = FontWeight.Medium, fontSize = 14.sp,
            modifier = Modifier.padding(end = getDP(R.dimen.dp_10), top = getDP(R.dimen.dp_5))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GetCountryBasicDetail() {
    CountryBasicDetail(CountryData())
}