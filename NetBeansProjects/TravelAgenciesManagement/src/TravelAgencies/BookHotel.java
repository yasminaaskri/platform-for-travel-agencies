package TravelAgencies;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class BookHotel extends JFrame {
    private JPanel contentPane;
    JTextField t1, t2;
    Choice c1, c2;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BookHotel frame = new BookHotel("");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BookHotel(String username) {
        setBounds(420, 220, 1100, 600);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/book.jpg"));
        Image i3 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i3);
        JLabel la1 = new JLabel(i2);
        la1.setBounds(450, 100, 700, 300);
        add(la1);

        JLabel lblName = new JLabel("BOOK HOTEL");
        lblName.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
        lblName.setBounds(118, 11, 300, 53);
        contentPane.add(lblName);

        JLabel la2 = new JLabel("Username :");
        la2.setBounds(35, 70, 200, 14);
        contentPane.add(la2);

        JLabel l1 = new JLabel(username);
        l1.setBounds(271, 70, 200, 14);
        contentPane.add(l1);

        JLabel lblId = new JLabel("Select Hotel :");
        lblId.setBounds(35, 110, 200, 14);
        contentPane.add(lblId);

        c1 = new Choice();
        Conn c = new Conn();
        try {
            ResultSet rs = c.s.executeQuery("SELECT * FROM hotel");
            while (rs.next()) {
                c1.add(rs.getString("name"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        c1.setBounds(271, 110, 150, 30);
        add(c1);

        JLabel la3 = new JLabel("Total Persons");
        la3.setBounds(35, 150, 200, 14);
        contentPane.add(la3);

        t1 = new JTextField();
        t1.setText("0");
        t1.setBounds(271, 150, 150, 20);
        contentPane.add(t1);
        t1.setColumns(10);

        JLabel la4 = new JLabel("Number of Days");
        la4.setBounds(35, 190, 200, 14);
        contentPane.add(la4);

        t2 = new JTextField();
        t2.setText("0");
        t2.setBounds(271, 190, 150, 20);
        contentPane.add(t2);
        t2.setColumns(10);

        JLabel la5 = new JLabel("Food Included :");
        la5.setBounds(35, 230, 200, 14);
        contentPane.add(la5);

        c2 = new Choice();
        c2.add("Yes");
        c2.add("No");
        c2.setBounds(271, 230, 150, 30);
        add(c2);

        JLabel lblDeposite = new JLabel("Total Price :");
        lblDeposite.setBounds(35, 430, 200, 14);
        contentPane.add(lblDeposite);

        JLabel l5 = new JLabel();
        l5.setBounds(271, 430, 200, 14);
        l5.setForeground(Color.RED);
        contentPane.add(l5);

        JButton b1 = new JButton("Check Price");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedHotel = c1.getSelectedItem();
                    String query = "SELECT * FROM hotel WHERE name = '" + selectedHotel + "'";
                    ResultSet rs = c.s.executeQuery(query);
                    if (rs.next()) {
                        int costPerDay = Integer.parseInt(rs.getString("costperperson"));
                        int foodCharges = Integer.parseInt(rs.getString("foodincluded"));

                        int persons = Integer.parseInt(t1.getText());
                        int days = Integer.parseInt(t2.getText());

                        String foodChoice = c2.getSelectedItem();

                        if (persons * days > 0) {
                            int total = costPerDay * persons * days;
                            if (foodChoice.equals("Yes")) {
                                total += foodCharges * days;
                            }
                            l5.setText("Rs " + total);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        b1.setBounds(50, 470, 120, 30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        contentPane.add(b1);

        JButton btnNewButton = new JButton("Book");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedHotel = c1.getSelectedItem();
                    String query = "INSERT INTO bookHotel (username, name, persons, days, food, price) VALUES ('"
                            + l1.getText() + "', '" + selectedHotel + "', '" + t1.getText() + "', '"
                            + t2.getText() + "', '" + c2.getSelectedItem() + "', '"
                            + l5.getText() + "')";
                    c.s.executeUpdate(query);

                    JOptionPane.showMessageDialog(null, "Hotel Booked Successfully");
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnNewButton.setBounds(200, 470, 120, 30);
        btnNewButton.setBackground(Color.BLACK);
        btnNewButton.setForeground(Color.WHITE);
        contentPane.add(btnNewButton);

        JButton btnExit = new JButton("Back");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        btnExit.setBounds(350, 470, 120, 30);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        contentPane.add(btnExit);

        getContentPane().setBackground(Color.WHITE);
    }
}
