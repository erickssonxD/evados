package com.example.evados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.evados.data.Item
import com.example.evados.data.ListDB
import com.example.evados.ui.theme.EvaDosTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityUI()
        }
    }
}

enum class Action {
    CREATE,
    LIST,
}

@Composable
fun MainActivityUI() {
    val ctx = LocalContext.current
    val (items, setItems) = remember { mutableStateOf(emptyList<Item>()) }
    val (action, setAction) = remember { mutableStateOf(Action.LIST) }

    LaunchedEffect(items) {
        withContext(Dispatchers.IO) {
            val db = ListDB.getInstance(ctx)
            setItems( db.itemDao().getAll() )
        }
    }

    val onListItems = {
        setAction(Action.LIST)
        setItems(emptyList())
    }

    EvaDosTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when(action) {
                Action.LIST -> ItemsListUI(
                    items,
                    onCreateItemButtonClick = { setAction(Action.CREATE) },
                    onUpdateItemButtonClick = onListItems,
                    onDeleteItemButtonClick = onListItems
                )
                Action.CREATE -> ItemFormUI(
                    onItemAddButtonClick = onListItems
                )
            }
        }
    }
}
