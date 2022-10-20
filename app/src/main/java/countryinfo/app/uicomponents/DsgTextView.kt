package countryinfo.app.uicomponents

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@Composable
fun DsgTextView(modifier : Modifier = Modifier,
                value: String,
                fontWeight: FontWeight,
                color: Color = Color.Gray,
                fontSize : TextUnit = TextUnit.Unspecified,

) {
    Text(
        text = value,
        fontWeight = fontWeight,
        color = color,
        modifier = modifier,
        fontSize = fontSize
    )
}