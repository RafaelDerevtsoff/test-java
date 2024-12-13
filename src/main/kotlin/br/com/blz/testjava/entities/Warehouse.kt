package br.com.blz.testjava.entities

import br.com.blz.testjava.enums.TypeWarehouse

data class Warehouse(
    val locality: String,
    val quantity: Int,
    val type: TypeWarehouse
)
