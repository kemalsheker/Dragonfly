package jettyDeneme;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import java.util.concurrent.*;

@WebSocket
public class DroneWebSocket { // CONTINUE FROM HERE , FIGURE OUT IMPLEMENTING WEB SOCKETS
    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();
    private static final Gson gson = new Gson();
    private static final ConcurrentHashMap<Session, BlockingQueue<String>> messageQueues = new ConcurrentHashMap<>();
    private static final int QUEUE_CAPACITY = 200;

    @OnWebSocketConnect
    public void onConnect(Session session) {
        sessions.add(session);
        messageQueues.put(session, new LinkedBlockingQueue<>(QUEUE_CAPACITY));
        startMessageSender(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        sessions.remove(session);
        messageQueues.remove(session);
    }




    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        System.out.println("Received message from the adaptation module: " + message);
        try {
            JsonArray jsonMessages = JsonParser.parseString(message).getAsJsonArray();
            for (int i = 0; i < jsonMessages.size(); i++) {
                JsonObject jsonMessage = jsonMessages.get(i).getAsJsonObject();
                handleAction(jsonMessage);
            }
        } catch (Exception e) {
            System.err.println("Failed to parse message: " + e.getMessage());
        }
    }

    private void handleAction(JsonObject jsonMessage) {
        String droneId = jsonMessage.get("id").getAsString();
        String action = jsonMessage.get("action").getAsString();
        System.out.println("Drone ID: " + droneId + ", Action: " + action);

        DroneController droneController = DroneController.getInstance();
        Drone drone = droneController.getDroneFrom(droneId);
        if (drone == null) {
            System.err.println("Drone with ID " + droneId + " not found.");
            return;
        }

        switch (action) {
            case "overrideGoDestinyAutomatic":
                droneController.overrideGoDestinyAutomatic(droneId, true);
                drone.setOverrideGoDestinyAutomaticFlag(true);
                break;
            case "GoDestinyAutomatic":
                droneController.overrideGoDestinyAutomatic(droneId, false);
                drone.setOverrideGoDestinyAutomaticFlag(false);
                break;
            case "ExecuteLanding":
                droneController.setExecuteLanding(droneId, true);
                drone.setExecuteLanding(true);
                break;
            case "NoOverrideLanding":
                droneController.setExecuteLanding(droneId, false);
                drone.setExecuteLanding(false);
                break;
            case "SafeLandingFalse":
                droneController.setSafeLanding(droneId, false);
                drone.setSafeLanding(false);
                break;
            case "SafeLandingTrue":
                droneController.setSafeLanding(droneId, true);
                drone.setSafeLanding(true);
                break;
            case "returnBase":
                droneController.setReturnBase(droneId, true);
                drone.setReturnBase(true);
                break;
            case "seekConnection":
                droneController.setSeekConnection(droneId, true);
                drone.setSeekConnection(true);
                break;
            case "noBadConnection":
                droneController.setSeekConnection(droneId, false);
                drone.setSeekConnection(false);
                break;
            case "EmergencyLanding":
                droneController.setEmergency(droneId, true);
                drone.setEmergency(true);
                break;
            default:
                System.out.println("No context change/or adaptation");
                break;
        }
    }



    public static void broadcastDroneData(DroneData droneData) {
        String jsonMessage = gson.toJson(droneData);
        System.out.println("Broadcasting Drone Data: " + jsonMessage);
        sessions.forEach(session -> {
            try {
                messageQueues.get(session).offer(jsonMessage, 100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    private void startMessageSender(Session session) {
        Executors.newSingleThreadExecutor().submit(() -> {
           BlockingQueue<String> queue = messageQueues.get(session);
           while (session.isOpen()) {
               try {
                   String message = queue.take();
                   session.getRemote().sendStringByFuture(message).get();
               } catch (InterruptedException | ExecutionException e) {
                   if(e.getCause() instanceof IOException){
                       break;
                   }
                   e.printStackTrace();
               }
           }
        });
    }
}
