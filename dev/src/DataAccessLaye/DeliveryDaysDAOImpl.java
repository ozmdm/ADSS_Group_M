package DataAccessLaye;

import ServiceLayer.ServiceObjects.CatalogItemDTO;
import ServiceLayer.ServiceObjects.DeliveryDaysDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDaysDAOImpl implements IDeliveryDaysDAO {
    private Connection conn;

    public DeliveryDaysDAOImpl(Connection conn)
    {
        this. conn = conn;
    }

    @Override
    public DeliveryDaysDTO findAllByContract(int contractId) throws SQLException {
        List<DayOfWeek> dayOfWeeks = new ArrayList<>();
        String sql = "SELECT * "
                + "FROM DeliveryDays WHERE contractId = ?";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        //
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String date = rs.getString("Deliday");
            dayOfWeeks.add(DayOfWeek.valueOf(date));
        }
        return new DeliveryDaysDTO(dayOfWeeks);
    }

    @Override
    public void insert(DeliveryDaysDTO deliveryDaysDTO,int contractId) throws SQLException {
        for (DayOfWeek dayOfWeek : deliveryDaysDTO.dayOfWeeks()) {
            String sql = "INSERT INTO DeliveryDays(contractId,Deliday) VALUES(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, contractId);
            preparedStatement.setString(2, dayOfWeek.toString());
            preparedStatement.executeUpdate();
        }
    }
}
