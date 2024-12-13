package br.com.blz.testjava.service

import br.com.blz.testjava.entities.Product

interface ProductService {
    fun createProduct(product: Product): Product
    fun updateProductBySku(sku: Long, product: Product): Product
    fun getProductBySku(sku: Long): Product
    fun deleteProductBySku(sku: Long): Long
}
