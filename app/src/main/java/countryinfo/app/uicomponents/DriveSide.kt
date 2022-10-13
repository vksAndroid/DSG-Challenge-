package countryinfo.app.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import countryinfo.app.R


@Composable
fun DriveSide(driverSide: String, clickAction: () -> Unit) {

    val isLeft = driverSide.toLowerCase().contains("left")

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 12.dp)
    ) {

        Text(
            text = "Left", fontWeight = FontWeight.Normal,
            color = if (isLeft) Color.DarkGray else Color.LightGray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 0.dp, end = 10.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.wheel),
            contentDescription = "Andy Rubin",
            modifier = Modifier.size(width = 20.dp, height = 20.dp)
        )
        Text(
            text = "Right", fontWeight = FontWeight.Normal,
            color = if (isLeft) Color.LightGray else Color.DarkGray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )


    }
}
