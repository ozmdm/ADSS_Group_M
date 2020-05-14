package DataAccessLaye;

import java.sql.Connection;

public class ItemStatusDAO {
    private Connection conn;
public ItemStatusDAO(Connection conn)
{
    this.conn = conn;
}
}
