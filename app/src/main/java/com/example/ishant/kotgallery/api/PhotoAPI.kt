package com.example.ishant.kotgallery.api

import com.example.ishant.kotgallery.models.PhotoList

import retrofit2.Call
import retrofit2.http.GET

interface PhotoAPI {
    @GET("?key=5740195-36072a9a9eb4cf667d2cce7dd&q=nature&image_type=photo")
    fun getPhotos() : Call<PhotoList>
}