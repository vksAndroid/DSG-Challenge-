package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import countryinfo.app.api.model.CurrenciesName


@Composable
fun ValueComponent(value: Any) {

    when (value) {
        is String -> {
            ValueText(value)
        }
        is ArrayList<*> -> {

            if (value.size <= 1)
                ValueText(value[0].toString())
            else
                Column {
                    value.forEach { data ->
                        bulletItem(data.toString())
                    }
                }
        }
        is Map<*, *> -> {

            if (value.size <= 1)
                for (currency in value) {

                    val name = if (currency.value is String)
                        currency.value
                    else
                        (currency.value as CurrenciesName).name

                    ValueText("${currency.key} ($name)")
                }
            else
                Column {

                    value.forEach { data ->
                        bulletItem(value[data.key.toString()].toString())

                    }
                }
        }
    }

}

@Composable
fun bulletItem(data: String) {

    val bullet = "\u2022"

    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
    Text(
        buildAnnotatedString {
            withStyle(style = paragraphStyle) {
                append(bullet)
                append("\t")
                append(data)
            }
        },
        color = Color.Gray,
        fontWeight = FontWeight.Medium, fontSize = 14.sp,
        modifier = Modifier.padding(2.dp)
    )

}
