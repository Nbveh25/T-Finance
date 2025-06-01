package ru.practice.t_finance.presentation.screens.expenses

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import ru.practice.t_finance.R
import ru.practice.t_finance.presentation.components.BudgetDiagram
import ru.practice.t_finance.presentation.components.DateRangeButton
import ru.practice.t_finance.presentation.components.TextSwitch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import ru.practice.t_finance.presentation.components.EmptyDiagram
import ru.practice.t_finance.presentation.components.ExpensesDateRangePicker
import ru.practice.t_finance.presentation.components.TransactionSlot
import ru.practice.t_finance.presentation.components.TransactionsSlotExpenses


@Composable
fun ExpensesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ExpensesViewModel = hiltViewModel()
) {



    val uiState = viewModel.uiState.collectAsState()



    val startDate = viewModel.startDate.collectAsState()
    val endDate = viewModel.endDate.collectAsState()



    LaunchedEffect(Unit) {
        viewModel.loadData(startDate.value,endDate.value)
    }

    val pagerState = rememberPagerState(
            pageCount = { Int.MAX_VALUE },
            initialPage = Int.MAX_VALUE / 2
        )

    val selectedPage = remember {
        derivedStateOf { pagerState.currentPage }
    }
    val coroutineScope = rememberCoroutineScope()

    val periodType = viewModel.periodType.collectAsState()

    val selectedIndex = remember(periodType) {
       derivedStateOf {
           when (periodType.value){
               PeriodType.DAY -> 0
               PeriodType.WEEK -> 1
               PeriodType.MONTH -> 2
               PeriodType.YEAR -> 3
           }
       }

    }

    var lastLoadedPage by rememberSaveable { mutableIntStateOf(Int.MAX_VALUE/2) }

    val rangeTitle = remember {
        derivedStateOf {
            viewModel.formatPeriodForDisplay(startDate.value,endDate.value)
        }
    }

    var showDatePicker by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()



    LaunchedEffect(selectedPage.value) {
        if (pagerState.currentPage != lastLoadedPage) {
            val pageOffset = pagerState.currentPage - lastLoadedPage
            viewModel.shiftPeriod(offset = pageOffset)
            lastLoadedPage = pagerState.currentPage
        }

    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = modifier.fillMaxSize()
            .verticalScroll(scrollState)) {
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = "Назад",
                        modifier = Modifier.size(112.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                Text(
                    style = MaterialTheme.typography.displayLarge,
                    text = stringResource(R.string.expenses_screen_title),
                )

                Spacer(modifier = Modifier.padding(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    DateRangeButton(title = rangeTitle.value) {
                        Log.d("MyLog",showDatePicker.toString())
                        showDatePicker = true
                    }
                    Spacer(Modifier.weight(1f))
                    when (val state = uiState.value) {
                        is ExpensesUiState.Loading -> {
                            Text(
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 32.sp),
                                text = "Загрузка..."
                            )
                        }

                        is ExpensesUiState.Error -> {
                            Text(
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 32.sp),
                                text = "Ошибка"
                            )
                        }

                        is ExpensesUiState.Success -> {
                            Text(
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 32.sp),
                                text = "${state.totalExpenses} ₽"
                            )
                        }
                    }

                }
            }
            Box(Modifier.fillMaxWidth()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.align(Alignment.Center),
                    pageSize = PageSize.Fill,
                    contentPadding = PaddingValues(horizontal = 56.dp),
                    beyondViewportPageCount = 0
                ) { page ->

                    val currentState = uiState.value


                    if (page == selectedPage.value) {
                        // Только для активной страницы
                        when (currentState) {
                            is ExpensesUiState.Loading -> {
                                Box(Modifier.fillMaxSize()) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                            }

                            is ExpensesUiState.Error -> {
                                EmptyDiagram(
                                    thickness = 40.dp
                                )
                            }

                            is ExpensesUiState.Success -> {
                                if (currentState.categories.isNotEmpty()) {
                                    BudgetDiagram(
                                        data = currentState.categories,
                                        thickness = 40.dp
                                    )
                                } else {
                                    Box(modifier = Modifier.fillMaxWidth())
                                }
                            }
                        }
                    } else {
                        // Не отображаем ничего
                        Box(modifier = Modifier.fillMaxWidth())
                    }
                }
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (selectedPage.value > 0) {
                                pagerState.animateScrollToPage(selectedPage.value - 1)
                            }
                        }
                    }, modifier.align(alignment = Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_chevron_left_24),
                        contentDescription = "Назад",
                        modifier = Modifier.size(112.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (pagerState.currentPage < pagerState.pageCount - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }, modifier.align(alignment = Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_chevron_right_24),
                        contentDescription = "Вперед",
                        modifier = Modifier.size(112.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            TextSwitch(
                modifier = Modifier,
                selectedIndex = selectedIndex.value,
                onSelectionChange = {
                    viewModel.changePeriodType(it)
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            when (val state = uiState.value){
                is ExpensesUiState.Success -> {
                    TransactionsSlotExpenses(modifier = Modifier.padding(horizontal = 8.dp).height(250.dp),transactionModelList = state.transactions)
                }
                is ExpensesUiState.Error -> {
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .height(100.dp)
                            .shadow(
                                elevation = dimensionResource(R.dimen.card_shadow_elevation_medium),
                                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_shape_large))
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )

                    ){
                        Text(
                            text = "Ошибка",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(
                                vertical = dimensionResource(R.dimen.padding_small)
                            ),
                        )
                    }
                }
                is ExpensesUiState.Loading -> {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))

        }
        if (showDatePicker) {
            ExpensesDateRangePicker(
                onDateRangeSelected = { range ->
                    // Преобразуем миллисекунды в строки дат
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val start = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(range.first!!),
                        ZoneId.systemDefault()
                    ).format(formatter)
                    val end = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(range.second!!),
                        ZoneId.systemDefault()
                    ).format(formatter)
                    viewModel.loadData(start, end)
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }

}



//@Preview
//@Composable
//private fun Preview(){
//    TfinanceTheme {
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            containerColor = MaterialTheme.colorScheme.background
//        ){ padding ->
//            ExpensesScreen(Modifier.padding(padding), {}, data = listOf(
//                Category(
//                    name = "dasda",
//                    color = Color(0xFF0000FF),
//                    value = 40
//                ),
//                Category(
//                    name = "dasda",
//                    color = Color(0xFFFF0099),
//                    value = 30
//                )
//                ,Category(
//                    name = "dasda",
//                    color = Color(0xFFEAA114),
//                    value = 10
//                )
//                ,Category(
//                    name = "dasda",
//                    color = Color(0xFF00D2C9),
//                    value = 20
//                )
//            ))
//        }
//    }
//}