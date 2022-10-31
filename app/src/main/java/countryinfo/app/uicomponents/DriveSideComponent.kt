package countryinfo.app.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import countryinfo.app.R
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.contentDescriptionDriverSide


@Composable
fun DriveSideComponent(driverSide: String) {

    val isLeft = driverSide.lowercase().contains("left")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = getDP(dimenKey = R.dimen.dp_12))
    ) {

        DsgTextView(
            value = stringResource(id = R.string.left), fontWeight = FontWeight.Normal,
            color = if (isLeft) Color.DarkGray else Color.LightGray,
            fontSize = 16.sp,
            style = null,
            modifier = Modifier.padding(start = 0.dp, end = getDP(dimenKey = R.dimen.dp_10))
        )

        Image(
            painter = painterResource(id = R.drawable.wheel),
            contentDescription = contentDescriptionDriverSide,
            modifier = Modifier.size(width = getDP(dimenKey = R.dimen.dp_20)
                , height = getDP(dimenKey = R.dimen.dp_20))
        )
        DsgTextView(
            value = stringResource(id = R.string.right), fontWeight = FontWeight.Normal,
            color = if (isLeft) Color.LightGray else Color.DarkGray,
            fontSize = 16.sp,
            style = null,
            modifier = Modifier.padding(start = getDP(dimenKey = R.dimen.dp_10),
                end = getDP(dimenKey = R.dimen.dp_10))
        )


    }
}
