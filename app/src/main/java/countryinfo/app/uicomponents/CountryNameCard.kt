package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CountryNameCard( title: String, value: String) {

    Card(elevation = 6.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.LightGray,
                modifier = Modifier
        .layoutId("Country")
                    .wrapContentHeight(Alignment.Bottom)
                    .fillMaxWidth(0.6f)
         .wrapContentWidth(Alignment.Start)) {
        Column(
             modifier = Modifier
                 .wrapContentHeight()
                .padding(start = 12.dp,end =12.dp, top = 4.dp, bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title, fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                maxLines = 1,
                 fontSize = 22.sp,
            )
            Text(
                text = value, color = Color.DarkGray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                fontWeight = FontWeight.Medium, fontSize = 18.sp,
                modifier = Modifier.padding(top=0.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun getCountry() {
    showItem("Capital", "Dublin")
}