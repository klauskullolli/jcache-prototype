package cluster;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class ClusterReceiver extends ReceiverAdapter {

    private static Log log = LogFactory.getLog(ClusterReceiver.class);

    private static final String JGROUP_CONFIGURATION_FILE_PATH = "cluster.xml";
    private static final String CHANNEL_NAME = "YouserXPCluster";

    private JChannel channel;
    private final ClusterManager manager;

    ClusterReceiver(final ClusterManager manager) {
        this.manager = manager;
        // System.setProperty("java.net.preferIPv4Stack", "true");
    }

    protected void start() {
        final URL confPath = getClass().getClassLoader().getResource(ClusterReceiver.JGROUP_CONFIGURATION_FILE_PATH);
        try {
            channel = new JChannel(JGROUP_CONFIGURATION_FILE_PATH);
            channel.setReceiver(this);
            channel.connect(ClusterReceiver.CHANNEL_NAME);
            log.info("Created channel: " + channel.getName());
        } catch (final Exception e) {
            throw new ClusterException(e);
        }
    }

    protected void stop() {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
    }

    @Override
    public void receive(final Message msg) {
        log.info("Received message: " + msg + ". Channel: " + channel.getAddressAsString());

        final Object cmdObj = msg.getObject();
        if (cmdObj instanceof RemoteMessage) {
            final RemoteMessage cmd = (RemoteMessage) cmdObj;
            try {
                if (cmd.getDestinationHost() == null || InetAddress.getLocalHost().getHostAddress().equals(cmd.getDestinationHost().getHostAddress())) {
                    manager.processMessage(cmd);
                }
            } catch (final UnknownHostException e) {
                log.error(e, e);
            }
        }
    }

    protected void send(final RemoteMessage cmd) {
        if (channel != null && channel.isOpen()) {
            final Message msg = new Message();
            msg.setObject(cmd);
            log.info("Sending message: " + msg);
            try {
                channel.send(msg);
            } catch (final Exception e) {
                throw new ClusterException(e);
            }
        } else {
            log.info("Unable to send message: " + cmd + " Channel is null or closed.");
        }
    }
}
