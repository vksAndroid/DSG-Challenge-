package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
 import coil.compose.AsyncImage
import countryinfo.app.R
 import countryinfo.app.ui.screens.CountryItemTextView


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
            .fillMaxWidth()
            .padding(12.dp),
        onClick = { onItemClicked.invoke() },
        elevation = 0.dp,
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = countryFlag, contentDescription = stringResource(R.string.country_flag),
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
                    name = commonName ?: "",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                CountryItemTextView(
                    name = officialName ?: "",
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



@Preview(showBackground = true)
@Composable
fun showCountryItemView() {
    CountryItemView("","","","", { } )
}