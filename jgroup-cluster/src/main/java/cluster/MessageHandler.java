package cluster;

public interface MessageHandler {

    void execute(RemoteMessage message);
}
