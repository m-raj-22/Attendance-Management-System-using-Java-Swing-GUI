package csw_endsem_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Modify implements ActionListener {
    Connection connection = null;
    JFrame mf = new JFrame();
    ImageIcon icon = new ImageIcon("img/modify.png");
    JButton backButton = new JButton("< BACK");
    JButton saveButton = new JButton("SAVE");
    JLabel mainLabel = new JLabel("MODIFY ATTENDANCE RECORDS");
    JLabel currentLabel = new JLabel("CURRENT ENTRIES");
    JLabel newEntryLabel = new JLabel("MODIFY AN ENTRY");
    JLabel idLabel = new JLabel("Student ID:");
    JLabel nameLabel = new JLabel("Student Name:");
    JLabel dateLabel = new JLabel("Date:");
    JLabel statusLabel = new JLabel("Attendance Status:");

    JTextField idField = new JTextField();
    JTextField nameField = new JTextField();

    JComboBox<String> dayComboBox = new JComboBox<>(getDays());
    JComboBox<String> monthComboBox = new JComboBox<>(getMonths());
    JComboBox<String> yearComboBox = new JComboBox<>(getYears());

    String statusArr[] = { "Present", "Absent" };
    JComboBox<String> statusBox = new JComboBox<>(statusArr);

    JScrollPane scrollPane = new JScrollPane();
    JTable table = new JTable();

    JSeparator lineSep = new JSeparator();

    public Modify() {
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mf.setUndecorated(true);
        mf.setResizable(false);
        mf.setSize(1200, 560);
        mf.setVisible(true);
        mf.setLocationRelativeTo(null);
        mf.setTitle("MODIFY");
        mf.getContentPane().setBackground(new Color(0x1d1d1d));
        mf.setLayout(null);
        mf.setIconImage(icon.getImage());

        backButton.setBounds(50, 473, 90, 36);
        backButton.setBackground(new Color(0x1d1b1b));
        backButton.setFocusable(false);
        backButton.setFont(new Font("Calibri", Font.PLAIN, 19));
        backButton.setForeground(new Color(0xEEEDE7));
        backButton.addActionListener(this);

        saveButton.setBounds(390, 473, 90, 36);
        saveButton.setBackground(new Color(0x1d1b1b));
        saveButton.setFocusable(false);
        saveButton.setFont(new Font("Calibri", Font.PLAIN, 19));
        saveButton.setForeground(new Color(0xEEEDE7));
        saveButton.addActionListener(this);

        mainLabel.setBounds(290, 27, 700, 40);
        mainLabel.setFont(new Font("Calibri", Font.PLAIN, 36));
        mainLabel.setForeground(new Color(0xEEEDE7));

        currentLabel.setBounds(720, 127, 270, 31);
        currentLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        currentLabel.setForeground(new Color(0xEEEDE7));

        newEntryLabel.setBounds(196, 127, 270, 31);
        newEntryLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        newEntryLabel.setForeground(new Color(0xEEEDE7));

        idLabel.setBounds(100, 200, 100, 21);
        idLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
        idLabel.setForeground(new Color(0xEEEDE7));

        nameLabel.setBounds(100, 240, 150, 21);
        nameLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
        nameLabel.setForeground(new Color(0xEEEDE7));

        dateLabel.setBounds(100, 280, 165, 21);
        dateLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
        dateLabel.setForeground(new Color(0xEEEDE7));

        statusLabel.setBounds(100, 320, 160, 21);
        statusLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
        statusLabel.setForeground(new Color(0xEEEDE7));

        idField.setBounds(277, 195, 200, 21);
        idField.setFont(new Font("Calibri", Font.PLAIN, 21));
        idField.setBorder(null);

        nameField.setBounds(277, 235, 200, 21);
        nameField.setFont(new Font("Calibri", Font.PLAIN, 21));
        nameField.setBorder(null);

        dayComboBox.setBounds(277, 275, 60, 21);
        monthComboBox.setBounds(347, 275, 50, 21);
        yearComboBox.setBounds(410, 275, 70, 21);

        statusBox.setBounds(277, 315, 200, 21);

        table.setBackground(new Color(0x121212));
        table.setForeground(new Color(0xEEEDE7));
        scrollPane.setBounds(520, 180, 650, 330);
        scrollPane.getViewport().setBackground(new Color(0x404040));
        scrollPane.setViewportView(table);

        lineSep.setOrientation(SwingConstants.VERTICAL);
        lineSep.setBounds(500, 115, 120, 420);

        mf.add(lineSep);
        mf.add(scrollPane);
        mf.add(mainLabel);
        mf.add(currentLabel);
        mf.add(newEntryLabel);
        mf.add(backButton);
        mf.add(saveButton);
        mf.add(idLabel);
        mf.add(nameLabel);
        mf.add(dateLabel);
        mf.add(statusLabel);
        mf.add(idField);
        mf.add(nameField);
        mf.add(dayComboBox);
        mf.add(monthComboBox);
        mf.add(yearComboBox);
        mf.add(statusBox);

        connection = getMySQLConnection();
        try {
            String query = "SELECT * FROM Attendance";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            table.setModel(buildTableModel(rs));

        } catch (Exception a) {
            a.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            mf.setVisible(false);
            new Admin();
            // Redirect to the main menu or previous screen
        }
        if (e.getSource() == saveButton) {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the particulars");
            } else {
                try {
                    String query = "UPDATE Attendance SET Name=?, Date=?, Status=? WHERE ID=?";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1, nameField.getText());

                    // Convert the date to a suitable format
                    String selectedDay = (String) dayComboBox.getSelectedItem();
                    String selectedMonth = (String) monthComboBox.getSelectedItem();
                    String selectedYear = (String) yearComboBox.getSelectedItem();
                    String formattedDate = selectedYear + "-" + selectedMonth + "-" + selectedDay;

                    pst.setString(2, formattedDate);
                    pst.setString(3, (String) statusBox.getSelectedItem());
                    pst.setString(4, idField.getText());

                    pst.executeUpdate();

                    String query2 = "SELECT * FROM Attendance";
                    PreparedStatement pst2 = connection.prepareStatement(query2);
                    ResultSet rs = pst2.executeQuery();
                    table.setModel(buildTableModel(rs));

                    JOptionPane.showMessageDialog(null, "Attendance record modified successfully!");
                } catch (Exception g) {
                    JOptionPane.showMessageDialog(null, "Failed to modify data");
                    g.printStackTrace();
                }
            }
        }
    }

    private Connection getMySQLConnection() {
        String url = "jdbc:mysql://localhost:3306/attendance"; 
        String username = "root";
        String password = "Happy@123";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private DefaultTableModel buildTableModel(ResultSet rs) {
        try {
            return ResultSetTableModel.buildTableModel(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String[] getDays() {
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.format("%02d", i);
        }
        return days;
    }

    private String[] getMonths() {
        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) {
            months[i - 1] = String.format("%02d", i);
        }
        return months;
    }

    private String[] getYears() {
        String[] years = new String[10]; // Assuming you want to show 10 years
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear + i);
        }
        return years;
    }

    public static void main(String[] args) {
        new Modify();
    }
}
