package org.example.services;
import org.example.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.Optional;
import org.example.util.userServiceutil;
import org.example.entities.Ticket;
import org.example.services.TrainService;
import org.example.entities.Train;
import java.time.LocalDateTime;
import java.util.UUID;
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
    public Boolean loginUser()
    {
        Optional<User> foundUser = userLists.stream().filter(
            user1-> user1.getName().equalsIgnoreCase(user.getName()) 
            && userServiceutil.checkPassword(user1.getPassword(), user.getPassword())).findFirst();
        return foundUser.isPresent();
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
        user.printTickets();
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
                train.getTrainInfo();
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