    package csw_endsem_project;

    import java.awt.Color;
    import java.awt.Font;
    import java.sql.Connection;

    import javax.swing.ImageIcon;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JProgressBar;

    public class Splashscreen {
        // Init of required components
        JFrame sf = new JFrame();
        JLabel createdby = new JLabel("CREATED BY: ");
        JLabel name = new JLabel("RAJ MEHTA");
        JLabel imagelabel = new JLabel();
        ImageIcon dbimageicon = new ImageIcon("img/newimage.png");
        JLabel Label = new JLabel("Attendance Management System");
        JProgressBar bar = new JProgressBar();
        ImageIcon icon = new ImageIcon("img/logo.png");

        Splashscreen() {
            sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sf.setUndecorated(true);
            sf.setSize(650, 400);
            sf.setTitle("Municipal DBMS");
            sf.setVisible(true);
            sf.setResizable(false);
            sf.setLocationRelativeTo(null);
            sf.setLayout(null);
            sf.getContentPane().setBackground(new Color(0x1d1d1d));
            sf.setIconImage(icon.getImage());

            name.setBounds(501, 300, 120, 120);
            createdby.setBounds(501, 285, 120, 120);
            imagelabel.setBounds(133, 16, 400, 300);
            Label.setBounds(15, 0, 700, 100);
            bar.setBounds(77, 285, 450, 18);

            name.setFont(new Font("Calibri", Font.PLAIN, 16));
            createdby.setFont(new Font("Calibri", Font.PLAIN, 16));
            Label.setFont(new Font("Calibri", Font.PLAIN, 45));
            Label.setForeground(new Color(0xEEEDE7));
            createdby.setForeground(Color.WHITE);
            name.setForeground(Color.WHITE);

            sf.add(name);
            sf.add(createdby);

            sf.add(Label);
            sf.add(imagelabel);
            imagelabel.setIcon(dbimageicon);

            sf.add(bar);
            bar.setBorder(null);
            bar.setBackground(new Color(0x023047));
            bar.setForeground(new Color(0xEEEDE7));
            bar.setBorderPainted(false);

            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(54);
                    bar.setValue(i);
                    if (i == 100) {
                        sf.setVisible(false);
                        new Login(null);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Splashscreen(Connection connection) {
        }
    }
