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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import countryinfo.app.api.model.CountryData


@Composable
fun CountryBasicDetail(countryData: CountryData?) {

    Card(
        elevation = 1.5.dp,
        modifier = Modifier
            .layoutId("BasicDetail")
            .padding(start = 12.dp, end = 12.dp),
        shape = RoundedCornerShape(10.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth(),

            Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            showItem("Region", countryData?.region!!)

            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(30.dp),
                color = Color.LightGray
            )

            showItem("SubRegion", countryData.subregion?: "")

            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(30.dp),
                color = Color.LightGray
            )

            showItem("Capital", countryData.capital[0])

        }

    }
}


@Composable
fun showItem(title: String, value: String) {

    Column(
        modifier = Modifier
            .padding(bottom = 8.dp, top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title, fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 0.dp, end = 10.dp)
        )
        Text(
            text = value, color = Color.DarkGray,
            fontWeight = FontWeight.Medium, fontSize = 14.sp,
            modifier = Modifier.padding(end = 10.dp, top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun getCountryBasicDetail() {
    CountryBasicDetail(null)
}