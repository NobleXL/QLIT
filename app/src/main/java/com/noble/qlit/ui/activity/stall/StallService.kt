package com.noble.qlit.ui.activity.stall

import com.noble.qlit.data.repository.Network
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author: NobleXL
 * @desc: StallService
 */
interface StallService {

    @GET("swipe/list")
    suspend fun swipeList(): SwiperResponse

    @GET("stall/list")
    suspend fun stallList(): StallListResponse

    @GET("job/list")
    suspend fun jobList(): StallListResponse

    @POST("stall/listP")
    suspend fun info(@Query("id") id: String): StallInfoResponse

    @POST("job/listP")
    suspend fun info2(@Query("id") id: String): StallInfoResponse

    @POST("stall/save")
    suspend fun add(
        @Body apiEntity: StallEntity
    ): Boolean

    @POST("job/save")
    suspend fun add2(
        @Body apiEntity: StallEntity
    ): Boolean

    companion object {
        fun instance(): StallService {
            return Network.createService(StallService::class.java)
        }
    }

}