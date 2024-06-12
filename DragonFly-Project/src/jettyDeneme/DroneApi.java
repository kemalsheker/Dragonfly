package jettyDeneme;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.WebSocketHandler;

public class DroneApi implements Runnable {



    public static void startServer() throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new DroneStatusServlet()), "/drone/status");

        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(org.eclipse.jetty.websocket.servlet.WebSocketServletFactory webSocketServletFactory) {
                webSocketServletFactory.register(DroneWebSocket.class);
            }
        };
        context.insertHandler(wsHandler);

        server.start();
        server.join();
    }

    @Override
    public void run() {
        try {
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

