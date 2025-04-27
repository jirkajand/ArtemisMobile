package cz.esnhk.artemisMobile.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cz.esnhk.artemisMobile.entities.InternationalStudent
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.repository.StudentRepository
import cz.esnhk.artemisMobile.services.StudentService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InternationalStudentViewModel(private val studentRepository: StudentRepository) : ViewModel() {
    private val _internationalStudentList = MutableStateFlow<ApiResult<List<InternationalStudent>>>(ApiResult.Loading)
    val internationalStudentList: StateFlow<ApiResult<List<InternationalStudent>>> = _internationalStudentList.asStateFlow()

    private val _myStudentsList = MutableStateFlow<ApiResult<List<InternationalStudent>>>(ApiResult.Loading)
    val myStudentsList: StateFlow<ApiResult<List<InternationalStudent>>> = _myStudentsList.asStateFlow()

    fun getStudentList() {
        viewModelScope.launch {
            _internationalStudentList.value = ApiResult.Loading
            try {
                val response = studentRepository.getStudentList()
                if (response.isSuccessful) {
                    val students = response.body()
                    if (students != null) {
                        _internationalStudentList.value = ApiResult.Success(students)
                        Log.d("IntStudentViewModel", "getInternationalStudentList: $students")
                    } else {
                        _internationalStudentList.value = ApiResult.Error("Data is null")
                        Log.e("IntStudentViewModel", "Data is null")
                    }
                } else {
                    _internationalStudentList.value =
                        ApiResult.Error("Error fetching InternationalStudent list: ${response.message()}")
                    Log.e("IntStudentViewModel", "Error fetching student list: ${response.message()}")
                }
            } catch (e: Exception) {
                _internationalStudentList.value = ApiResult.Error("Exception fetching IntStudent list: ${e.message}")
                Log.e("IntStudentViewModel", "Exception fetching student list: ${e.message}")
            }
        }
    }

    fun getStudentsOfByBuddy(buddyId: Int) {
        viewModelScope.launch {
            _myStudentsList.value = ApiResult.Loading
            try {
                val students = studentRepository.getStudentByBuddyId(buddyId)
                _myStudentsList.value = ApiResult.Success(students)
                Log.d("IntStudentViewModel", "getMyStudents: ${students.size} students found")
            } catch (e: Exception) {
                _myStudentsList.value = ApiResult.Error("Exception fetching my students: ${e.message}")
                Log.e("IntStudentViewModel", "Exception fetching my students: ${e.message}")
            }
        }
    }

    // Factory class to provide dependencies
    class Factory(private val studentRepository: StudentRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InternationalStudentViewModel::class.java)) {
                return InternationalStudentViewModel(studentRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}