package com.example.data.database.bit

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "bitmaps")
data class Bitmapdata(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "imageBitmap", typeAffinity = ColumnInfo.BLOB)
    val imageBitmap: ByteArray?,
    @ColumnInfo(name = "isLiked")
    var isLiked: Boolean = false,
    @ColumnInfo(name = "label")
    var label: String? = null
)

@Dao
interface BitmapDao {
    @Query("SELECT * FROM bitmaps")
    suspend fun getAll(): List<Bitmapdata>

    @Query("SELECT * FROM bitmaps WHERE id = :id")
    suspend fun getBitmapById(id: Int): Bitmapdata

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bitmapData: Bitmapdata)

    @Delete
    suspend fun delete(bitmapdata: Bitmapdata)

    @Query("UPDATE bitmaps SET isLiked = :isLiked WHERE id = :id")
    suspend fun updateIsLiked(id: Int, isLiked: Boolean)

    @Query("UPDATE bitmaps SET imageBitmap = :imageBitmap WHERE id = :id")
    suspend fun updateImageBitmap(id: Int, imageBitmap: ByteArray?)
}

@Database(entities = [Bitmapdata::class], version = 1)
abstract class AppBitBase : RoomDatabase() {
    abstract fun bitmapDao(): BitmapDao
}
