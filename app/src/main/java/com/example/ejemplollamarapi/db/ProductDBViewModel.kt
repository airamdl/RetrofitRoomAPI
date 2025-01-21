package com.example.ejemplollamarapi.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductDBViewModel(
    private val dao: ProductDao
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getFavoriteProductList(): Flow<List<Product>> {
        return dao.getFavoriteProductList()
    }

    fun insertOrUpdateFavoriteProduct(product: Product) {
        viewModelScope.launch {
            _isLoading.value = true
            dao.upsertProduct(product)
            _isLoading.value = false
        }
    }

    fun deleteFavoriteProduct(productId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            dao.deleteFavoriteProduct(productId)
            _isLoading.value = false
        }
    }
}