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
import androidx.compose.ui.unit.sp
import countryinfo.app.R
import countryinfo.app.data.model.CurrenciesName
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.formatWithComma

/**
 * This function return Any type of Composable view depends upon sending value
 *
 * @param value Any type of value
 */
@Composable
fun ValueComponent(value: Any) {

    when (value) {
        is String ->
            RenderText(value)

        is ArrayList<*> ->
        RenderList(list = value)

        is Map<*, *> ->
            RenderCurrency(map = value)

        is Int ->
            RenderText(value.toString().toLongOrNull().formatWithComma())

        else -> RenderText("$value")

    }

}

@Composable
fun RenderText(value : String) {

    DsgTextView(value=value, fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.padding(getDP(dimenKey = R.dimen.dp_10)))

}

@Composable
fun RenderCurrency(map : Map<*,*>) {

    if (map.size <= 1) {
        for (currency in map) {

            val name = if (currency.value is String)
                currency.value as String
            else {
                val currencyModel = (currency.value as CurrenciesName)

                "${currency.key} (${ currencyModel.symbol + " " + currencyModel.name})"

            }
            RenderText(value = name)
        }
    } else {
        Column {

            map.forEach { data ->
                BulletItem(map[data.key.toString()].toString())

            }
        }
    }
}

@Composable
fun RenderList(list : ArrayList<*>) {
    if (list.size <= 1)
        RenderText(list[0].toString())
    else
        Column {
            list.forEach { data ->
                BulletItem(data.toString())
            }
        }
}

@Composable
fun BulletItem(data: String) {

    val bullet = "\u2022"

    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))

    Text(
        buildAnnotatedString {
            withStyle(style = paragraphStyle) {
                append(bullet)
                append("\t\t")
                append(data)
            }
        },
        color = Color.Gray,
        fontWeight = FontWeight.Medium, fontSize = 14.sp,
        modifier = Modifier.padding(getDP(dimenKey = R.dimen.dp_4))
    )

}
