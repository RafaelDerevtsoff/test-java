package br.com.blz.testjava.controller

import br.com.blz.testjava.dto.UpdateDto
import br.com.blz.testjava.entities.Inventory
import br.com.blz.testjava.entities.Product
import br.com.blz.testjava.entities.Warehouse
import br.com.blz.testjava.enums.TypeWarehouse
import br.com.blz.testjava.service.ProductService
import com.fasterxml.jackson.databind.ObjectMapper

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var product: Product

    final val warehouse1 = Warehouse(locality = "SP", quantity = 12, type = TypeWarehouse.ECOMMERCE)
    final val warehouse2 = Warehouse(locality = "MOEMA", quantity = 3, type = TypeWarehouse.PHYSICAL_STORE)

    // Criando o inventário
    val inventory = Inventory(warehouses = listOf(warehouse1, warehouse2))
    @BeforeEach
    fun setUp() {
        product = Product(
            sku = 43264,
            name = "L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g",
            inventory = inventory // Simplificação para o exemplo
        )
    }

    @Test
    fun `should return product by SKU`() {
        `when`(productService.getProductBySku(43264)).thenReturn(product)

        mockMvc.get("/products/43264")
            .andExpect {
                status().isOk
                jsonPath("$.sku").value(43264)
                jsonPath("$.name").value("L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g")
            }
    }

    @Test
    fun `should create a new product`() {
        `when`(productService.createProduct(product)).thenReturn(product)

        mockMvc.post("/products/create") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(product)
        }.andExpect {
            status().isCreated
            jsonPath("$.sku").value(43264)
            jsonPath("$.name").value("L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g")
        }
    }

    @Test
    fun `should update a product`() {
        val updateDto = UpdateDto(sku = 43264, product = product)
        `when`(productService.updateProductBySku(43264, product)).thenReturn(product)

        mockMvc.put("/products/update") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateDto)
        }.andExpect {
            status().isOk
            jsonPath("$.sku").value(43264)
            jsonPath("$.name").value("L'Oréal Professionnel Expert Absolut Repair Cortex Lipidium - Máscara de Reconstrução 500g")
        }
    }

    @Test
    fun `should delete a product`() {
        `when`(productService.deleteProductBySku(43264)).thenReturn(43264)

        mockMvc.delete("/products/delete/43264")
            .andExpect {
                status().isAccepted
                content().string("43264")
            }
    }

    @Test
    fun `should return an error when trying to delete a non-existent product`() {
        `when`(productService.deleteProductBySku(99999)).thenThrow(IllegalArgumentException("Produto não encontrado"))

        mockMvc.delete("/products/delete/99999")
            .andExpect {
                status().isNotFound
            }
    }
}
