package cz.esnhk.artemisMobile.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.entities.Semester
import cz.esnhk.artemisMobile.repository.SemesterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SemesterViewModel(private val semesterRepository: SemesterRepository) : ViewModel() {
    private val _semesterList = MutableStateFlow<ApiResult<List<Semester>>>(ApiResult.Loading)
    val semesterList: StateFlow<ApiResult<List<Semester>>> = _semesterList.asStateFlow()

    fun getSemesterList() {
        viewModelScope.launch {
            _semesterList.value = ApiResult.Loading
            try {
                val response = semesterRepository.getSemesterList()
                if (response.isSuccessful) {
                    val semesters = response.body()
                    if (semesters != null) {
                        _semesterList.value = ApiResult.Success(semesters)
                        Log.d("SemesterViewModel", "getSemesterList: $semesters")
                    } else {
                        _semesterList.value = ApiResult.Error("Data is null")
                        Log.e("SemesterViewModel", "Data is null")
                    }
                } else {
                    _semesterList.value = ApiResult.Error("Error fetching Semester list: ${response.message()}")
                    Log.e("SemesterViewModel", "Error fetching semester list: ${response.message()}")
                }
            } catch (e: Exception) {
                _semesterList.value = ApiResult.Error("Exception fetching Semester list: ${e.message}")
                Log.e("SemesterViewModel", "Exception fetching semester list: ${e.message}")
            }
        }
    }

    fun getCurrentSemester(): Semester? {
        val semesters = (_semesterList.value as? ApiResult.Success)?.data
        return semesters?.find { it.isCurrent }
    }

    fun getSemesterById(id: Int): Semester? {
        val semesters = (_semesterList.value as? ApiResult.Success)?.data
        return semesters?.find { it.id == id }
    }
}
