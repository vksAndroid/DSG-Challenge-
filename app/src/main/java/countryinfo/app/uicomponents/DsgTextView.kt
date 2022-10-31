package countryinfo.app.uicomponents

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit

@Composable
fun DsgTextView(
    modifier: Modifier = Modifier,
    value: String,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Gray,
    fontSize: TextUnit = TextUnit.Unspecified,
    style: TextStyle?,
    lines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
) {

    if (style == null) {
        Text(
            text = value,
            modifier = modifier,
            fontWeight = fontWeight,
            color = color,
            fontSize = fontSize,
            maxLines = lines,
            textAlign = textAlign
        )
    } else {
        Text(
            text = value,
            modifier = modifier,
            style = style,
            maxLines = lines,
            textAlign = textAlign
        )
    }


}