package com.example.data

import kotlinx.coroutines.flow.Flow

class StudyMartRepository(private val dao: StudyMartDao) {
    
    // User
    suspend fun insertUser(user: UserEntity) = dao.insertUser(user)
    suspend fun getUserById(userId: String) = dao.getUserById(userId)
    suspend fun getUserByEmail(email: String) = dao.getUserByEmail(email)
    
    // Notes
    suspend fun insertNote(note: NoteEntity) = dao.insertNote(note)
    suspend fun insertNotes(notes: List<NoteEntity>) = dao.insertNotes(notes)
    
    fun getAllNotes(): Flow<List<NoteEntity>> = dao.getAllNotes()
    fun getTrendingNotes(): Flow<List<NoteEntity>> = dao.getTrendingNotes()
    fun getRecommendedNotes(): Flow<List<NoteEntity>> = dao.getRecommendedNotes()
    fun getNoteById(noteId: String): Flow<NoteEntity?> = dao.getNoteById(noteId)
    fun getNotesByCreator(creatorId: String): Flow<List<NoteEntity>> = dao.getNotesByCreator(creatorId)
    
    // Orders
    suspend fun insertOrder(order: OrderEntity) = dao.insertOrder(order)
    suspend fun updateOrderStatus(orderId: String, status: String) = dao.updateOrderStatus(orderId, status)
    
    fun getOrdersForBuyer(buyerId: String): Flow<List<OrderEntity>> = dao.getOrdersForBuyer(buyerId)
    fun getOrdersForSeller(sellerId: String): Flow<List<OrderEntity>> = dao.getOrdersForSeller(sellerId)
    fun getPurchasedNotesForBuyer(buyerId: String): Flow<List<NoteEntity>> = dao.getPurchasedNotesForBuyer(buyerId)
}
