package cz.esnhk.artemisMobile.repository

import android.content.Context

object RepositoryProvider {
    private var studentRepository: StudentRepository? = null
    private var eventRepository: EventRepository? = null

    fun provideStudentRepository(context: Context): StudentRepository {
        if (studentRepository == null) {
            val dataStoreManager = DataStoreManager(context)
            studentRepository = StudentRepository(dataStoreManager)
        }
        return studentRepository!!
    }

    fun provideEventRepository(context: Context): EventRepository {
        if (eventRepository == null) {
            val dataStoreManager = DataStoreManager(context)
            eventRepository = EventRepository(dataStoreManager)
        }
        return eventRepository!!
    }
}
