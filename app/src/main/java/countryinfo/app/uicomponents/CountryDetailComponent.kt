package countryinfo.app.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import countryinfo.app.R
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.EMPTY_STRING

@Composable
fun CountryDetailComponent(
    isImage: Boolean = false,
    imageUrl: String = EMPTY_STRING,
    title: String, value: Any,
    isDriverItem: Boolean = false
) {

    Card(
        elevation = 1.5.dp,
        modifier = Modifier
            .padding(top = getDP(dimenKey = R.dimen.dp_12),
                start = getDP(dimenKey = R.dimen.dp_12),
                end = getDP(dimenKey = R.dimen.dp_12),
                bottom = getDP(dimenKey = R.dimen.dp_4))
            .layoutId(title)
            .fillMaxWidth(0.46f),
        shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_12))
    ) {

        Column(
            modifier = Modifier
                .padding(bottom = getDP(dimenKey = R.dimen.dp_8), top = getDP(dimenKey = R.dimen.dp_8)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = title, fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                fontSize = 16.sp,
                modifier = Modifier.padding( end = getDP(dimenKey = R.dimen.dp_10))
            )
            if (isImage)
                ImageCoatOfArm(imageUrl)
            else if (isDriverItem)
                DriveSideComponent(value as String)
            else
                ValueComponent(value = value)

        }
    }

}