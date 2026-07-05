package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.NoteEntity
import com.example.data.OrderEntity
import com.example.data.StudyMartRepository
import com.example.data.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class StudyMartViewModel(private val repository: StudyMartRepository) : ViewModel() {
    
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    val purchasedNotes: StateFlow<List<NoteEntity>> = _currentUser.flatMapLatest { user ->
        if (user != null) {
            repository.getPurchasedNotesForBuyer(user.id)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotes: StateFlow<List<NoteEntity>> = repository.getAllNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val trendingNotes: StateFlow<List<NoteEntity>> = repository.getTrendingNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        
    val recommendedNotes: StateFlow<List<NoteEntity>> = repository.getRecommendedNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Seed dummy data for demo if empty
        viewModelScope.launch {
            val user = UserEntity(
                id = "user1",
                email = "demo@studymart.com",
                name = "Demo Buyer",
                isSeller = false
            )
            repository.insertUser(user)
            
            val seller = UserEntity(
                id = "seller1",
                email = "seller@studymart.com",
                name = "Top Creator",
                isSeller = true
            )
            repository.insertUser(seller)
            
            // Wait for notes to check if empty
            // (In a real app we'd check properly, but this just ensures data is there)
            repository.insertNote(
                NoteEntity(
                    id = "n1",
                    title = "Complete JEE Physics Mechanics",
                    description = "Handwritten notes covering all concepts of Mechanics with solved previous year questions.",
                    subject = "Physics",
                    examType = "JEE",
                    price = 49.0,
                    rating = 4.8,
                    reviews = 120,
                    creatorName = "Top Creator",
                    creatorId = "seller1",
                    coverImageUrl = "https://images.unsplash.com/photo-1517842645767-c639042777db?auto=format&fit=crop&w=400&q=80",
                    isTrending = true
                )
            )
            repository.insertNote(
                NoteEntity(
                    id = "n2",
                    title = "NEET Biology Quick Revision",
                    description = "NCERT based point-wise notes for NEET Biology.",
                    subject = "Biology",
                    examType = "NEET",
                    price = 99.0,
                    rating = 4.9,
                    reviews = 345,
                    creatorName = "Top Creator",
                    creatorId = "seller1",
                    coverImageUrl = "https://images.unsplash.com/photo-1532094349884-543bc11b234d?auto=format&fit=crop&w=400&q=80",
                    isTrending = true,
                    isRecommended = true
                )
            )
            
            login("demo@studymart.com")
        }
    }

    fun login(email: String) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            _currentUser.value = user
        }
    }
    
    fun getNote(noteId: String): StateFlow<NoteEntity?> {
        return repository.getNoteById(noteId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }

    fun submitPayment(noteId: String, sellerId: String, price: Double, utrNumber: String) {
        val user = _currentUser.value ?: return
        val order = OrderEntity(
            orderId = "SM-${System.currentTimeMillis()}",
            noteId = noteId,
            buyerId = user.id,
            sellerId = sellerId,
            price = price,
            utrNumber = utrNumber,
            status = "PENDING"
        )
        viewModelScope.launch {
            repository.insertOrder(order)
        }
    }
}
