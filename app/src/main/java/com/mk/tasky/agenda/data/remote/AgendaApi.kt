package com.mk.tasky.agenda.data.remote

import com.mk.tasky.agenda.data.remote.dto.AgendaResponseDto
import com.mk.tasky.agenda.data.remote.dto.ReminderDto
import retrofit2.http.*
import java.util.*

interface AgendaApi {
    companion object {
        const val BASE_URL = "https://tasky.pl-coding.com/"
    }

    @POST("/reminder")
    suspend fun createReminder(@Body body: ReminderDto)

    @PUT("/reminder")
    suspend fun updateReminder(@Body body: ReminderDto)

    @DELETE("/reminder")
    suspend fun deleteReminder(@Query("reminderId") id: String)

    @GET("/agenda")
    suspend fun getAgenda(
        @Query("time") time: Long,
        @Query("timezone") timeZone: String = TimeZone.getDefault().id
    ): AgendaResponseDto
}
