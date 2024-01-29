package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DishDao {
    @Query("SELECT * FROM dish")
    fun getAll(): List<Dish>

    @Insert
    fun insertAll(vararg dishes: Dish)

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