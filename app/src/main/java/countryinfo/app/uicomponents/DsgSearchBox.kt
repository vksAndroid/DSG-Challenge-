package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun DsgSearchBox(
    modifier: Modifier = Modifier,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable() () -> Unit)? = null
) {

//    val state = savedInstanceState(saver = TextFieldValue.Saver) { TextFieldValue() }


//    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
//        if (leadingIcon != null) {
//            leadingIcon()
//        }
//        Box(
//            modifier = Modifier.weight(1f)
//                .padding(start = paddingLeadingIconEnd, end = paddingTrailingIconStart)
//        ) {
//            TextField(
//                value = state.value,
//                onValueChange = { state.value = it }
//            )
//            if (state.value.text.isEmpty()) {
//                Text(
//                    text = "Placeholder"
//                )
//            }
//        }

 //   }
}