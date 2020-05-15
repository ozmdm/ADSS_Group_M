package DataAccessLaye;

import java.sql.Connection;

public class BranchDAO {

    private Connection conn;
    public BranchDAO(Connection conn)
    {
        this.conn = conn;
    }
}
