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
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import countryinfo.app.ui.theme.OffWhite


@OptIn(ExperimentalTextApi::class)
@Composable
fun CountryNameCard(title: String, value: String) {

    Card(
        elevation = 1.5.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = OffWhite,
        modifier = Modifier
            .layoutId("Country")
            .wrapContentHeight(Alignment.Bottom)
            .fillMaxWidth(0.6f)
            .wrapContentWidth(Alignment.Start)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp),
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
            Text(
                text = value, color = Color.DarkGray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 0.dp),
                style = MaterialTheme.typography.body1.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun getCountry() {
    showItem("Capital", "Dublin")
}