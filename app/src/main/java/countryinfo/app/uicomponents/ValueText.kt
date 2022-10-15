package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ValueText(value: String) {

    Text(
        text = value, color = Color.Gray,
        fontWeight = FontWeight.Medium, fontSize = 14.sp,
        modifier = Modifier.padding(10.dp)
    )

}
