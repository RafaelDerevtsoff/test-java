package br.com.blz.testjava.service


import br.com.blz.testjava.entities.Inventory
import br.com.blz.testjava.entities.Product
import br.com.blz.testjava.exception.DuplicatedProductException
import br.com.blz.testjava.service.impl.ProductServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductServiceTest {

  private lateinit var productService: ProductService

  @BeforeEach
  fun setUp() {
    productService = ProductServiceImpl() // Criando uma nova instância para cada teste
  }

  @Test
  fun `should create a product successfully`() {
    val product = Product(sku = 123L, name = "Product 1", inventory = Inventory(warehouses = emptyList()))
    val createdProduct = productService.createProduct(product)
    assertEquals(product, createdProduct)
  }

  @Test
  fun `should throw DuplicatedProductException when creating a product with an existing SKU`() {
    val product = Product(sku = 123L, name = "Product 1", inventory = Inventory(warehouses = emptyList()))
    productService.createProduct(product) // Primeiro cria o produto
    val exception = assertThrows(DuplicatedProductException::class.java) {
      productService.createProduct(product) // Tenta criar o mesmo produto novamente
    }
    assertEquals("Produto com SKU 123 já existe.", exception.message)
  }

  @Test
  fun `should update a product successfully`() {
    val product = Product(sku = 123L, name = "Product 1", inventory = Inventory(warehouses = emptyList()))
    productService.createProduct(product)
    val updatedProduct = Product(sku = 123L, name = "Updated Product", inventory = Inventory(warehouses = emptyList()))
    val result = productService.updateProductBySku(123L, updatedProduct)
    assertEquals(updatedProduct, result)
  }

  @Test
  fun `should throw IllegalArgumentException when updating a non-existing product`() {
    val product = Product(sku = 123L, name = "Product 1", inventory = Inventory(warehouses = emptyList()))
    val exception = assertThrows(IllegalArgumentException::class.java) {
      productService.updateProductBySku(123L, product) // Tenta atualizar um produto que não existe
    }
    assertEquals("Produto com SKU 123 não encontrado.", exception.message)
  }

  @Test
  fun `should get a product by SKU successfully`() {
    val product = Product(sku = 123L, name = "Product 1", inventory = Inventory(warehouses = emptyList()))
    productService.createProduct(product) // Cria o produto
    val fetchedProduct = productService.getProductBySku(123L)
    assertEquals(product, fetchedProduct)
  }

  @Test
  fun `should throw IllegalArgumentException when getting a product by non-existing SKU`() {
    val exception = assertThrows(IllegalArgumentException::class.java) {
      productService.getProductBySku(999L) // Tenta buscar um produto que não existe
    }
    assertEquals("Produto com SKU 999 não encontrado.", exception.message)
  }

  @Test
  fun `should delete a product by SKU successfully`() {
    val product = Product(sku = 123L, name = "Product 1", inventory = Inventory(warehouses = emptyList()))
    productService.createProduct(product) // Cria o produto
    val deletedSku = productService.deleteProductBySku(123L)
    assertEquals(123L, deletedSku)
  }

  @Test
  fun `should throw IllegalArgumentException when deleting a non-existing product`() {
    val exception = assertThrows(IllegalArgumentException::class.java) {
      productService.deleteProductBySku(999L) // Tenta deletar um produto que não existe
    }
    assertEquals("Produto com SKU 999 não encontrado.", exception.message)
  }
}
