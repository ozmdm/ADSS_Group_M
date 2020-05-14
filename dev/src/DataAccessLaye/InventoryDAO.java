package DataAccessLaye;

import java.sql.Connection;

public class InventoryDAO {
    private Connection conn;

    public InventoryDAO (Connection conn)
    {
        this.conn = conn;
    }
}
