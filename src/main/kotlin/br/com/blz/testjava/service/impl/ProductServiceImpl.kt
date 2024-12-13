package br.com.blz.testjava.service.impl

import br.com.blz.testjava.entities.Product
import br.com.blz.testjava.exception.DuplicatedProductException
import br.com.blz.testjava.service.ProductService
import br.com.blz.testjava.utils.CustomLogger
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl : ProductService {

  private val products = mutableMapOf<Long, Product>()
  private val logger = CustomLogger.logger

  override fun createProduct(product: Product): Product {
    if (products.containsKey(product.sku)) {
      logger.error("Não foi possivel cadastrar o Produto com SKU ${product.sku},produto já existe.")
      throw DuplicatedProductException("Produto com SKU ${product.sku} já existe.")
    }
    products[product.sku] = product
    logger.info("Produto criado como sucesso ")
    return product
  }

  override fun updateProductBySku(sku: Long, product: Product): Product {
    if (!products.containsKey(sku)) {
      logger.error("Produto com SKU $sku não encontrado.")
      throw IllegalArgumentException("Produto com SKU $sku não encontrado.")
    }
    products[sku] = product
    logger.info("Produto com SKU $sku atualizado como sucesso.")
    return product
  }

  override fun getProductBySku(sku: Long): Product {
    logger.info("Procurando Produto com SKU $sku.")
    val product = products[sku] ?: throw IllegalArgumentException("Produto com SKU $sku não encontrado.")
    return product
  }

  override fun deleteProductBySku(sku: Long): Long {
    if (!products.containsKey(sku)) {
      logger.error("Nao foi possivel deletar o produto com SKU $sku, produto não encontrado.")
      throw IllegalArgumentException("Produto com SKU $sku não encontrado.")
    }
    products.remove(sku)
    logger.info("Produto com SKU $sku removido com sucesso.")
    return sku
  }


}
