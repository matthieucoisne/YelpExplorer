package com.yelpexplorer.features.business.presentation.businesslist

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yelpexplorer.R
import com.yelpexplorer.core.utils.StarsProvider
import com.yelpexplorer.core.theme.YelpExplorerTheme
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun BusinessListScreen(
    modifier: Modifier = Modifier,
    viewModel: BusinessListViewModel = koinViewModel(),
    onBusinessClicked: (String) -> Unit
) {
    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

    BusinessListContent(
        modifier = modifier,
        viewState = viewState,
        onBusinessClicked = onBusinessClicked
    )
}

@ExperimentalMaterial3Api
@Composable
fun BusinessListContent(
    modifier: Modifier = Modifier,
    viewState: BusinessListViewModel.ViewState,
    onBusinessClicked: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }
    ) { innerPadding ->
        when (viewState) {
            is BusinessListViewModel.ViewState.ShowBusinessList -> {
                BusinessList(
                    modifier = modifier.padding(innerPadding),
                    data = viewState.businessList.businessList,
                    onBusinessClicked = onBusinessClicked
                )
            }

            is BusinessListViewModel.ViewState.ShowError -> {
                CenteredText(
                    modifier = modifier.padding(innerPadding),
                    stringId = R.string.error_something_went_wrong
                )
            }

            is BusinessListViewModel.ViewState.ShowLoading -> {
                CenteredText(
                    modifier = modifier.padding(innerPadding),
                    stringId = R.string.loading
                )
            }
        }
    }
}

@Composable
fun CenteredText(
    modifier: Modifier = Modifier,
    @StringRes stringId: Int,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(id = stringId),
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun BusinessList(
    modifier: Modifier = Modifier,
    data: List<BusinessUiModel>,
    onBusinessClicked: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(horizontal = 8.dp),
    ) {
        itemsIndexed(items = data) { index, businessUiModel ->
            BusinessListItem(
                businessUiModel = businessUiModel,
                position = index + 1,
                onBusinessClicked = onBusinessClicked
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun BusinessListItem(
    modifier: Modifier = Modifier,
    businessUiModel: BusinessUiModel,
    position: Int,
    onBusinessClicked: (String) -> Unit,
) {
    val price = if (businessUiModel.price.isNotEmpty()) {
        stringResource(id = R.string.business_price, businessUiModel.price)
    } else ""

    ElevatedCard(
        onClick = {
            onBusinessClicked(businessUiModel.id)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier,
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(businessUiModel.photoUrl)
                    .crossfade(true)
                    .build(),
//                placeholder = painterResource(R.drawable.placeholder_business_list), // TODO make a dark version?
                error = painterResource(id = R.drawable.placeholder_business_list),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp),
            )
            Spacer(modifier = Modifier.size(4.dp))
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                Text(
                    text = stringResource(
                        id = R.string.business_name,
                        position,
                        businessUiModel.name.uppercase()
                    ),
//                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(id = StarsProvider.getDrawableId(businessUiModel.rating)),
                        contentDescription = null,
                        modifier = Modifier
                            .width(82.dp)
                            .height(14.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = pluralStringResource(
                            id = R.plurals.business_reviews_count,
                            count = businessUiModel.reviewCount,
                            businessUiModel.reviewCount
                        ),
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = "$price${businessUiModel.categories}",
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = businessUiModel.address,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                )
                // TODO use typography to avoid duplication
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoading() {
    YelpExplorerTheme(darkTheme = true) {
        Scaffold {
            CenteredText(
                stringId = R.string.loading,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewBusinessList() {
    val businessUiModel = BusinessUiModel(
        id = "id",
        name = "Jun i",
        photoUrl = "",
        rating = 4.5,
        reviewCount = 2,
        address = "4567 Rue St-Denis, Montreal",
        price = "$$",
        categories = "Sushi Bars, Japanese"
    )

    val data = MutableList(10) { businessUiModel }

    YelpExplorerTheme(darkTheme = true) {
        Scaffold {
            BusinessList(
                modifier = Modifier.padding(it),
                data = data,
                onBusinessClicked = {}
            )
        }
    }
}
