package countryinfo.app.presentation.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SettingsVoice
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import countryinfo.app.R
import countryinfo.app.presentation.graph.BottomTab
import countryinfo.app.presentation.vm.CountryListVm
import countryinfo.app.presentation.vm.DsgShopVm
import countryinfo.app.uicomponents.scaffold_comp.getDP
import countryinfo.app.utils.EMPTY_STRING
import countryinfo.app.utils.checkRecordAudioPermission

@Composable
fun HomeShopTab(viewModel: DsgShopVm, title: (String) -> Unit) {

    val getDsgData = viewModel.observeDsgList().collectAsState()
    title(BottomTab.TabDsgSearch.title)
    val focusRequester = remember { FocusRequester() }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = getDP(dimenKey = R.dimen.dp_20))
        ) {

            SearchTextField(viewModel, focusRequester)

            Text(
                text = getDsgData.value, color = Color.Black,
                modifier = Modifier.padding(getDP(dimenKey = R.dimen.dp_12)).verticalScroll(rememberScrollState())
            )

        }
    }
}


@Composable
fun SearchTextField(viewModel: DsgShopVm, focus: FocusRequester) {

    val query = viewModel.searchQuery().collectAsState().value
    var isVoicePermissionGranted = checkRecordAudioPermission()

    LaunchedEffect(key1 = query) {
        viewModel.searchByDebounce(query)
        if (query.isEmpty()) {
            viewModel.clearSearch()
        }
    }

    TextField(
        value = query,
        onValueChange = {
            viewModel.updateSearchQuery(it)
        },
        placeholder = { Text(text = stringResource(id = R.string.search)) },
        modifier = Modifier
            .testTag("country_search_text_field")
            .padding(all = getDP(dimenKey = R.dimen.dp_8))
            .fillMaxWidth()
            .focusRequester(focusRequester = focus)
            .border(
                width = getDP(dimenKey = R.dimen.dp_8),
                color = Color.White,
                shape = RoundedCornerShape(
                    getDP(dimenKey = R.dimen.dp_20)
                )
            ),
        singleLine = true,
        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = EMPTY_STRING) },
        trailingIcon = {
            if (isVoicePermissionGranted) {
                IconButton(
                    onClick = {
                        if (isVoicePermissionGranted) {
                            viewModel.convertSpeechToText()
                        }
                    },
                ) {
                    Icon(
                        Icons.Default.SettingsVoice,
                        contentDescription = EMPTY_STRING,
                        tint = Color.Gray
                    )
                }
            } else {
                null
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Gray
        ),
        shape = RoundedCornerShape(getDP(dimenKey = R.dimen.dp_20))
    )

    focus.requestFocus()
}
