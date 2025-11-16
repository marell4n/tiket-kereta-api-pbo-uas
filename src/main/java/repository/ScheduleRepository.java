/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.*;

/**
 *
 * @author asus
 */
public class ScheduleRepository implements Repository {
    private Map<String, Station> stationCache = new HashMap<>();
    private Map<String, Train> trainCache = new HashMap<>();
    private Map<String, Schedule> scheduleDatabase = new HashMap<>();
    
    @Override
    public Schedule findById(String id) {
        return scheduleDatabase.get(id);
    }
    
    @Override
    public List<Schedule> findAll() {
        return new ArrayList<>(scheduleDatabase.values());
    }
    
    @Override
    public void save(Schedule object) {
        scheduleDatabase.put(object.getScheduleId(), object);
        System.out.println("Jadwal " + object.getScheduleId() + " berhasil disimpan.");
    }
    
     /**
     * Sesuai dengan diagram UML.
     */
    public Train getTrain(String trainId) {
        return trainCache.get(trainId);
    }
    
    /**
     * Sesuai dengan diagram UML.
     */
    public Station getStation(String stationId) {
        return stationCache.get(stationId);
    }

    // Helper methods untuk mengisi cache (untuk demo)
    public void addStationToCache(Station station) {
        stationCache.put(station.getStationCode(), station);
    }

    public void addTrainToCache(Train train) {
        trainCache.put(train.getTrainNumber(), train);
    }
}
