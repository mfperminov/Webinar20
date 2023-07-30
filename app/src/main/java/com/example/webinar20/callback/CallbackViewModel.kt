package com.example.webinar20.callback

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resumeWithException

class CallbackViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openbrewerydb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(Api::class.java)

    // Coroutine function using suspendCancellableCoroutine
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun fetchDataWithRetrofit(param: String): ApiResponse {
        return suspendCancellableCoroutine { continuation ->
            // Call your Retrofit API function
            val call = api.fetchBreweryData(param)

            // Cancel the Retrofit call if the coroutine is cancelled
            continuation.invokeOnCancellation {
                call.cancel()
            }

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()!!) { call.cancel() }
                    } else {
                        continuation.resumeWithException(Exception("Failed to fetch data"))
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    if (t is CancellationException) {
                        /*nothing*/
                    } else {
                        continuation.resumeWithException(t)
                    }
                }
            })
        }
    }


}