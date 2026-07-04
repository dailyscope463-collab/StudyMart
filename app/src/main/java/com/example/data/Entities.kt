package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val name: String,
    val isSeller: Boolean
)

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val subject: String,
    val examType: String,
    val price: Double,
    val rating: Double = 0.0,
    val reviews: Int = 0,
    val creatorName: String,
    val creatorId: String,
    val coverImageUrl: String,
    val isTrending: Boolean = false,
    val isRecommended: Boolean = false,
    val uploadDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val noteId: String,
    val buyerId: String,
    val sellerId: String,
    val price: Double,
    val utrNumber: String,
    val status: String, // "PENDING", "APPROVED", "REJECTED"
    val timestamp: Long = System.currentTimeMillis()
)
