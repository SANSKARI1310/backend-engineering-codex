package org.example.entities;
import java.util.List;

public class User {
    private String name;
    private String password;
    private String hashPassword;
    List<Ticket>  ticketsBooked;
    private String UUID; 

    public User(String name, String password, String hashPassword, List<Ticket> tickets, String UUID) {
        this.name = name;
        this.password = password;
        this.hashPassword = hashPassword;
        this.ticketsBooked = ticketsBooked;
        this.UUID = UUID;
    }
    public User()
    {}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;      
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getHashPassword() {
        return hashPassword;    
    }
    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }
    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;     
    }
    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }
    public String getUUID() {
        return UUID;   
    }
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
    public void printTickets()
    {
        for(int i=0;i<ticketsBooked.size();i++)
        {
            System.out.println(ticketsBooked.get(i).getTicketInfo());
        }
    }
    
}
