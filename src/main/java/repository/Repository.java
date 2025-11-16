/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package repository;

import java.util.List;
import model.Schedule;
/**
 *
 * @author asus
 */
public interface Repository {
    Schedule findById(String id);
    List<Schedule> findAll();
    
    void save(Schedule object);
}
