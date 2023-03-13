package cache;

import java.io.Serializable;

public class SimpleDTO implements Serializable {

    private Long id;
    private String name;


    public SimpleDTO() {
    }

    public SimpleDTO(Long id) {
        this.id = id;
    }


    public SimpleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "cache.SimpleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
