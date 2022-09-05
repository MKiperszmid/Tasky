package com.mk.tasky.agenda.data.remote

import com.mk.tasky.agenda.data.remote.dto.ReminderDto
import retrofit2.http.*

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
}
