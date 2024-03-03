package com.example.data

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
import androidx.room.Transaction
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "bitmaps")
data class Bitmapdata(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "imageBitmap", typeAffinity = ColumnInfo.BLOB)
    val imageBitmap: ByteArray?,
    @ColumnInfo(name = "isLiked")
    var isLiked: Boolean = false
)

@Dao
interface BitmapDao {
    @Query("SELECT * FROM bitmaps")
    fun getAll(): List<Bitmapdata>

    @Query("SELECT * FROM bitmaps WHERE id = :id")
    fun getBitmapById(id: Int): Bitmapdata

    @Insert
    fun insert(bitmapdata: Bitmapdata)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bitmapData: Bitmapdata)

    @Delete
    fun delete(bitmapdata: Bitmapdata)

    @Query("UPDATE bitmaps SET isLiked = :isLiked WHERE id = :id")
    fun updateIsLiked(id: Int, isLiked: Boolean)

    @Query("UPDATE bitmaps SET imageBitmap = :imageBitmap WHERE id = :id")
    fun updateImageBitmap(id: Int, imageBitmap: ByteArray?)

    @Transaction
    fun updateBitmap(bitmapdata: Bitmapdata) {
        updateIsLiked(bitmapdata.id, bitmapdata.isLiked)
        updateImageBitmap(bitmapdata.id, bitmapdata.imageBitmap)
    }
}

@Database(entities = [Bitmapdata::class], version = 1)
@TypeConverters(ConvertersBit::class)
abstract class AppBitBase : RoomDatabase() {
    abstract fun bitmapDao(): BitmapDao
}
class ConvertersBit {
    @TypeConverter
    fun fromBoolean(value: Boolean): Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun toBoolean(value: Int): Boolean {
        return value != 0
    }
}
