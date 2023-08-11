package StoreManagementSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class loginPage extends JFrame{
    private JButton btnLogin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPanel mainPanel;
    private JButton btnChangePassword;
    public String username = "ADMIN", password = "";
    Connection con = null;
    Statement sql = null;
    ResultSet rs = null;
    public loginPage(){
        this.setTitle("Store Management System");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 250);
        this.add(mainPanel);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        try{
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/storemngmsys?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "root", "lizismee");
            sql = con.createStatement();
            String s = "select password from accounts where username = 'ADMIN'";
            rs = sql.executeQuery(s);
            while(rs.next()){
                password = rs.getString("password");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = txtUsername.getText(), pass = String.valueOf(txtPassword.getPassword());
                if(!user.equals(username) || !pass.equals(password)){
                    JOptionPane.showMessageDialog(null, "Wrong username or password!");
                }else{
                    JOptionPane.showMessageDialog(null, "Login successfully!");
                    setvisible();
                    new trangChu();
                }
            }
        });
        btnChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new changePass();
                    }
                });
            }
        });
    }

    private void setvisible() {
        this.setVisible(false);
    }
}
