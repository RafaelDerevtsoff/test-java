package br.com.blz.testjava.entities

data class Inventory(
    val warehouses: List<Warehouse>
) {
    val quantity: Int
        get() = warehouses.sumOf { it.quantity }
    val isMarketable: Boolean
        get() = quantity > 0
}
