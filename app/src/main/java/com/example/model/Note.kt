package com.example.model

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val subject: String,
    val examType: String,
    val price: Double,
    val rating: Double,
    val reviews: Int,
    val creatorName: String,
    val coverImageUrl: String,
    val isTrending: Boolean = false,
    val isRecommended: Boolean = false
)

val sampleNotes = listOf(
    Note(
        id = "n1",
        title = "Complete JEE Physics Mechanics",
        description = "Handwritten notes covering all concepts of Mechanics with solved previous year questions. Perfect for quick revision before the exam.",
        subject = "Physics",
        examType = "JEE",
        price = 49.0,
        rating = 4.8,
        reviews = 120,
        creatorName = "Topper Notes",
        coverImageUrl = "https://images.unsplash.com/photo-1517842645767-c639042777db?auto=format&fit=crop&w=400&q=80",
        isTrending = true
    ),
    Note(
        id = "n2",
        title = "NEET Biology Quick Revision",
        description = "NCERT based point-wise notes for NEET Biology. Covers Botany and Zoology with mnemonics.",
        subject = "Biology",
        examType = "NEET",
        price = 99.0,
        rating = 4.9,
        reviews = 345,
        creatorName = "Medico Student",
        coverImageUrl = "https://images.unsplash.com/photo-1532094349884-543bc11b234d?auto=format&fit=crop&w=400&q=80",
        isTrending = true,
        isRecommended = true
    ),
    Note(
        id = "n3",
        title = "Class 12 Math Board Prep",
        description = "Calculus and Algebra detailed notes. Includes step-by-step solutions for difficult textbook problems.",
        subject = "Mathematics",
        examType = "CBSE Class 12",
        price = 29.0,
        rating = 4.5,
        reviews = 89,
        creatorName = "Math Wizard",
        coverImageUrl = "https://images.unsplash.com/photo-1635070041078-e363dbe005cb?auto=format&fit=crop&w=400&q=80",
        isRecommended = true
    )
)
