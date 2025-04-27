package cz.esnhk.artemisMobile

import cz.esnhk.artemisMobile.repository.DataStoreManager
import cz.esnhk.artemisMobile.repository.StudentRepository
import cz.esnhk.artemisMobile.viewmodels.AuthViewModel
import cz.esnhk.artemisMobile.viewmodels.InternationalStudentViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(), get()) }

    viewModel { InternationalStudentViewModel(get()) }
}

val repositoryModule = module {
    single { DataStoreManager(get()) }

    single { StudentRepository(get()) }
}