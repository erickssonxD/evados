package com.example.evados

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.evados.data.Item
import com.example.evados.ui.theme.EvaDosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListUI(
    itemList: List<Item>,
    onCreateItemButtonClick: () -> Unit,
    onUpdateItemButtonClick: () -> Unit,
    onDeleteItemButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onCreateItemButtonClick() },
                icon = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(R.string.content_description_add_button)
                    )
                },
                text = { Text(stringResource(R.string.button_add_text)) }
            )
        }
    ) { contentPadding ->
        if (itemList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.text_no_items))
            }
        } else {
            LazyColumn(modifier = modifier) {
                items(itemList) { item ->
                    ItemRowUI(item, onDeleteItemButtonClick, onUpdateItemButtonClick)
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun MainActivityContentFilled() {
    EvaDosTheme {
        ItemsListUI(
            itemList = listOf(
                Item(name = "Item 1"),
                Item(name = "Item 2"),
                Item(name = "Item 3"),
                Item(name = "Item 4"),
                Item(name = "Item 5"),
                Item(name = "Item 6"),
                Item(name = "Item 7"),
                Item(name = "Item 8"),
                Item(name = "Item 9"),
                Item(name = "Item 10"),
            ),
            onCreateItemButtonClick = {},
            onUpdateItemButtonClick = {},
            onDeleteItemButtonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityContentEmpty() {
    EvaDosTheme {
        ItemsListUI(emptyList(), onCreateItemButtonClick = {}, onUpdateItemButtonClick = {}, onDeleteItemButtonClick = {})
    }
}
