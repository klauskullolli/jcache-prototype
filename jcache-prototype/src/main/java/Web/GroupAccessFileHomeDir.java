package Web;

import java.io.Serializable;

public class GroupAccessFileHomeDir implements Serializable {

    private static final long serialVersionUID = 7441939714810151414L;

    private String path;
    private Long idFile;
    private Long idRepository;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getIdFile() {
        return idFile;
    }

    public void setIdFile(Long idFile) {
        this.idFile = idFile;
    }

    public Long getIdRepository() {
        return idRepository;
    }

    public void setIdRepository(Long idRepository) {
        this.idRepository = idRepository;
    }

}
