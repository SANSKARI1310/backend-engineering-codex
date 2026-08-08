package org.example.entities;
import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Boolean>> seatAvailability;
    private Map<String , String> trainSchedule;
    private List<String> stations;
    public Train() {
    }
    public Train(String trainId, String trainNo, List<List<Boolean>> seatAvailability, Map<String, String> trainSchedule, List<String> stations) {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seatAvailability = seatAvailability;
        this.trainSchedule = trainSchedule;
        this.stations = stations;
    }
    public String getTrainId() {
        return trainId;
    }
    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }   

    public String getTrainNo() {
        return trainNo;
    }
    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }
    public List<List<Boolean>> getSeatAvailability() {
        return seatAvailability;
    }
    public void setSeatAvailability(List<List<Boolean>> seatAvailability) {
        this.seatAvailability = seatAvailability;
    }
    public Map<String, String> getTrainSchedule() {
        return trainSchedule;
    }
    public void setTrainSchedule(Map<String, String> trainSchedule) {
        this.trainSchedule = trainSchedule;
    }
    public List<String> getStations() {
        return stations;
    }
    public void setStations(List<String> stations) {
        this.stations = stations;
    }
    public int getSeatCount() {
        int count = 0;
        for (List<Boolean> row : seatAvailability) {
            for (Boolean seat : row) {
                if (seat) {
                    count++;
                }
            }
        }
        return count;
    }
    public void getTrainInfo()
    {
        System.out.println("Train ID: "+trainId+" Train No: "+trainNo);
        System.out.println("Train Schedule: ");
        for(Map.Entry<String,String> entry : trainSchedule.entrySet())
        {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
        System.out.println("Stations: ");
        for(int i=0;i<stations.size();i++)
        {
            System.out.println(stations.get(i));
        }
        System.out.println("Seat Availability: "+ getSeatCount());
    }
    


}
