package com.noble.qlit.data.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author: NobleXL
 * @desc: Network
 */
object Network {

    //mock 数据请求 url
    private const val baseUrl =
        "http://192.168.254.101:8090/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        ).build()

    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

}