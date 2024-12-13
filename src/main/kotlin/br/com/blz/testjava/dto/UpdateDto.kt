package br.com.blz.testjava.dto

import br.com.blz.testjava.entities.Product

data class UpdateDto(val sku: Long,val product: Product)
