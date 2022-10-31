package countryinfo.app.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

val countryBasicDetailTitle =
    TextStyle(color = Color.DarkGray, fontSize = 16.sp, fontWeight = FontWeight.Bold)

val detailsTextStyle = TextStyle(color = Color.DarkGray, fontSize = 16.sp, fontWeight = FontWeight.Bold)

val bulletTextStyle = TextStyle(color = Color.Gray,
    fontWeight = FontWeight.Medium, fontSize = 14.sp)

val dsgTitleBlackBold = TextStyle(fontWeight = FontWeight.Bold,
    color = Color.Black)

val dsgTitleDarkGrayMedium = TextStyle(fontWeight = FontWeight.Medium,
    color = Color.DarkGray)

val dsgTitleGrayMedium = TextStyle(fontWeight = FontWeight.Medium,
    color = Color.Gray)

val countryName = TextStyle(fontWeight = FontWeight.Bold,
    color = Color.Black, fontSize = 22.sp)

val mapLabelTextStyle = TextStyle(fontWeight = FontWeight.Medium,
    color = Color.DarkGray, fontSize = 16.sp)

val mapValueTextStyle = TextStyle(fontWeight = FontWeight.Medium,
    color = Color.Gray, fontSize = 16.sp)




