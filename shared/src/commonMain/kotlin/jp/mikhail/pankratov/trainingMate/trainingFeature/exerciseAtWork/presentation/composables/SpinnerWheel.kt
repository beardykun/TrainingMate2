package jp.mikhail.pankratov.trainingMate.trainingFeature.exerciseAtWork.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpinnerWheel(
    items: List<Int>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    val itemHeight = 40.dp
    val scrollState = rememberLazyListState()
    LaunchedEffect(Unit){
        scrollState.scrollToItem(items.indexOf(selectedItem))
    }
    LaunchedEffect(scrollState) {
        snapshotFlow {
            scrollState.layoutInfo
        }.collect { layoutInfo ->
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isNotEmpty()) {
                val middleIndex = visibleItems.size / 2
                val middleItem = visibleItems[middleIndex]
                onItemSelected(items[middleItem.index])
            }
        }
    }
    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .height(itemHeight * 5) // Display 5 items at a time
            .width(80.dp) // Adjust the width as needed
            .background(color = Color.Gray, shape = RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items) { item ->
            Box(
                modifier = Modifier
                    .height(itemHeight)
                    .fillMaxWidth()
                    .background(
                        if (item == selectedItem) Color.Blue else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.toString(),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 24.sp,
                        color = if (item == selectedItem) Color.White else Color.Black
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
