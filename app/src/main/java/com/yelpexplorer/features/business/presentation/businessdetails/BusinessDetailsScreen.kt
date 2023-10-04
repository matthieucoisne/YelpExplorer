package com.yelpexplorer.features.business.presentation.businessdetails

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yelpexplorer.R
import com.yelpexplorer.core.utils.Const
import com.yelpexplorer.core.utils.StarsProvider
import com.yelpexplorer.core.theme.YelpExplorerTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: BusinessDetailsViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

    BusinessDetailsContent(
        modifier = modifier,
        viewState = viewState,
        onBackPressed = onBackPressed
    )
}

@ExperimentalMaterial3Api
@Composable
fun BusinessDetailsContent(
    modifier: Modifier = Modifier,
    viewState: BusinessDetailsViewModel.ViewState,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        when (viewState) {
            is BusinessDetailsViewModel.ViewState.ShowBusinessDetails -> {
                BusinessDetails(
                    modifier = modifier.padding(innerPadding),
                    businessDetailsUiModel = viewState.businessDetails,
                )
            }

            is BusinessDetailsViewModel.ViewState.ShowError -> {
                CenteredText(
                    modifier = modifier.padding(innerPadding),
                    stringId = R.string.error_something_went_wrong
                )
            }

            is BusinessDetailsViewModel.ViewState.ShowLoading -> {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessDetails(
    modifier: Modifier = Modifier,
    businessDetailsUiModel: BusinessDetailsUiModel
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        BusinessPhoto(
            modifier = Modifier,
            businessDetailsUiModel = businessDetailsUiModel,
        )
        Spacer(modifier = Modifier.size(16.dp))
        BusinessInfo(
            modifier = Modifier.padding(horizontal = 8.dp),
            businessDetailsUiModel = businessDetailsUiModel,
        )
        Spacer(modifier = Modifier.size(16.dp))
        BusinessHours(
            modifier = Modifier.padding(horizontal = 8.dp),
            openingHours = businessDetailsUiModel.openingHours,
        )
        Spacer(modifier = Modifier.size(16.dp))
        BusinessReviews(
            modifier = Modifier.padding(horizontal = 8.dp),
            data = businessDetailsUiModel.reviews
        )
    }
}

@Composable
fun BusinessPhoto(
    modifier: Modifier,
    businessDetailsUiModel: BusinessDetailsUiModel,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(businessDetailsUiModel.photoUrl)
            .crossfade(true)
            .build(),
//        placeholder = painterResource(R.drawable.placeholder_business_list), // TODO make a dark version?
        error = painterResource(id = R.drawable.placeholder_business_list),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth(),
    )
}

@ExperimentalMaterial3Api
@Composable
fun BusinessInfo(
    modifier: Modifier,
    businessDetailsUiModel: BusinessDetailsUiModel,
) {
    val price = if (businessDetailsUiModel.price.isNotEmpty()) {
        stringResource(id = R.string.business_price, businessDetailsUiModel.price)
    } else ""

    Column(modifier = modifier) {
        Text(
            text = businessDetailsUiModel.name.uppercase(),
//            style = MaterialTheme.typography.bodyMedium,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Image(
                painter = painterResource(id = StarsProvider.getDrawableId(businessDetailsUiModel.rating)),
                contentDescription = null,
                modifier = Modifier
                    .width(82.dp)
                    .height(14.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = pluralStringResource(
                    id = R.plurals.business_reviews_count,
                    count = businessDetailsUiModel.reviewCount,
                    businessDetailsUiModel.reviewCount
                ),
                fontSize = 12.sp,
            )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = "$price${businessDetailsUiModel.categories}",
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = businessDetailsUiModel.address,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        // TODO use typography to avoid duplication
    }
}

@Composable
fun BusinessHours(
    modifier: Modifier,
    openingHours: Map<Int, String>,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.opening_hours),
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.size(4.dp))
        (0 until Const.DAYS.size).forEach { day ->
            Spacer(modifier = Modifier.size(6.dp))
            Row {
                Text(
                    text = stringResource(id = Const.DAYS[day]!!),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = openingHours[day] ?: stringResource(id = R.string.closed),
                    fontSize = 12.sp,
                    lineHeight = 1.4.em,
                    modifier = Modifier.weight(3f)
                )
            }
        }
    }
}

@Composable
fun BusinessReviews(
    modifier: Modifier,
    data: List<ReviewUiModel>,
) {
    Column(modifier = modifier) {
        if (data.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.latest_reviews),
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.size(8.dp))
            (data.indices).forEach {
                BusinessReview(
                    modifier = Modifier,
                    reviewUiModel = data[it]
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun BusinessReview(
    modifier: Modifier,
    reviewUiModel: ReviewUiModel
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(2.dp),
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(reviewUiModel.userImageUrl)
                        .crossfade(true)
                        .build(),
//                    placeholder = painterResource(R.drawable.placeholder_user), // TODO make a dark version?
                    error = painterResource(id = R.drawable.placeholder_user),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(
                        text = reviewUiModel.userName,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Image(
                            painter = painterResource(id = StarsProvider.getDrawableId(reviewUiModel.rating)),
                            contentDescription = null,
                            modifier = Modifier
                                .width(82.dp)
                                .height(14.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = reviewUiModel.timeCreated,
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = reviewUiModel.text,
                fontSize = 13.sp,
                lineHeight = 1.3.em,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
fun PreviewBusinessDetails() {
    YelpExplorerTheme(darkTheme = true) {
        Scaffold {
            BusinessDetails(
                modifier = Modifier
                    .padding(it)
                    .padding(8.dp),
                businessDetailsUiModel = BusinessDetailsUiModel(
                    id = "id",
                    name = "Jun i",
                    photoUrl = "",
                    rating = 3.5,
                    reviewCount = 2,
                    address = "4567 Rue St-Denis, Montreal",
                    price = "$$",
                    categories = "Sushi Bars, Japanese",
                    openingHours = mapOf(
                        2 to "4:30 pm - 10:00 pm",
                        3 to "4:30 pm - 10:00 pm",
                        4 to "4:30 pm - 10:00 pm",
                        5 to "4:30 pm - 10:00 pm",
                        6 to "4:30 pm - 10:00 pm",
                    ),
                    reviews = listOf(
                        ReviewUiModel(
                            userName = "Matthieu C.",
                            userImageUrl = "",
                            text = "This place is well worth the money.",
                            rating = 5.0,
                            timeCreated = "4/21/2020"
                        )
                    ),
                ),
            )
        }
    }
}
