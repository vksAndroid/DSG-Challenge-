package countryinfo.app.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SettingsVoice
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import countryinfo.app.R
import countryinfo.app.theme.SearchBG
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.EMPTY_STRING
import countryinfo.app.utils.checkRecordAudioPermission


@Composable
fun DsgSearchComponent(query : String,
                       isFocus: Boolean = false,
                       modifier: Modifier = Modifier,
                       onValueChange : (String)->Unit
                       , onVoiceClick : ()->Unit) {

    val focusRequester = remember { FocusRequester() }

    val isVoicePermissionGranted = checkRecordAudioPermission()

    TextField(
        value = query,
        onValueChange = {
            onValueChange(it)
         },

        placeholder = {
            Text(text = stringResource(id = R.string.search),
                fontWeight = FontWeight.Normal,color = Color.Black)
        },
        modifier = modifier
            .testTag("country_search_text_field")
            .border(
                width = getDP(dimenKey = R.dimen.dp_16),
                color = Color.White,
                shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_30))
            )
            .padding(all = getDP(dimenKey = R.dimen.dp_8))
            .fillMaxWidth()
            .background(SearchBG)
            .focusRequester(focusRequester),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = EMPTY_STRING, tint = Color.Gray) },
        trailingIcon = {
            if (isVoicePermissionGranted) {
                IconButton(
                    onClick = {
                            onVoiceClick.invoke()

                    },
                ) {
                    Icon(
                        Icons.Default.SettingsVoice,
                        contentDescription = EMPTY_STRING,
                        tint = Color.Gray
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Gray
        ),
        shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_20))
    )

    if(isFocus)
        focusRequester.requestFocus()
}