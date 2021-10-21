package com.example.get_name_location
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
interface APIinterface {

        @GET("/test/")
        fun getUser(): Call<Array<Users>>

    @Headers("Content-Type: application/json")
        @POST("/test/")
        fun addUser(@Body userData: Users): Call<Users>


    }
class Users {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("location")
    var location: String? = null

    constructor(name:String?,location:String?){
        this.name=name
        this.location=location
    }
}