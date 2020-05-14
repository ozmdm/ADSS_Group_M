package DataAccessLaye;

import java.sql.Connection;

public class ItemDAO {
    private Connection conn;

    public ItemDAO(Connection conn)
    {
        this.conn = conn;
    }
}
