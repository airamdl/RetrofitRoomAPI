package com.example.ejemplollamarapi.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemplollamarapi.network.product.model.ProductResponse

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductDBViewModel(
    private val dao: ProductDao
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _productList = MutableLiveData<List<ProductResponse>>(emptyList())
    val productList: LiveData<List<ProductResponse>> = _productList

    fun getFavouriteProductList(): Flow<List<Product>> {
        return dao.getFavouriteProductList()
    }

    fun insertOrUpdateFavoriteProduct(product: Product) {
        viewModelScope.launch {
            _isLoading.value = true
            dao.upsertProduct(product)
            _isLoading.value = false
        }
    }

    fun deleteFavouriteProduct(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            dao.deleteFavouriteProduct(productId)
            _isLoading.value = false
        }
    }
}