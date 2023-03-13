package cluster;

import com.google.common.net.InetAddresses;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.net.InetAddress;


public class RemoteMessage implements Serializable {

    private static final long serialVersionUID = 3038938706955679746L;

    private InetAddress destinationHost;
    private String category;

    public RemoteMessage(final String category) {
        this(null, category);
    }

    public RemoteMessage(final String hostIp, final String category) {
        if (hostIp != null) {
            // destinationHost = InetAddress.getByAddress(hostIp,
            // IPAddressUtil.textToNumericFormatV4(hostIp));
            destinationHost = InetAddresses.forString(hostIp);
        }

        this.category = category;
    }

    @Override
    public String toString() {
        ToStringBuilder b = new ToStringBuilder(this).append(category);
        if (destinationHost != null) {
            b.append(destinationHost.getHostAddress());
        }

        return b.toString();
    }

    /**
     * @param category
     *           the category to set
     */
    public void setCategory(final String category) {
        this.category = category;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param
     *
     */
    public void setDestinationHost(final InetAddress host) {
        destinationHost = host;
    }

    /**
     * @return the destinationHost
     */
    public InetAddress getDestinationHost() {
        return destinationHost;
    }

}

