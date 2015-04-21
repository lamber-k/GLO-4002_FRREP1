package ca.ulaval.glo4002.rest.configuration;

import ca.ulaval.glo4002.core.room.Room;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ApplicationConfiguration {

    private final Properties properties;
    private String path;

    public ApplicationConfiguration(String path) throws IOException {
        this.path = path;
        properties = retrieveProperties();
    }

    private Properties retrieveProperties() throws IOException {
        Properties properties = new Properties();
        InputStream configFile = getClass().getClassLoader().getResourceAsStream(this.path);
        if (configFile != null) {
            properties.load(configFile);
        } else {
            throw new FileNotFoundException("Property file '" + this.path + "'.");
        }
        return properties;
    }

    public int getMaximumPendingRequest() {
        String maximumPendingRequests = properties.getProperty("maximumPendingRequests", "42");
        return Integer.parseInt(maximumPendingRequests);
    }

    public List<Room> getDefaultRooms() {
        String defaultRoomsNameProperties = properties.getProperty("defaultRoomName");
        String defaultRoomsSizeProperties = properties.getProperty("defaultRoomSize");
        String[] defaultRoomsName = convertPropertyToArray(defaultRoomsNameProperties);
        String[] defaultRoomsSize = convertPropertyToArray(defaultRoomsSizeProperties);
        if (defaultRoomsName.length != defaultRoomsSize.length) {
            return null;
        }
        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < defaultRoomsName.length; ++i) {
            int size = Integer.parseInt(defaultRoomsSize[i]);
            rooms.add(new Room(size, defaultRoomsName[i]));
        }
        return rooms;
    }

    public int getIntervalTimer() {
        String intervalProperty = properties.getProperty("intervalTimer", "1");
        return Integer.parseInt(intervalProperty);
    }

    private String[] convertPropertyToArray(String property) {
        return property.split(";");
    }
}
