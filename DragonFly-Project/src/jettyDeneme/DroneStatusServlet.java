package jettyDeneme;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
import controller.DroneController;
import controller.EnvironmentController;
import model.entity.drone.Drone;
import model.entity.drone.DroneBusinessObject;
import org.eclipse.jetty.util.IO;
import view.SelectableView;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DroneStatusServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String droneId = req.getParameter("droneId");

        DroneController droneController = DroneController.getInstance();

        if(droneId == null){
            Map<String, Drone> droneMap = droneController.getDroneMap();
            List<DroneData> allDroneData = droneMap.values().stream()
                    .map(DroneData::new)
                    .collect(Collectors.toList());

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(allDroneData);

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(jsonResponse);
        } else {
            Drone drone = droneController.getDroneFrom(droneId);

            if (drone == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().println("Drone not found");
                return;
            }

            DroneData droneData = new DroneData(drone);
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(droneData);

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(jsonResponse);
        }

    }


}
