package com.example.data
// должжно ли это быть в домейне?
import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface RecipeApiService {
    @Headers(
        "Accept-Language: en",
        "X-RapidAPI-Key: 96c1521583msh4af0535795a21b9p116135jsn1ac75cb7c088",
        "X-RapidAPI-Host: edamam-recipe-search.p.rapidapi.com"
    )
    @GET("api/recipes/v2?type=public&co2EmissionsClass=A%2B&field%5B0%5D=uri&beta=true&random=true&imageSize%5B0%5D=LARGE")
    fun getJson(): Call<ResponseBody>
}


class RecipeApi {
    private val service: RecipeApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://edamam-recipe-search.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RecipeApiService::class.java)
    }

    suspend fun getJson(): String {
        return suspendCoroutine { continuation ->
            val call = service.getJson()
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        continuation.resume(response.body()?.string() ?: "")
                        Log.d("MyTag", "Сообщение 2")
                    } else {
                        Log.d("MyTag", "HTTP error code: ${response.code()}")
                        continuation.resume("help")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}
