import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@WebServlet(urlPatterns="/KalahaGame")
public class KalahaGameWebSocketServlet  extends WebSocketServlet{

    @Override
    public void configure(WebSocketServletFactory factory) {

        factory.register(KalahaGameWebSocket.class);

    }

}