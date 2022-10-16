package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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

@Composable
fun CountryDetailComponent(
    isImage: Boolean = false,
    imageUrl: String = "",
    title: String, value: Any,
    isDriverItem: Boolean = false
) {

    Card(
        elevation = 1.5.dp,
        modifier = Modifier
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
            .layoutId(title)
            .fillMaxWidth(0.46f),
        shape = RoundedCornerShape(12.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(bottom = 8.dp, top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = title, fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 0.dp, end = 10.dp)
            )
            if (isImage)
                ImageCoatOfArm(imageUrl)
            else if (isDriverItem)
                DriveSide(value as String)
            else
                ValueComponent(value = value)

        }
    }

}


@Preview(showBackground = true)
@Composable
fun ShowCountryDetailComponent() {
    CountryDetailComponent(false, "", "Capital", "Dublin")
}