package com.example.data

import android.content.Context

interface AppContainer {
    val studyMartRepository: StudyMartRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val studyMartRepository: StudyMartRepository by lazy {
        StudyMartRepository(AppDatabase.getDatabase(context).studyMartDao())
    }
}
