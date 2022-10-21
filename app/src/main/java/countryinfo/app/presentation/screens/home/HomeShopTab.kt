package countryinfo.app.presentation.screens.home

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import countryinfo.app.R
import countryinfo.app.data.model.ProductVOs
import countryinfo.app.presentation.graph.BottomTab
import countryinfo.app.presentation.vm.DsgShopVm
import countryinfo.app.uicomponents.LoadingShimmerEffect
import countryinfo.app.uicomponents.DsgSearchComponent
import countryinfo.app.uicomponents.scaffold_comp.getDP

@Composable
fun HomeShopTab(viewModel: DsgShopVm, title: (String) -> Unit) {

    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            photoUri = it
        }
    )

    val errorState = viewModel.observeErrorState().collectAsState()

    val countList = listOf("5", "10", "15", "20", "25")
    var mExpanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    val getDsgData: List<ProductVOs> = viewModel.observeDsgList().collectAsState().value
    val query = viewModel.searchQuery().collectAsState().value
    title(BottomTab.TabDsgSearch.title)
    LaunchedEffect(key1 = query) {
        viewModel.searchByDebounce(query, viewModel.selectedCount.value)
        if (query.isEmpty()) {
            viewModel.clearSearch()
        }
    }

    LaunchedEffect(key1 = photoUri) {
        if (photoUri != null) {
            viewModel.getStringData(photoUri)
        }
    }

    Surface(
        modifier = Modifier
            .testTag("home_shop_screen")
            .fillMaxSize(), color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(getDP(dimenKey = R.dimen.dp_12))
            ) {
                val (search, drop) = createRefs()

                DsgSearchComponent(query = query,
                    isFocus = true,
                    modifier = Modifier
                        .testTag("shop_search_text_field")
                        .fillMaxWidth(fraction = .85f)
                        .constrainAs(search) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(drop.start)
                        },
                    onValueChange = {
                        viewModel.updateSearchQuery(it)
                    }) {
                    viewModel.convertSpeechToText()
                }

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
                        .background(
                            color = Color.LightGray, RoundedCornerShape(
                                getDP(dimenKey = R.dimen.dp_10)
                            )
                        ),

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

            OutlinedButton(onClick = {
                launcher.launch("image/*")
            }, modifier = Modifier.fillMaxWidth(.8f)) {
                Text(text = "Pick an image", color = Color.Black,
                )
            }

            if (getDsgData.isEmpty() && errorState.value.isEmpty() && viewModel.showShimmer.value) {
                LazyColumn(Modifier.testTag("shimmer_effect")) {
                    repeat(7) {
                        item { LoadingShimmerEffect() }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .testTag("search_result_list")
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
}
