package cache;

public class DAOFactory {

    public static <T extends BaseDAO> T create(Class<?> element) {
        BaseDAO dao = null;
        if (element == GekoPairDAO.class) {
            dao = new GekoPairDAO();

            return (T) dao;
        }
        return null;
    }
}
