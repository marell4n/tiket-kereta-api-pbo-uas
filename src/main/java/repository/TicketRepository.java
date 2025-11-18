/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Ticket;

/**
 *
 * @author asus
 */
public class TicketRepository implements Repository<Ticket>{
    private Map<String, Ticket> ticketDatabase = new HashMap<>();

    @Override
    public Ticket findById(String id) {
        return ticketDatabase.get(id);
    }

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>(ticketDatabase.values());
    }
    
    @Override
    public void save(Ticket ticket) {
        ticketDatabase.put(ticket.getTicketId(), ticket);
        System.out.println("Tiket " + ticket.getTicketId() + " berhasil disimpan ke TicketRepository.");
    }
}
