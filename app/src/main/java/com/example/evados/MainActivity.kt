package com.example.evados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.evados.data.Item
import com.example.evados.data.ItemDao
import com.example.evados.data.ListDB
import com.example.evados.ui.theme.EvaDosTheme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    lateinit var itemDao: ItemDao
    val dbDispatcher: CoroutineDispatcher = Dispatchers.IO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(dbDispatcher) {
            val db: ListDB = ListDB.getInstance(this@MainActivity)
            itemDao = db.itemDao()
        }

        setContent {
            var items: List<Item> by remember { mutableStateOf(emptyList()) }
            val (action, setAction) = remember { mutableStateOf(Action.LIST) }

            LaunchedEffect(Unit) {
                withContext(dbDispatcher) {
                    items = itemDao.getAll()
                }
            }

            val onAddItem: (Item) -> Unit = { item ->
                lifecycleScope.launch(dbDispatcher) {
                    itemDao.insert(item)
                    items = itemDao.getAll()
                }
                setAction(Action.LIST)
            }

            EvaDosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when(action) {
                        Action.LIST -> ItemsListUI(
                            items,
                            onAddItem = { setAction(Action.CREATE) }
                        )
                        Action.CREATE -> ItemFormUI(onAddItem)
                    }
                }
            }
        }
    }
}

enum class Action {
    CREATE,
    LIST,
    DELETE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListUI(items: List<Item>, onAddItem: () -> Unit = {}, modifier: Modifier = Modifier) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onAddItem() },
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
        if (items.isEmpty()) {
            // ItemFormUI(modifier = modifier)
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
                items(items.size) { index ->
                    Text(items[index].name)
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
            items = listOf(
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
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainActivityContentEmpty() {
    EvaDosTheme {
        ItemsListUI(emptyList())
    }
}
