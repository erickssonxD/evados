package com.example.evados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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

            LaunchedEffect(Unit) {
                withContext(dbDispatcher) {
                    items = itemDao.getAll()
                }
            }

            EvaDosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityContent(items)
                }
            }
        }
    }
}

@Composable
fun MainActivityContent(items: List<Item>, modifier: Modifier = Modifier) {
    if (items.isEmpty()) {
        Text("No items")
    } else {
        LazyColumn(modifier = modifier) {
            items(items.size) { index ->
                Text(items[index].name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EvaDosTheme {
        MainActivityContent(emptyList())
    }
}