package com.example.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "bitmaps")
data class Bitmapdata(
    @PrimaryKey() val id : Int,
    @ColumnInfo(name = "imageBitmap", typeAffinity = ColumnInfo.BLOB)
val imageBitmap: ByteArray?,
)
@Dao
interface BitmapDao {
    @Query("SELECT * FROM bitmaps")
    fun getAll(): List<Bitmapdata>

    @Query("SELECT * FROM bitmaps WHERE id = :id")
    fun getBitmapById(id: Int): Bitmapdata

    @Insert
    fun insert(bitmapdata: Bitmapdata)

    @Delete
    fun delete(bitmapdata: Bitmapdata)
}

@Database(entities = [Bitmapdata::class], version = 1)
abstract class AppBitBase : RoomDatabase() {
    abstract fun bitmapDao(): BitmapDao
}