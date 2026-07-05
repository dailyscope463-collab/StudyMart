package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyMartDao {
    // Users
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    // Notes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<NoteEntity>)

    @Query("SELECT * FROM notes ORDER BY uploadDate DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isTrending = 1")
    fun getTrendingNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isRecommended = 1")
    fun getRecommendedNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: String): Flow<NoteEntity?>
    
    @Query("SELECT * FROM notes WHERE creatorId = :creatorId")
    fun getNotesByCreator(creatorId: String): Flow<List<NoteEntity>>

    // Orders
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM orders WHERE buyerId = :buyerId ORDER BY timestamp DESC")
    fun getOrdersForBuyer(buyerId: String): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE sellerId = :sellerId ORDER BY timestamp DESC")
    fun getOrdersForSeller(sellerId: String): Flow<List<OrderEntity>>

    @Query("SELECT notes.* FROM notes INNER JOIN orders ON notes.id = orders.noteId WHERE orders.buyerId = :buyerId")
    fun getPurchasedNotesForBuyer(buyerId: String): Flow<List<NoteEntity>>

    @Query("UPDATE orders SET status = :newStatus WHERE orderId = :orderId")
    suspend fun updateOrderStatus(orderId: String, newStatus: String)
}
