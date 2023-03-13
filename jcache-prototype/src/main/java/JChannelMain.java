import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

import java.net.URL;
import java.util.Scanner;

public class JChannelMain {

    private static final String CONFIG_PATH = "cluster.xml";

    private static final String CLUSTERNAME = "test";

    protected static Log log = LogFactory.getLog(JChannelMain.class);

    public static void main(String[] args) throws Exception {

        final URL confPath = JChannelMain.class.getClassLoader().getResource(CONFIG_PATH);
        JChannel channel = new JChannel(confPath);

        channel.setReceiver(new ReceiverAdapter() {
            @Override
            public void receive(Message msg) {
                log.info(String.format("SRC -> %s || DES -> %s || MSG -> %s  ", msg.getSrc(), msg.getDest(), msg.getObject()));
            }

            @Override
            public void viewAccepted(View view) {
                log.info(String.format("%s", view));
            }
        });

        channel.connect(CLUSTERNAME);

        Scanner input = new Scanner(System.in);

        while (true) {
            String message = input.nextLine();
            Message msg = new Message(null, null, message);
            channel.send(msg);
        }
    }
}