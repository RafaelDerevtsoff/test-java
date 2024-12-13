package br.com.blz.testjava.controller

import br.com.blz.testjava.dto.UpdateDto
import br.com.blz.testjava.entities.Product
import br.com.blz.testjava.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController {
    @Autowired
    private lateinit var productService: ProductService

    @GetMapping("/{sku}")
    fun getProductBySku(@PathVariable sku: Long): ResponseEntity<Product> {
        return ResponseEntity.ok(productService.getProductBySku(sku = sku))
    }

    @PostMapping("/create")
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(product = product))
    }

    @PutMapping("/update")
    fun updateProduct(@RequestBody updateDto: UpdateDto): ResponseEntity<Product> {
        return ResponseEntity.ok(productService.updateProductBySku(sku = updateDto.sku, product = updateDto.product))
    }

    @DeleteMapping("/delete/{sku}")
    fun deleteProduct(@PathVariable sku: Long): ResponseEntity<Long> {
        return ResponseEntity.status(204).body(productService.deleteProductBySku(sku = sku))
    }
}
