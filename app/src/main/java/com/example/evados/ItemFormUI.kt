package com.example.evados

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.evados.data.Item
import com.example.evados.data.ListDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemFormUI(onItemAddButtonClick: () -> Unit) {
    val (name, setName) = remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_add_shopping_cart_24),
            contentDescription = "Add item",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = name,
            onValueChange = { setName(it) },
            label = { Text(stringResource(R.string.edit_text_name)) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                val dao = ListDB.getInstance(context).itemDao()
                val item = Item(name = name)
                dao.insert(item)
                onItemAddButtonClick()
            }
        }) {
            Text(stringResource(R.string.button_add_text))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemFormUIExample() {
    ItemFormUI(onItemAddButtonClick = {})
}