package com.example.projectlimbrescue.db.session;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.projectlimbrescue.db.device.Device;
import com.example.projectlimbrescue.db.sensor.Sensor;
import com.example.projectlimbrescue.db.sensor.SensorWithDevices;
import com.example.projectlimbrescue.db.sensor.SensorWithReadings;
import com.example.projectlimbrescue.db.sensor.SensorWithSessions;

import java.util.List;

/*
Data access object for the Session entity, providing the methods used to query the session table.
 */

@Dao
public interface SessionDao {
    // Simple "placeholder" methods for now; add more in as functionality or testing requires
    @Query("SELECT * FROM Session")
    List<Session> getSessions();
    @Transaction
    @Query("SELECT * FROM Session")
    List<SessionWithDevices> getSessionsWithDevices();
    @Transaction
    @Query("SELECT * FROM Session")
    List<SessionWithReadings> getSessionsWithReadings();
    @Transaction
    @Query("SELECT * FROM Session")
    List<SessionWithSensors> getSessionsWithSensors();

    @Query("SELECT * FROM Session WHERE session_id IN (:ids)")
    List<Session> getSessionsByIds(int[] ids);
    @Transaction
    @Query("SELECT * FROM Session WHERE session_id IN (:ids)")
    List<SessionWithDevices> getSessionsWithDevicesByIds(int[] ids);
    @Transaction
    @Query("SELECT * FROM Session WHERE session_id IN (:ids)")
    List<SessionWithReadings> getSessionsWithReadingsByIds(int[] ids);
    @Transaction
    @Query("SELECT * FROM Session WHERE session_id IN (:ids)")
    List<SessionWithSensors> getSessionsWithSensorsByIds(int[] ids);

    @Insert
    void insert(Session... sessions);

    @Delete
    void delete(Session session);
}
