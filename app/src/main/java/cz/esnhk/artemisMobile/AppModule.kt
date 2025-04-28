package cz.esnhk.artemisMobile

import cz.esnhk.artemisMobile.repository.DataStoreManager
import cz.esnhk.artemisMobile.repository.EventRepository
import cz.esnhk.artemisMobile.repository.SemesterRepository
import cz.esnhk.artemisMobile.repository.StudentRepository
import cz.esnhk.artemisMobile.viewmodels.AuthViewModel
import cz.esnhk.artemisMobile.viewmodels.EventViewModel
import cz.esnhk.artemisMobile.viewmodels.InternationalStudentViewModel
import cz.esnhk.artemisMobile.viewmodels.SemesterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get(), get()) }

    viewModel { InternationalStudentViewModel(get()) }

    viewModel { EventViewModel(get()) }

    viewModel { SemesterViewModel(get()) }
}

val repositoryModule = module {
    single { DataStoreManager(get()) }

    single { StudentRepository(get()) }

    single { EventRepository(get()) }

    single { SemesterRepository(get()) }

}