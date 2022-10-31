package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import countryinfo.app.R
import countryinfo.app.data.model.CurrenciesName
import countryinfo.app.theme.bulletTextStyle
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
        is String -> {
            RenderText(value)
        }
        is ArrayList<*> -> {

            if (value.size <= 1)
                RenderText(value[0].toString())
            else
                Column {
                    value.forEach { data ->
                        BulletItem(data.toString())
                    }
                }
        }
        is Map<*, *> -> {

            if (value.size <= 1) {
                for (currency in value) {

                    val name = if (currency.value is String)
                        currency.value as String
                    else {
                        val currencyModel = (currency.value as CurrenciesName)

                        "${currency.key} (${currencyModel.symbol + " " + currencyModel.name})"

                    }
                    RenderText(value = name)
                }
            } else {
                Column {

                    value.forEach { data ->
                        BulletItem(value[data.key.toString()].toString())

                    }
                }
            }

        }
        is Int -> {
            RenderText(value.toString().toLongOrNull().formatWithComma())
        }
        else -> {
            RenderText("$value")
        }

    }

}

@Composable
fun RenderText(value: String) {

    DsgTextView(
        value = value,
        style = bulletTextStyle,
        textAlign = TextAlign.Center,
        lines = 2,
        modifier = Modifier.padding(getDP(dimenKey = R.dimen.dp_5))
    )
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
        modifier = Modifier.padding(
            getDP(dimenKey = R.dimen.dp_4)
        ), style = bulletTextStyle
    )

}
