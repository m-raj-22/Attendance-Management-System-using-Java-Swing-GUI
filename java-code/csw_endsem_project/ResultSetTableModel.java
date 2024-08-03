package csw_endsem_project;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class ResultSetTableModel {

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // Get number of columns
        int columnCount = metaData.getColumnCount();

        // Create vector to hold column names
        Vector<String> columnNames = new Vector<>();

        // Get column names
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // Create vector to hold data (rows)
        Vector<Vector<Object>> data = new Vector<>();

        // Get row data
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                row.add(rs.getObject(columnIndex));
            }
            data.add(row);
        }

        // Return DefaultTableModel
        return new DefaultTableModel(data, columnNames);
    }

    @Override
    public String toString() {
        return "ResultSetTableModel []";
    }
}