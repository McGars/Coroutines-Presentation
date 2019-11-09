package com.mcgars.coroutines_example.feature

import com.mcgars.coroutines_example.core.PresenterCoroutine
import com.mcgars.coroutines_example.core.Progress
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class ExampleCoroutine7(private val progress: Progress) : PresenterCoroutine(progress) {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request()
                    println("ExampleCoroutine7: retrofit before request")
                    Thread.sleep(2000)
                    println("ExampleCoroutine7: retrofit after request and sleeping")
                    val response = it.proceed(request)
                    println("ExampleCoroutine7: retrofit response: $response")
                    response
                }
                .build()
        )
        .build()

    override fun name(): String = "Coroutine, cancel request while load data from server"

    override fun run() = launch {
        progress.show()

        runDestroyDelay()

        val data = retrofit
            .create(GoogleService::class.java)
            .getBody()

        progress.showToast(data.string())
        progress.hide()
    }

    /**
     * destroy presenter request after 2 seconds
     */
    private fun runDestroyDelay() = launch {
        delay(2000)
        println("ExampleCoroutine7: cancel request")
        progress.hide()
        cancelChildJob()
    }

    /**
     * User repos
     */
    interface GoogleService {
        @GET("/")
        suspend fun getBody(): ResponseBody
    }

}