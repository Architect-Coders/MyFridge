package com.pabji.myfridge.model.database.daos

import androidx.room.*
import com.pabji.myfridge.model.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductById(productId: Long): ProductEntity?

    @Query("SELECT * FROM products WHERE barcode = :barcode")
    fun getProductByBarcode(barcode: String): ProductEntity?

    @Query("SELECT * FROM products WHERE barcode IN(:barcodeList)")
    fun getProductsByBarcode(barcodeList: List<String>): List<ProductEntity>

    @Insert
    fun insertAll(list: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: ProductEntity)

    @Delete
    fun remove(product: ProductEntity)
}