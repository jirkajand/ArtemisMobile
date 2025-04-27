package cz.esnhk.artemisMobile.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cz.esnhk.artemisMobile.repository.RepositoryProvider
import cz.esnhk.artemisMobile.repository.StudentRepository

object ViewModelProvider {

    fun provideInternationalStudentViewModel(
        context: Context,
        viewModelStoreOwner: ViewModelStoreOwner
    ): InternationalStudentViewModel {
        val studentRepository = RepositoryProvider.provideStudentRepository(context)
        val factory = InternationalStudentViewModel.Factory(studentRepository)
        return ViewModelProvider(viewModelStoreOwner, factory)[InternationalStudentViewModel::class.java]
    }

    // Add more ViewModel providers as needed
}