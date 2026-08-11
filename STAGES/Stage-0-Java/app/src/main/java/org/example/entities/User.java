package org.example.entities;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String name;
    private String password;
    private String hashPassword;
    List<Ticket>  ticketsBooked;
    @JsonProperty("UUID")
    private String UUID; 

    public User(String name, String password, String hashPassword, List<Ticket> ticketsBooked, String UUID) {
        this.name = name;
        this.password = password;
        this.hashPassword = hashPassword;
        this.ticketsBooked = ticketsBooked;
        this.UUID = UUID;
    }
    public User()
    {
        ticketsBooked = new ArrayList<>();
    }
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
    @JsonIgnore
    public void printTickets(String name)
    {
         if(ticketsBooked.size()==0 || ticketsBooked==null)
         {   System.out.println("No tickets booked.");
             return;
            } 
        for(int i=0;i<ticketsBooked.size();i++)
        {
            System.out.println(ticketsBooked.get(i).getTicketInfo());
        }
    }
    
}
