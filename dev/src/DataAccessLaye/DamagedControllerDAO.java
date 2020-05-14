package DataAccessLaye;

import java.sql.Connection;

public class DamagedControllerDAO {
    private Connection conn;
    public DamagedControllerDAO(Connection conn)
    {
        this.conn = conn;
    }
}
