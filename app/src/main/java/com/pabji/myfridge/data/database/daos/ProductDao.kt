package com.pabji.myfridge.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.pabji.myfridge.data.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAll(): LiveData<List<ProductEntity>>

    @Insert
    fun insertAll(list: List<ProductEntity>)

    @Insert
    fun insert(product: ProductEntity)

    @Delete
    fun remove(product: ProductEntity)
    /*@Query("DELETE FROM products WHERE id = :productId")
    fun removeById(productId: Long)*/
}