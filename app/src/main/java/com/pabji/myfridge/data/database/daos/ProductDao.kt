package com.pabji.myfridge.data.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pabji.myfridge.data.database.entities.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    fun getAll(): LiveData<List<ProductEntity>>

    @Insert
    fun insertAll(list: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: ProductEntity)

    @Delete
    fun remove(product: ProductEntity)
}