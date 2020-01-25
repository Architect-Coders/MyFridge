package com.pabji.myfridge.model.database.daos

import androidx.room.*
import com.pabji.myfridge.model.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: Long): ProductEntity?

    @Query("SELECT * FROM products WHERE barcode = :barcode")
    suspend fun getProductByBarcode(barcode: String): ProductEntity?

    @Query("SELECT * FROM products WHERE barcode IN(:barcodeList)")
    suspend fun getProductsByBarcode(barcodeList: List<String>): List<ProductEntity>

    @Query("SELECT * FROM products WHERE name LIKE :searchTerm")
    suspend fun getProductsByTerm(searchTerm: String): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    @Delete
    suspend fun remove(product: ProductEntity)
}