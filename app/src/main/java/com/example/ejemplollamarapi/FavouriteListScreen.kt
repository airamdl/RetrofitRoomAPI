package com.example.ejemplollamarapi

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ejemplollamarapi.db.ProductDBViewModel
import com.example.ejemplollamarapi.db.productResponseToProduct
import com.example.ejemplollamarapi.network.product.model.ProductListResponse
import com.example.ejemplollamarapi.network.product.model.ProductResponse
import com.example.ejemplollamarapi.network.product.model.productToProductResponse

@Composable
fun FavouriteListScreen(
    databaseViewModel: ProductDBViewModel,
    context: Context
    ) {
        val favoriteList = databaseViewModel.getFavouriteProductList().collectAsState(initial = emptyList())
        val convertedList = favoriteList.value.map { productToProductResponse(it) }
        val isLoading: Boolean by databaseViewModel.isLoading.observeAsState(initial = false)
        if (isLoading) {
            databaseViewModel.getFavouriteProductList()
            LoadingScreen()
        } else {
            FavouriteListView(databaseViewModel.productList.value!!, context)
        }
}

@Composable
fun FavouriteListView(
    favoriteList: List<ProductResponse>,
    context: Context
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 25.dp, bottom = 10.dp),
                text = "Lista de productos Favoritos",
                fontSize = TextUnit(8f, TextUnitType.Em),
                fontWeight = FontWeight.Bold
            )
        }
        if (favoriteList.isEmpty()) {
            item{
                Text(
                    modifier = Modifier.padding(top = 25.dp),
                    text = "Lista vacía",
                    fontSize = TextUnit(8f, TextUnitType.Em)
                )
            }
        } else {
            items(favoriteList) { product ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier.width(270.dp).padding(bottom = 25.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.title,
                            fontSize = TextUnit(5.5f, TextUnitType.Em),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        FilledIconButton(
                            modifier = Modifier.size(25.dp).padding(start = 10.dp),
                            onClick = {
                                Toast.makeText(context, "Producto añadido", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Añadir elemento"
                            )
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 10.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier.fillMaxSize().padding(start = 5.dp, bottom = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(160.dp),
                            model = product.thumbnail,
                            contentDescription = "Imagen del ${product.title}"
                        )
                    }
                }
            }
        }
    }
}