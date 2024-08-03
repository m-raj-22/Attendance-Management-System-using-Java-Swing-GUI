package csw_endsem_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

public class LoadDBAdmin implements ActionListener {
    Connection connection = null;
    JFrame af = new JFrame();
    ImageIcon icon = new ImageIcon("img/logo.png");
    JButton exitButton = new JButton("EXIT");
    JButton backButton = new JButton("< BACK");
    JButton loadButton = new JButton("LOAD ATTENDANCE");
    JButton exportButton = new JButton("EXPORT ATTENDANCE");
    JScrollPane scrollPane = new JScrollPane();
    JTable table = new JTable();
    JLabel showLabel = new JLabel();
    ImageIcon showImage = new ImageIcon("img/show.png");
    JLabel exportLabel = new JLabel();
    ImageIcon exportImage = new ImageIcon("img/export.png");

    LoadDBAdmin(Connection connection) {
        af.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        af.setUndecorated(true);
        af.setResizable(false);
        af.setSize(700, 500);
        af.setVisible(true);
        af.setLocationRelativeTo(null);
        af.setTitle("Load DB(Teacher)");
        af.getContentPane().setBackground(new Color(0x1d1d1d));
        af.setLayout(null);
        af.setIconImage(icon.getImage());

        exitButton.setBounds(590, 450, 90, 36);
        exitButton.setBackground(new Color(0x1d1b1b));
        exitButton.setFocusable(false);
        exitButton.setFont(new Font("Calibri", Font.PLAIN, 19));
        exitButton.setForeground(new Color(0xEEEDE7));
        exitButton.addActionListener(this);

        backButton.setBounds(20, 450, 110, 36);
        backButton.setBackground(new Color(0x1d1b1b));
        backButton.setFocusable(false);
        backButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        backButton.setForeground(new Color(0xEEEDE7));
        backButton.addActionListener(this);

        loadButton.setBounds(20, 20, 200, 45);
        loadButton.setBackground(new Color(0x1d1b1b));
        loadButton.setFocusable(false);
        loadButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        loadButton.setForeground(new Color(0xEEEDE7));
        loadButton.addActionListener(this);

        exportButton.setBounds(400, 20, 230, 45);
        exportButton.setBackground(new Color(0x1d1b1b));
        exportButton.setFocusable(false);
        exportButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        exportButton.setForeground(new Color(0xEEEDE7));
        exportButton.addActionListener(this);

        table.setBackground(new Color(0x121212));
        table.setForeground(new Color(0xEEEDE7));
        scrollPane.setBounds(20, 90, 659, 350);
        scrollPane.getViewport().setBackground(new Color(0x404040));
        scrollPane.setViewportView(table);

        showLabel.setBounds(227, 27, 32, 32);
        showLabel.setIcon(showImage);
        exportLabel.setBounds(650, 27, 32, 32);
        exportLabel.setIcon(exportImage);

        af.add(showLabel);
        af.add(exitButton);
        af.add(backButton);
        af.add(loadButton);
        af.add(scrollPane);
        af.add(showLabel);
        af.add(exportLabel);
        af.add(exportButton);

        this.connection = connection;

        // JOptionPane.showMessageDialog(null, "Click LOAD ATTENDANCE to view the ATTENDANCE");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            System.exit(0);
        }
        if (e.getSource() == backButton) {
            af.setVisible(false);
            new Admin(); // Assuming Teacher is the previous screen for teachers
        }
        if (e.getSource() == loadButton) {
            try {
                String query = "SELECT * FROM Attendance";
                PreparedStatement pst = connection.prepareStatement(query);
                ResultSet rs = pst.executeQuery();

                table.setModel(DbUtils.resultSetToTableModel(rs));

            } catch (Exception a) {
                a.printStackTrace();
            }
        }
        if (e.getSource() == exportButton) {
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "LOAD DATABASE FIRST!");
            } else {
                String osname = System.getProperty("os.name");
                String username = System.getProperty("user.name");
                if (osname.charAt(0) == 'W') {
                    try {
                        BufferedWriter bw = new BufferedWriter(
                                new FileWriter("C:\\Users\\" + username + "\\Desktop\\output.txt"));
                        bw.write(
                                "--------------------------------------------------------------------------------------------------------------\n");
                        bw.write("ID     |     " + "Name          |" + "        Date       |" + "        Status       \n");
                        bw.write(
                                "--------------------------------------------------------------------------------------------------------------\n");

                        for (int i = 0; i < table.getRowCount(); i++) {
                            String id = (table.getValueAt(i, 0).toString());
                            String name = (table.getValueAt(i, 1).toString());
                            String date = (table.getValueAt(i, 2).toString());
                            String status = (table.getValueAt(i, 3).toString());
                            bw.write(id + "   " + name + "           " + date + "          " + status + "\n");
                        }
                        JOptionPane.showMessageDialog(null, "SUCCESSFULLY EXPORTED DATA TO DESKTOP!");
                        bw.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (osname.charAt(0) == 'L') {
                    try {
                        BufferedWriter bw = new BufferedWriter(
                                new FileWriter("/home/" + username + "/Desktop/output.txt"));
                        bw.write(
                                "--------------------------------------------------------------------------------------------------------------\n");
                        bw.write("ID     |     " + "Name          |" + "        Date       |" + "        Status       \n");
                        bw.write(
                                "--------------------------------------------------------------------------------------------------------------\n");

                        for (int i = 0; i < table.getRowCount(); i++) {
                            String id = (table.getValueAt(i, 0).toString());
                            String name = (table.getValueAt(i, 1).toString());
                            String date = (table.getValueAt(i, 2).toString());
                            String status = (table.getValueAt(i, 3).toString());
                            bw.write(id + "   " + name + "           " + date + "          " + status + "\n");
                        }
                        JOptionPane.showMessageDialog(null, "SUCCESSFULLY EXPORTED DATA TO DESKTOP!");
                        bw.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Cant export data on MacOS");
                }
            }
        }
    }
}
