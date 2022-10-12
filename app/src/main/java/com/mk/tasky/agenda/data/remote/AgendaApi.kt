package com.mk.tasky.agenda.data.remote

import com.mk.tasky.agenda.data.remote.dto.*
import okhttp3.MultipartBody
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

    @POST("/task")
    suspend fun createTask(@Body body: TaskDto)

    @PUT("/task")
    suspend fun updateTask(@Body body: TaskDto)

    @DELETE("/task")
    suspend fun deleteTask(@Query("taskId") id: String)

    @GET("/agenda")
    suspend fun getAgenda(
        @Query("time") time: Long,
        @Query("timezone") timeZone: String = TimeZone.getDefault().id
    ): AgendaResponseDto

    @GET("/attendee")
    suspend fun getAttendee(@Query("email") email: String): AttendeeExistResponseDto

    @Multipart
    @POST("/event")
    suspend fun createEvent(@Part body: MultipartBody.Part, @Part files: List<MultipartBody.Part>): EventResponseDto
}
