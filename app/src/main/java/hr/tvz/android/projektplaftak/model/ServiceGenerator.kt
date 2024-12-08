package hr.tvz.android.projektplaftak.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {

    fun <S> createService(serviceClass: Class<S>, apiUrl: String): S {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
        retrofit.baseUrl(apiUrl)
        retrofit.addConverterFactory(GsonConverterFactory.create())
        retrofit.client(httpClient.build())

        return retrofit.build().create(serviceClass)
    }

}