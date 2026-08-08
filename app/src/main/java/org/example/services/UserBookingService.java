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
public class UserBookingService {
    private User user;
    private static final String USER_PATH ="/app/src/main/java/LocalDB/users.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private List<User> userLists;
    public UserBookingService(User user) throws IOException {
        this.user = user;
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
    public Boolean signUp()
    {
        try
        {
            userLists.add(user);
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
       
}