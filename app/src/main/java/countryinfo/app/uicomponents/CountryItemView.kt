package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import countryinfo.app.R
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
        modifier = Modifier.testTag("country_item_view")
            .fillMaxWidth()
            .padding(12.dp),
        onClick = { onItemClicked.invoke() },
        elevation = 1.dp,
        shape = RoundedCornerShape(12.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)

        ) {

            AsyncImage(
                model = countryFlag, contentDescription = stringResource(R.string.country_flag),
                placeholder = painterResource(id = R.drawable.default_loading),
                modifier = Modifier
                    .size(100.dp, 65.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(4.dp)
            ) {
                CountryItemTextView(
                    name = commonName ?: EMPTY_STRING,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                CountryItemTextView(
                    name = officialName ?: EMPTY_STRING,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
                CountryItemTextView(
                    name = capitalName,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            }
        }

    }

}


@Composable
fun CountryItemTextView(name: String, fontWeight: FontWeight, color: Color) {
    Text(
        text = name,
        fontWeight = fontWeight,
        style = MaterialTheme.typography.body1,
        color = color
    )
}


@Preview(showBackground = true)
@Composable
fun showCountryItemView() {
    CountryItemView(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, { })
}