package cache;

import java.util.ArrayList;
import java.util.List;


public class GekoPairDAO extends BaseDAO {


    private List<GekoPair> gekoPairs = new ArrayList<>();

    public GekoPairDAO() {
        gekoPairs.add(new GekoPair("test1", "1"));
        gekoPairs.add(new GekoPair("test2", "test"));
        gekoPairs.add(new GekoPair("test3", "2.5"));
        gekoPairs.add(new GekoPair("test4", "12/06/2022"));
        gekoPairs.add(new GekoPair("test5", "true"));
        gekoPairs.add(new GekoPair("test6", "12/06/2022 06:55:54"));

    }

    @Override
    protected GekoPair findByName(String name) {
        if (gekoPairs.size() > 0) {
            for (GekoPair pair : gekoPairs) {
                if (pair.getName().equals(name)) {
                    return pair;
                }
            }

            return null;
        }
        return null;
    }

    @Override
    protected List<GekoPair> findAll() {
        return gekoPairs;
    }

    public void addGekoPair(String name, String value) {
        GekoPair gekoPair = findByName(name);
        if (gekoPair == null) {
            gekoPair = new GekoPair(name, value);
            gekoPairs.add(gekoPair);
        } else {
            gekoPair.setValue(value);
        }
    }

    public GekoPair removeGekoPair(String name) {
        GekoPair gekoPair = findByName(name);

        if (gekoPair != null) {
            gekoPairs.remove(gekoPair);
            return gekoPair;
        }

        return null;
    }


}
