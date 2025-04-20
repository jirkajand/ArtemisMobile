package cz.esnhk.artemisMobile.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.esnhk.artemisMobile.InternationalStudent
import cz.esnhk.artemisMobile.api.ApiResult
import cz.esnhk.artemisMobile.api.ArtemisApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentViewModel(private val cryptoApi: ArtemisApi) : ViewModel() {
    private val _cryptoList = MutableStateFlow<ApiResult<List<InternationalStudent>>>(ApiResult.Loading)
    val cryptoList: StateFlow<ApiResult<List<InternationalStudent>>> = _cryptoList.asStateFlow()

    private val _cryptoDetail = MutableStateFlow<ApiResult<InternationalStudent>>(ApiResult.Loading)
    val cryptoDetail: StateFlow<ApiResult<InternationalStudent>> = _cryptoDetail.asStateFlow()


    fun getStudentList() {
        viewModelScope.launch {
            _cryptoList.value = ApiResult.Loading
            try {
                val response = cryptoApi.getCryptoList()
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    if (data != null) {
                        _cryptoList.value = ApiResult.Success(data)
                        Log.d("CryptoViewModel", "getCryptoList: ${response.body()?.data}")
                    } else {
                        _cryptoList.value = ApiResult.Error("Data is null")
                        Log.e("CryptoViewModel", "Data is null")
                    }
                } else {
                    _cryptoList.value =
                        ApiResult.Error("Error fetching crypto list: ${response.message()}")
                    Log.e("CryptoViewModel", "Error fetching crypto list: ${response.message()}")
                }
            } catch (e: Exception) {
                _cryptoList.value = ApiResult.Error("Exception fetching crypto list: ${e.message}")
                Log.e("CryptoViewModel", "Exception fetching crypto list: ${e.message}")
            }
        }
    }
}