package cache;

import java.util.List;

abstract class BaseDAO {
    protected abstract <T extends Object> T findByName(String name);

    protected abstract <T extends Object> List<T> findAll();
}
