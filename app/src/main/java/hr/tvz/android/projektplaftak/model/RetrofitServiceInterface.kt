package hr.tvz.android.projektplaftak.model

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServiceInterface {

    @GET("api/Jerseys")

    fun collectJerseys(): Call<List<Jersey>>

}