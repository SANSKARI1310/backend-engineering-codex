package org.example.services;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.userServiceutil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
public class UserBookingService {
    private User user;
    private static final String USER_PATH ="app/src/main/java/LocalDB/users.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private List<User> userLists;
    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserData();
    }
    public UserBookingService() throws IOException {
        loadUserData();
    }
    public void loadUserData() throws IOException {
        File userFile = new File(USER_PATH);
        userLists = objectMapper.readValue(userFile, new TypeReference<List<User>>() {});
    }
    public User loginUser(String username, String password)
    {
        Optional<User> foundUser = userLists.stream()
        .filter(user ->
            user.getName().equalsIgnoreCase(username)
            && userServiceutil.checkPassword(
                password,
                user.getHashPassword()
            )
        )
        .findFirst();

    return foundUser.orElse(null);
    }
    public Boolean signUp(User newUser)
    {
        try
        {
            userLists.add(newUser);
            saveUsersToFile();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    private void saveUsersToFile() throws IOException {
        File userFile = new File(USER_PATH);
        objectMapper.writeValue(userFile, userLists);
    }
    public void fetchBooking()
    {
        System.out.println(user.getTicketsBooked().size());
        user.printTickets(user.getName());
    }
    public Boolean cancelBooking(String ticketId)
    {
       List<Ticket> tickets = user.getTicketsBooked();
       Optional<Ticket> abtToCancel = tickets.stream().filter(tickets1->tickets1.getTicketId().equalsIgnoreCase(ticketId)).findFirst();
        if(abtToCancel.isPresent())
        {
            tickets.remove(abtToCancel.get());
            user.setTicketsBooked(tickets);
            try
            {
                saveUsersToFile();
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    public void findTrain(String source , String destination)
    {
        try{
        TrainService trainService = new TrainService();
        List<Train> trains = trainService.searchTrains(source, destination);
        if(trains.isEmpty())
        {
            System.out.println("No trains found for the given source and destination.");
        }
        else{
            System.out.println("Trains found:");
            int count=1;
            for(Train train : trains)
            {
                System.out.println("Train " + count + ":");
                train.trainInfo();
                count++;
            }
        }
        }catch(Exception e)
        {
            System.out.println("Error loading train data: " + e.getMessage());
        }
    }
    public void bookTicket(String trainId , String source , String destination)
    {
       try{
        TrainService trainService = new TrainService();
        List<Train> trains = trainService.getTrainList();
        Optional<Train> abtToBook = trains.stream().filter(train1->train1.getTrainId().equalsIgnoreCase(trainId)).findFirst();
        if(abtToBook.isPresent())
        {
            Train train = abtToBook.get();
            Ticket ticket = new Ticket(UUID.randomUUID().toString(),source,destination,train, LocalDateTime.now().toString());
            Boolean updateSuccess = updateTicket(ticket);
            if(updateSuccess)
            {
                System.out.println("Ticket booked successfully!");
                System.out.println("Ticket ID: " + ticket.getTicketId());
            }
            else
            {
                System.out.println("Failed to book ticket. Please try again.");
            }
        }
        else
        {
            System.out.println("Train not found for the given train ID.");
        }
       }
       catch(Exception e)
       {
        System.out.println("Error loading train data: " + e.getMessage());
       }
    }
    public Boolean updateTicket(Ticket ticket)
    {

        List<Ticket> tickets = user.getTicketsBooked();
        tickets.add(ticket);
        user.setTicketsBooked(tickets);
        try
        {
            saveUsersToFile();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
       
}