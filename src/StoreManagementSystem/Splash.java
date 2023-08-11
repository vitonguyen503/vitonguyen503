package StoreManagementSystem;

import javax.swing.*;

public class Splash extends JFrame{
    private JPanel panel1;

    public Splash(String title){
        super(title);
        this.setResizable(false);
        this.setContentPane(panel1);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        try{
            Thread.sleep(2500);
        } catch (Exception e){
            e.printStackTrace();
        }
        this.setVisible(false);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new loginPage();
            }
        });
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Splash("Store Management System");
    }
}
