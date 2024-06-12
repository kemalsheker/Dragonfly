package jettyDeneme;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controller.DroneAutomaticController;
import controller.DroneController;
import model.entity.drone.Drone;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class DroneWebSocket { // CONTINUE FROM HERE , FIGURE OUT IMPLEMENTING WEB SOCKETS
    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();
    private static final Gson gson = new Gson();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        sessions.add(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        System.out.println("Received message from the adaptation module: " + message);
        try {
            // Parse the JSON message using Gson
            JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();

            // Check the action field
            String action = jsonMessage.get("action").getAsString();
            System.out.println("Action: " + action);


            switch (action) {
                case "overrideGoDestinyAutomatic":
                    DroneController.getInstance().overrideGoDestinyAutomatic(true);
                    break;
                // You can add more cases here as needed
                case "GoDestinyAutomatic":
                    DroneController.getInstance().overrideGoDestinyAutomatic(false);
                    break;
                default:
                    System.out.println("No context change/or adaptation");
            }


        } catch (Exception e) {
            System.err.println("Failed to parse message: " + e.getMessage());
        }
    }

    public static void broadcastDroneData(DroneData droneData) {
        String jsonMessage = gson.toJson(droneData);
        sessions.forEach(session -> {
            try {
                session.getRemote().sendString(jsonMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
