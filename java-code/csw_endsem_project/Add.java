package csw_endsem_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import net.proteanit.sql.DbUtils;

public class Add implements ActionListener {
    Connection connection = null;
    JFrame adf = new JFrame();
    ImageIcon icon = new ImageIcon("img/add.png");
    JButton backButton = new JButton("< BACK");
    JButton saveButton = new JButton("SAVE");
    JLabel mainLabel = new JLabel("ADD ATTENDANCE TO THE DATABASE");
    JLabel currentLabel = new JLabel("CURRENT ATTENDANCE");
    JLabel newEntryLabel = new JLabel("ADD NEW ENTRY");
    JLabel idLabel = new JLabel("ID:");
    JLabel dateLabel = new JLabel("Date:");
    JLabel nameLabel = new JLabel("Name:");
    JLabel statusLabel = new JLabel("Status:");
    JTextField idField = new JTextField();
    
    JComboBox<String> dayComboBox = new JComboBox<>(getDays());
    JComboBox<String> monthComboBox = new JComboBox<>(getMonths());
    JComboBox<String> yearComboBox = new JComboBox<>(getYears());
    
    JTextField nameField = new JTextField();
    String statusArr[] = {"Present", "Absent"};
    JComboBox<String> statusBox = new JComboBox<>(statusArr);
    JScrollPane scrollPane = new JScrollPane();
    JTable table = new JTable();
    JSeparator lineSep = new JSeparator();

    Add() {
        adf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adf.setUndecorated(true);
        adf.setResizable(false);
        adf.setSize(1300, 560);
        adf.setVisible(true);
        adf.setLocationRelativeTo(null);
        adf.setTitle("ADD ATTENDANCE");
        adf.getContentPane().setBackground(new Color(0x1d1d1d));
        adf.setLayout(null);
        adf.setIconImage(icon.getImage());

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

        mainLabel.setBounds(310, 27, 590, 40);
        mainLabel.setFont(new Font("Calibri", Font.PLAIN, 36));
        mainLabel.setForeground(new Color(0xEEEDE7));

        currentLabel.setBounds(720, 127, 370, 31);
        currentLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        currentLabel.setForeground(new Color(0xEEEDE7));

        newEntryLabel.setBounds(196, 127, 270, 31);
        newEntryLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        newEntryLabel.setForeground(new Color(0xEEEDE7));

        idLabel.setBounds(100, 200, 30, 21);
        idLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
        idLabel.setForeground(new Color(0xEEEDE7));

        dateLabel.setBounds(100, 240, 90, 21);
        dateLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
        dateLabel.setForeground(new Color(0xEEEDE7));

        nameLabel.setBounds(100, 280, 90, 21);
        nameLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
        nameLabel.setForeground(new Color(0xEEEDE7));

        statusLabel.setBounds(100, 320, 99, 21);
        statusLabel.setFont(new Font("Calibri", Font.PLAIN, 21));
        statusLabel.setForeground(new Color(0xEEEDE7));

        idField.setBounds(277, 195, 200, 21);
        idField.setFont(new Font("Calibri", Font.PLAIN, 21));
        idField.setBorder(null);

        dayComboBox.setBounds(277, 235, 60, 21);
        monthComboBox.setBounds(347, 235, 50, 21);
        yearComboBox.setBounds(410, 235, 70, 21);

        nameField.setBounds(277, 275, 200, 21);
        nameField.setFont(new Font("Calibri", Font.PLAIN, 21));
        nameField.setBorder(null);

        statusBox.setBounds(277, 315, 200, 21);

        table.setBackground(new Color(0x121212));
        table.setForeground(new Color(0xEEEDE7));
        scrollPane.setBounds(520, 180, 650, 330);
        scrollPane.getViewport().setBackground(new Color(0x404040));
        scrollPane.setViewportView(table);

        lineSep.setOrientation(SwingConstants.VERTICAL);
        lineSep.setBounds(500, 115, 120, 420);

        adf.add(lineSep);
        adf.add(scrollPane);
        adf.add(mainLabel);
        adf.add(currentLabel);
        adf.add(newEntryLabel);
        adf.add(backButton);
        adf.add(saveButton);
        adf.add(idLabel);
        adf.add(dateLabel);
        adf.add(nameLabel);
        adf.add(statusLabel);
        adf.add(idField);
        adf.add(dayComboBox);
        adf.add(monthComboBox);
        adf.add(yearComboBox);
        adf.add(nameField);
        adf.add(statusBox);

        connection = MySqlConnection.dbConnector();

        try {
            String query = "select * from Attendance";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception a) {
            a.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            adf.setVisible(false);
            new Admin();
        }
        if (e.getSource() == saveButton) {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the particulars");
            } else {
                try {
                    String selectedDay = (String) dayComboBox.getSelectedItem();
                    String selectedMonth = (String) monthComboBox.getSelectedItem();
                    String selectedYear = (String) yearComboBox.getSelectedItem();
                    String formattedDate = selectedYear + "-" + selectedMonth + "-" + selectedDay;

                    String query = "INSERT INTO Attendance(ID, Name, Date, Status) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1, idField.getText());
                    ps.setString(2, nameField.getText());
                    ps.setString(3, formattedDate);
                    ps.setString(4, (String) statusBox.getSelectedItem());

                    ps.execute();
                    ps.close();

                    String query2 = "select * from Attendance";
                    PreparedStatement pst = connection.prepareStatement(query2);
                    ResultSet rs = pst.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    JOptionPane.showMessageDialog(null, "Attendance Saved Successfully!");
                } catch (Exception a) {
                    a.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error saving attendance.");
                }
            }
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
        String[] years = new String[10];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear + i);
        }
        return years;
    }

    public static void main(String[] args) {
        new Add();
    }
}
