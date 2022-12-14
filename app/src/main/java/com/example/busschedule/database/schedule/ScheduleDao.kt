package com.example.busschedule.database.schedule

import androidx.room.Dao
import androidx.room.Query

// We use an interface instead of a class in this file
// class ScheduleDao {}
@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule ORDER BY arrival_time ASC")
    fun getAll(): List<Schedule>

    @Query("SELECT * FROM schedule WHERE stop_name = :stopName ORDER BY arrival_time ASC")
    fun getByStopName(stopName: String): List<Schedule>
}
