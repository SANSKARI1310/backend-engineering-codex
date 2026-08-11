package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ticket {
    private String ticketId;
    private String source;
    private String destination;
    private Train train;
    private String date;
    public Ticket() {
    }   
    public Ticket(String ticketId, String source, String destination, Train train, String date) {
        this.ticketId = ticketId;
        this.source = source;
        this.destination = destination;
        this.train = train;
        this.date = date;
    }
    public String getTicketId() {
        return ticketId;            
    }
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }   
    public String getSource() {
        return source;            
    }
    public void setSource(String source) {
        this.source = source;       
    }
    public String getDestination() {
        return destination;            
    }   
    public void setDestination(String destination) {
        this.destination = destination;       
    }
    public Train getTrain() {   
        return train;            
    }
    public void setTrain(Train train) { 
        this.train = train;       
    }   
    public String getDate() {   
        return date;            
    }
    public void setDate(String date) {
        this.date = date;   
    }
    @JsonIgnore
    public String getTicketInfo()
    {
        return "Ticket ID: "+ticketId+" Source: "+source+" Destination: "+destination+" Train No: "+train.getTrainNo()+" Date: "+date;
    }
    
}
