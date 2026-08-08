package org.example.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import org.example.entities.Train;
public class TrainService {

    private static final String TRAIN_PATH = "app/src/main/java/LocalDB/trains.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private List<Train> trainLists;
    public TrainService() throws IOException {
        loadTrainData();
    }
    public void loadTrainData() throws IOException {
        File trainFile = new File(TRAIN_PATH);
        trainLists = objectMapper.readValue(trainFile, new TypeReference<List<Train>>() {});
    }
    public List<Train> searchTrains(String source, String destination) { 
        return trainLists.stream()
                .filter(train -> validTrain(train, source, destination))
                .toList();
    }
    private boolean validTrain(Train train, String source, String destination) {
        List<String> stations = train.getStations();
        int sourceIndex = stations.indexOf(source);
        int destinationIndex = stations.indexOf(destination);
        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }
    public List<Train> getTrainList()
    {
        return trainLists;
    }
    


}
