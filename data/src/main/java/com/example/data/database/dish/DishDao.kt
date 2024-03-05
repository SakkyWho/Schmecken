package com.example.data.database.dish

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.database.dish.BasicInfo
import com.example.data.database.dish.Dish
import com.example.data.database.dish.Nutrition
import com.example.data.database.dish.OtherInfo

@Dao
interface DishDao {
    @Query(value = "SELECT * FROM dishes")
    fun getAll(): List<Dish>

    @Insert
    fun insertAll(vararg dishes: Dish)
    @Insert
    fun insertAllarr(dishes: List<Dish>)

    @Query("SELECT basicinfo FROM dishes WHERE id = :dishId")
    fun getBasicInfo(dishId: Int): BasicInfo
    @Query("SELECT otherinfo FROM dishes WHERE id = :dishId")
    fun getOtherInfo(dishId: Int): OtherInfo
    @Query("SELECT nutrition FROM dishes WHERE id = :dishId")
    fun getNutrition(dishId: Int): Nutrition



    /* @Query("SELECT * FROM dish WHERE Uri IN (:tablename)")
     fun loadAllByIds(userIds: IntArray): List<User>

     @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
             "last_name LIKE :last LIMIT 1")
     fun findByName(first: String, last: String): User

     @Insert
     fun insertAll(vararg users: User)

     @Delete
     fun delete(user: User)@

     @Query(       очень хорошо выглядит
     "SELECT * FROM user" +
     "JOIN book ON user.id = book.user_id"
 )
 fun loadUserAndBookNames(): Map<User, List<Book>>
 */
}