package com.example.evados

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch

@Composable
fun ItemRowUI(item: Item, onDeleteItemButtonClick: () -> Unit, onUpdateItemButtonClick: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

            }
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        when(item.bought) {
            true -> {
                IconButton(onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        ListDB.getInstance(context).itemDao().update(item.copy(bought = !item.bought))
                        onUpdateItemButtonClick()
                    }
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_shopping_cart_24),
                        contentDescription = stringResource(R.string.image_description_item_image),
                    )
                }
            }
            false -> {
                IconButton(onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        ListDB.getInstance(context).itemDao().update(item.copy(bought = !item.bought))
                        onUpdateItemButtonClick()
                    }
                }) {
                    Image(
                        painter = painterResource(R.drawable.baseline_check_box_outline_blank_24),
                        contentDescription = stringResource(R.string.image_description_item_image),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = item.name,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(20.dp))
        IconButton(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    ListDB.getInstance(context).itemDao().delete(item)
                    onDeleteItemButtonClick()
                }
            },
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_delete_forever_24),
                contentDescription = stringResource(R.string.image_description_delete_item),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemRowUIExample() {
    ItemRowUI(Item(name = "Item 1"), onDeleteItemButtonClick = {}, onUpdateItemButtonClick = {})
}