package countryinfo.app.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SettingsVoice
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import countryinfo.app.R
import countryinfo.app.data.model.ProductVOs
import countryinfo.app.presentation.graph.BottomTab
import countryinfo.app.presentation.vm.DsgShopVm
import countryinfo.app.uicomponents.DsgSearchComponent
import countryinfo.app.uicomponents.scaffold_comp.getDP

@Composable
fun HomeShopTab(viewModel: DsgShopVm, title: (String) -> Unit) {

    val countList = listOf("5", "10", "15", "20", "25")
    var mExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    val getDsgData: List<ProductVOs> = viewModel.observeDsgList().collectAsState().value
    title(BottomTab.TabDsgSearch.title)
    val focusRequester = remember { FocusRequester() }

    Surface(modifier = Modifier.testTag("home_shop_screen").fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(getDP(dimenKey = R.dimen.dp_12))
            ) {
                val (search, drop) = createRefs()
                DsgSearchTextField(
                    modifier = Modifier
                        .fillMaxWidth(fraction = .85f)
                        .constrainAs(search) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(drop.start)
                        },
                    viewModel = viewModel,
                    focus = focusRequester
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.15f)
                        .height(45.dp)
                        .constrainAs(drop) {
                            start.linkTo(search.end)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(search.bottom)
                        }
                        .background(color = Color.LightGray, RoundedCornerShape(
                            getDP(dimenKey = R.dimen.dp_10)
                        )),

                    contentAlignment = Alignment.Center
                ) {

                    Row {
                        Text(
                            countList[selectedIndex], modifier = Modifier
                                .wrapContentWidth()
                                .clickable(onClick = { mExpanded = true }),
                            color = Color.Black
                        )
                        Icon(
                            imageVector = if (mExpanded) Icons.Filled.ArrowDropUp
                            else Icons.Filled.ArrowDropDown,
                            contentDescription = "",
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable { mExpanded = true }
                        )
                    }

                    DropdownMenu(
                        modifier = Modifier
                            .fillMaxWidth(.2f)
                            .wrapContentHeight(),
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false },
                    ) {
                        countList.forEachIndexed { index, label ->
                            DropdownMenuItem(onClick = {
                                mExpanded = false
                                selectedIndex = index
                                viewModel.selectedCount.value = label
                                if (viewModel.searchQuery().value.isNotEmpty()) {
                                    viewModel.searchByDebounce(
                                        viewModel.searchQuery().value,
                                        viewModel.selectedCount.value
                                    )
                                }
                            }) {
                                Text(
                                    text = label,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.testTag("search_result_list")
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = getDsgData) { data ->
                    Text(
                        text = data.name, color = Color.Black,
                        modifier = Modifier
                            .padding(getDP(dimenKey = R.dimen.dp_12)),
                        textAlign = TextAlign.Start,
                        maxLines = 2

                    )
                }
            }
        }

    }
}


@Composable
fun DsgSearchTextField(modifier: Modifier, viewModel: DsgShopVm, focus: FocusRequester) {

    val query = viewModel.searchQuery().collectAsState().value
    var isVoicePermissionGranted = checkRecordAudioPermission()

    LaunchedEffect(key1 = query) {
        viewModel.searchByDebounce(query, viewModel.selectedCount.value)
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
        modifier = modifier
            .testTag("shop_search_text_field")
            .padding(all = getDP(dimenKey = R.dimen.dp_8))
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
