package cluster;

public class ClusterException extends RuntimeException {

    private static final long serialVersionUID = -632423086565831654L;

    public ClusterException() {
    }

    public ClusterException(final String message) {
        super(message);
    }

    public ClusterException(final Throwable cause) {
        super(cause);
    }

    public ClusterException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
