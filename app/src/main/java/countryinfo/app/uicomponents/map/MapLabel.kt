package countryinfo.app.uicomponents.map

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MapLabel(textLabel: String, textValue: String) {
    Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp)) {
        Text(
            text = textLabel,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
        Text(
            text = textValue,
            style = MaterialTheme.typography.body1,
            color = Color.DarkGray
        )
    }
}
