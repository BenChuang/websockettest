package websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)
public class WebsocketTest {

    private static volatile int onlineCount = 0;
    private static CopyOnWriteArraySet<WebsocketTest> allWebsockets = new CopyOnWriteArraySet<WebsocketTest>();
    private Session session;
    private HttpSession httpSession;
    private String username = "";

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
        this.httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        this.username = (String) this.httpSession.getAttribute("name");
        allWebsockets.add(this);
        onlineCountAdd();
        String tip = getName() + " is in, the amount of online is " + onlineCount;
        broadcastSystemMessage(tip);
        System.out.println(tip);
    }

    @OnClose
    public void onClose() {
        allWebsockets.remove(this);
        onlineCountMinus();
        String tip = getName() + " is out, the amount of online is " + onlineCount;
        broadcastSystemMessage(tip);
        System.out.println(tip);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        broadcastChatMessage(message);
    }

    @OnError
    public void onError(Session session, Throwable e) {
        e.printStackTrace();
    }

    private void sentSystemMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sentChatMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastSystemMessage(String message) {
        message = "系统通知: " + message;
        for (WebsocketTest websocketTest :
                allWebsockets) {
            websocketTest.sentSystemMessage(message);
        }
    }

    private void broadcastChatMessage(String message) {
        message = getName() + ": " + message;
        for (WebsocketTest websocketTest :
                allWebsockets) {
            websocketTest.sentChatMessage(message);
        }
    }


    private String getName() {
        return this.username;
    }


    private synchronized void onlineCountMinus() {
        onlineCount--;
    }


    private synchronized void onlineCountAdd() {
        onlineCount++;
    }

}
