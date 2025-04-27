package cz.esnhk.artemisMobile.repository

import android.content.Context

object RepositoryProvider {
    private var studentRepository: StudentRepository? = null

    fun provideStudentRepository(context: Context): StudentRepository {
        if (studentRepository == null) {
            val dataStoreManager = DataStoreManager(context)
            studentRepository = StudentRepository(dataStoreManager)
        }
        return studentRepository!!
    }
}