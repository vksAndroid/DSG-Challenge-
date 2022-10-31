package countryinfo.app.uicomponents.map

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import countryinfo.app.theme.mapLabelTextStyle
import countryinfo.app.theme.mapValueTextStyle
import countryinfo.app.uicomponents.DsgTextView

@Composable
fun MapLabel(textLabel: String, textValue: String) {
    Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp)) {
        DsgTextView(
            value = textLabel,
            style = mapLabelTextStyle
            )
        if (textValue.isNotEmpty()) {
            DsgTextView(
                value = textValue,
                style = mapValueTextStyle
            )
        }
    }
}
