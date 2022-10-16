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
import coil.compose.AsyncImage
import countryinfo.app.R
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
        modifier = Modifier.testTag("country_item_view")
            .fillMaxWidth()
            .padding(getDP(dimenKey = R.dimen.dp_12)),
        onClick = { onItemClicked.invoke() },
        elevation = getDP(dimenKey = R.dimen.dp_1),
        shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_12))

    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(getDP(dimenKey = R.dimen.dp_4))

        ) {

            AsyncImage(
                model = countryFlag, contentDescription = stringResource(R.string.country_flag),
                placeholder = painterResource(id = R.drawable.default_placeholder),
                modifier = Modifier
                    .size(width = getDP(dimenKey = R.dimen.dp_100),
                        height = getDP(dimenKey = R.dimen.dp_65))
                    .padding(getDP(dimenKey = R.dimen.dp_8))
            )
            Column(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(getDP(dimenKey = R.dimen.dp_4))
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

