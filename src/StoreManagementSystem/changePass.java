package StoreManagementSystem;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class changePass extends JFrame{
    private JFrame mainFrm;
    private JTextField txtOldPass;
    private JTextField txtNewPass;
    private JTextField txtConfirm;
    private JButton btnCancel;
    private JButton btnOK;
    private JPanel mainPanel;
    private JPasswordField passOld;
    private JPasswordField passNew;
    private JPasswordField passConfirm;
    JDialog dialog = new JDialog();

    String password = "";
    Connection con = null;
    Statement sql = null;
    ResultSet rs = null;
    public changePass(){
        this.setTitle("Change Password");
        this.setResizable(false);
        mainPanel.setSize(600, 300);
        dialog.setContentPane(mainPanel);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
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

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldpass = String.valueOf(passOld.getPassword()), newpass = String.valueOf(passNew.getPassword()), confirm = String.valueOf(passConfirm.getPassword());
                if(oldpass.isEmpty() || newpass.isEmpty() || confirm.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Missing information!");
                    return;
                }
                if(!oldpass.equals(password)){
                    JOptionPane.showMessageDialog(null, "Wrong password!");
                    passOld.grabFocus();
                    return;
                }
                if(!newpass.equals(confirm)){
                    JOptionPane.showMessageDialog(null, "New password doesn't match!");
                    passNew.grabFocus();
                    return;
                }
                password = newpass;
                String s = "update accounts set password = '" + password + "' where username = 'ADMIN'";
                try {
                    sql.executeUpdate(s);
                    rs.close();
                    sql.close();
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "Update successfully!");
                dialog.dispose();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }
}
