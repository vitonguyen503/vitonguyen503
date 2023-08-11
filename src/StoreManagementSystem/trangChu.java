package StoreManagementSystem;

import javax.swing.*;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Enumeration;

public class trangChu extends JFrame {
    private JTabbedPane tabbedPane;

    private JPanel mainPanel;
    private JTable tblBestSeller;
    private JTextField txtPrice;
    private JTextField txtAmt;
    private JTextField txtName;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JComboBox cbboxCategories;
    private JTable tblItems;
    private JTable tblSearchedItems;
    private JButton btnSearch;
    private JButton btnPrint;
    private JTextField txtSearch;
    private JTextField txtAmount;
    private JButton btnAddToCart;
    private JLabel lbSales;
    private JTable tblSelectedItems;
    private JButton btnDeleteFromCart;
    private JButton btnReset;

    Connection con = null;
    Statement stm = null;
    ResultSet rs = null;

    private void setTblBestSeller(){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        model.addColumn("Rank");
        model.addColumn("Product Name");
        model.addColumn("Sold Amount");
        try{
            String s = "SELECT name, amount " +
                    "FROM sold_items " +
                    "ORDER BY amount DESC " +
                    "LIMIT 5;";
            rs = stm.executeQuery(s);
            int id = 0;
            while(rs.next()){
                String name = rs.getString("name");
                int amount = rs.getInt("amount");
                model.addRow(new Object[]{++id, name, amount});
            }


        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        tblBestSeller.setModel(model);
    }

    private void setTblItems(){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        model.addColumn("No");
        model.addColumn("Name");
        model.addColumn("Categories");
        model.addColumn("In Stock");
        model.addColumn("Price");
        int id = 1;
        String s = "select * from items";
        try {
            rs = stm.executeQuery(s);
            while(rs.next()){
                String name = rs.getString("name"), cate = rs.getString("categories");
                int inStock = rs.getInt("amount");
                double price = rs.getDouble("price");
                model.addRow(new Object[]{id++, name, cate, inStock, price});
            }
            tblItems.setModel(model);
            // set do rong cot name cua bang items min 200;
            TableColumnModel column = tblItems.getColumnModel();
            column.getColumn(1).setMinWidth(200);
            // can cot con lai ra chinh giua
            DefaultTableCellRenderer center = new DefaultTableCellRenderer();
            center.setHorizontalAlignment(JLabel.CENTER);
            //column.getColumn(0).setCellRenderer(center);
            column.getColumn(2).setCellRenderer(center);
            column.getColumn(3).setCellRenderer(center);
            column.getColumn(4).setCellRenderer(center);
            for(int i = 2; i <= 4; i++){
                TableColumn co = column.getColumn(i);
                co.setHeaderRenderer(new DefaultTableCellRenderer(){
                    @Override
                    public void setHorizontalAlignment(int alignment) {
                        super.setHorizontalAlignment(JLabel.CENTER);
                    }
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTblSearchedItems(){
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        model.addColumn("No");
        model.addColumn("Name");
        model.addColumn("Categories");
        model.addColumn("In Stock");
        model.addColumn("Price");
        int id = 1;
        String s = "select * from items";
        try {
            rs = stm.executeQuery(s);
            while(rs.next()){
                String name = rs.getString("name"), cate = rs.getString("categories");
                int inStock = rs.getInt("amount");
                double price = rs.getDouble("price");
                model.addRow(new Object[]{id++, name, cate, inStock, price});
            }
            tblSearchedItems.setModel(model);
            // set do rong cot name cua bang search min 200;
            TableColumnModel column = tblSearchedItems.getColumnModel();
            column.getColumn(1).setMinWidth(50);
            column.getColumn(0).setMaxWidth(25);
            column.getColumn(3).setMaxWidth(50);
            column.getColumn(4).setMaxWidth(35);
            // can cot con lai ra chinh giua
            DefaultTableCellRenderer center = new DefaultTableCellRenderer();
            center.setHorizontalAlignment(JLabel.CENTER);
            //column.getColumn(0).setCellRenderer(center);
            column.getColumn(2).setCellRenderer(center);
            column.getColumn(3).setCellRenderer(center);
            column.getColumn(4).setCellRenderer(center);
            for(int i = 2; i <= 4; i++){
                TableColumn co = column.getColumn(i);
                co.setHeaderRenderer(new DefaultTableCellRenderer(){
                    @Override
                    public void setHorizontalAlignment(int alignment) {
                        super.setHorizontalAlignment(JLabel.CENTER);
                    }
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setlbSales(){
        double sales = 0;
        try{
            String s = "SELECT amount, price " +
                    "FROM sold_items";
            rs = stm.executeQuery(s);
            while(rs.next()){
                double price = rs.getDouble("price");
                int amount = rs.getInt("amount");
                sales += price * amount;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        lbSales.setText(Double.toString(sales));
    }
    private void setDashboard(){
        setTblBestSeller();
        setlbSales();
    }

    private void setItems(){
        cbboxCategories.addItem("Vegetable");
        cbboxCategories.addItem("Fruit");
        cbboxCategories.addItem("Root");
        setTblItems();
    }

    private void setPayment(){
        setTblSearchedItems();
    }
    public trangChu(){
        super("Store Management System");
        try{
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/storemngmsys?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    "root", "lizismee");
            stm = con.createStatement();
        } catch(Exception e){
            e.printStackTrace();
        }
        setDashboard();
        setItems();
        setPayment();
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 380);
        setResizable(false);
        setVisible(true);

        tblSearchedItems.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblSearchedItems.getSelectedRow();
                    if(selectedRow >= 0){
                        String name = tblSearchedItems.getValueAt(selectedRow, 1).toString();
                        //String amountStr = tblSearchedItems.getValueAt(selectedRow, 1).toString();
                        txtAmount.setText("1");
                        txtSearch.setText(name);
                    }

                }
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = "";
                if(txtSearch.getText() != null){
                    keyword = txtSearch.getText();
                }
                String s = "SELECT * FROM items WHERE name like '" + keyword + "%' and amount > 0;";
                try {
                    rs = stm.executeQuery(s);
                    DefaultTableModel model = new DefaultTableModel(){
                        @Override
                        public boolean isCellEditable(int row, int column){
                            return false;
                        }
                    };
                    model.addColumn("No");
                    model.addColumn("Name");
                    model.addColumn("Categories");
                    model.addColumn("In Stock");
                    model.addColumn("Price");
                    int id = 1;
                    while(rs.next()){
                        String name = rs.getString("name"), cate = rs.getString("categories");
                        int inStock = rs.getInt("amount");
                        double price = rs.getDouble("price");
                        model.addRow(new Object[]{id++, name, cate, inStock, price});
                    }
                    tblSearchedItems.setModel(model);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnAddToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtSearch.getText();
                int amount = Integer.parseInt(txtAmount.getText());
                int selectedRow = tblSearchedItems.getSelectedRow();
                if(selectedRow >= 0){
                    int inStock = Integer.parseInt(tblSearchedItems.getValueAt(selectedRow, 3).toString());
                    double price = Double.parseDouble(tblSearchedItems.getValueAt(selectedRow, 4).toString());
                    try {
                        if(amount > inStock){
                            JOptionPane.showMessageDialog(null, "Out of stock!");
                            txtAmount.setText("1");
                            return;
                        }
                        if(amount <= 0){
                            JOptionPane.showMessageDialog(null, "Invalid amount!");
                            txtAmount.setText("1");
                            return;
                        }
                        String s = "select * from selected_items where name ='" + name + "';";
                        rs = stm.executeQuery(s);
                        if(!rs.next()){
                            s = "insert into selected_items (name, amount, price) values ('" + name + "', " + amount + ", " + amount * price + ");";
                            stm.executeUpdate(s);
                        }else{
                            amount += rs.getInt("amount");
                            if(amount > inStock){
                                JOptionPane.showMessageDialog(null, "Out of stock!");
                                txtAmount.setText("1");
                                return;
                            }
                            double tmp = amount * price;
                            s = "update selected_items set amount = " + amount + ", price = " + tmp + "where name = '" + name + "';";
                            stm.executeUpdate(s);
                        }
                        s = "select * from selected_items";
                        rs = stm.executeQuery(s);
                        DefaultTableModel model = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int row, int column){
                                return false;
                            }
                        };
                        model.addColumn("No");
                        model.addColumn("Name");
                        model.addColumn("Amount");
                        model.addColumn("Price");
                        int id = 1;
                        double total = 0;
                        while(rs.next()){
                            total += rs.getDouble("price");
                            model.addRow(new Object[]{id++, rs.getString("name"), rs.getInt("amount"), rs.getDouble("price")});
                        }
                        DecimalFormat df = new DecimalFormat("#.##");
                        model.addRow(new Object[]{"", "", "Total:", df.format(total)});
                        tblSelectedItems.setModel(model);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        btnDeleteFromCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblSelectedItems.getSelectedRow();
                if(selectedRow >= 0){
                    try{
                        String name = tblSelectedItems.getValueAt(selectedRow, 1).toString();
                        String s = "delete from selected_items where name = '" + name + "';";
                        stm.executeUpdate(s);
                        s = "select * from selected_items";
                        rs = stm.executeQuery(s);
                        DefaultTableModel model = new DefaultTableModel(){
                            @Override
                            public boolean isCellEditable(int row, int column){
                                return false;
                            }
                        };
                        model.addColumn("No");
                        model.addColumn("Name");
                        model.addColumn("Amount");
                        model.addColumn("Price");
                        int id = 1;
                        double total = 0;
                        while(rs.next()){
                            total += rs.getDouble("price");
                            model.addRow(new Object[]{id++, rs.getString("name"), rs.getInt("amount"), rs.getDouble("price")});
                        }
                        DecimalFormat df = new DecimalFormat("#.##");
                        model.addRow(new Object[]{"", "", "Total:", df.format(total)});
                        tblSelectedItems.setModel(model);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Click on a row to delete!");
                }
            }
        });

        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Neu khong co san pham nao duoc chon thi khong duoc in hoa don
                if(tblSelectedItems.getRowCount() <= 1){
                    JOptionPane.showMessageDialog(null, "No items have been selected!");
                    return;
                }
                // In du lieu ra file
                try {
                    FileWriter writer = new FileWriter("HoaDon.txt");
                    int rowCount = tblSelectedItems.getRowCount();
                    int colCount = tblSelectedItems.getColumnCount();
                    //writer.write();
                    // Ghi tiêu đề cột
                    for (int i = 0; i < colCount; i++) {
                        writer.write(String.format("%-15s", tblSelectedItems.getColumnName(i)));
                    }
                    writer.write("\n");

                    // Ghi dữ liệu hàng
                    for (int i = 0; i < rowCount; i++) {
                        for (int j = 0; j < colCount; j++) {
                            writer.write(String.format(String.format("%-15s", tblSelectedItems.getValueAt(i, j).toString())));
                        }
                        writer.write("\n");
                    }
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Bill has been written to file \"Hoadon.txt\"!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Thay doi du lieu trong database: bang sold_items va items
                try{
                    int rowCount = tblSelectedItems.getRowCount();
                    for (int i = 0; i < rowCount - 1; i++) {
                        String name = tblSelectedItems.getValueAt(i, 1).toString();
                        int soldAmount = Integer.parseInt(tblSelectedItems.getValueAt(i, 2).toString());
                        double price = Double.parseDouble(tblSelectedItems.getValueAt(i, 3).toString()) / soldAmount;
                        String s = "select amount from items where name = '" + name + "';";
                        rs = stm.executeQuery(s);
                        rs.next();
                        int amountLeft = rs.getInt("amount") - soldAmount;
                        s = "update items set amount = " + amountLeft + " where name = '" + name + "';";
                        stm.executeUpdate(s);
                        // Them vao bang sold_items
                        s = "select * from sold_items where name = '" + name + "';";
                        rs = stm.executeQuery(s);
                        if(!rs.next()){
                            s = "insert into sold_items (name, amount, price)" +
                                    "values ('" + name + "', " + soldAmount + ", " + price + ");";
                        }else{
                            int tmp = rs.getInt("amount");
                            tmp += soldAmount;
                            s = "update sold_items set amount = " + tmp + " where name = '" + name + "';";
                        }
                        stm.executeUpdate(s);
                        // Xoa bang selected_items
                        s = "delete from selected_items";
                        stm.executeUpdate(s);
                    }
                    // Dat lai cac bang
                    tblSearchedItems.setModel(new DefaultTableModel());
                    tblSelectedItems.setModel(new DefaultTableModel());
                    setTblBestSeller();
                    setlbSales();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        tblItems.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblItems.getSelectedRow();
                    if(selectedRow >= 0){
                        String name = tblItems.getValueAt(selectedRow, 1).toString();
                        String amount = tblItems.getValueAt(selectedRow, 3).toString();
                        String price = tblItems.getValueAt(selectedRow, 4).toString();
                        String categories = tblItems.getValueAt(selectedRow,2).toString();
                        txtName.setText(name);
                        txtAmt.setText(amount);
                        txtPrice.setText(price);
                        cbboxCategories.setSelectedItem(categories);
                    }

                }
            }
        });

        // Phan items
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtName.setText("");
                txtAmt.setText("");
                txtPrice.setText("");
                cbboxCategories.setSelectedIndex(-1);
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText(), amount = txtAmt.getText(), categories = "";
                if(cbboxCategories.getSelectedIndex() >= 0) categories = cbboxCategories.getSelectedItem().toString();
                if(name.isEmpty() || txtAmt.getText().isEmpty() || txtPrice.getText().isEmpty() || cbboxCategories.getSelectedIndex() < 0){
                    JOptionPane.showMessageDialog(null, "Missing information!");
                    return;
                }
                int amt;
                double price;
                try{
                    amt = Integer.parseInt(txtAmt.getText());
                    price = Double.parseDouble(txtPrice.getText());
                } catch (NumberFormatException n){
                    JOptionPane.showMessageDialog(null, "Wrong amount or price format!");
                    return;
                }
                // Them vao database
                try{
                    String s = "select * from items where name = '" + name + "';";
                    rs = stm.executeQuery(s);
                    if(!rs.next()){
                        s = "insert into items (name, amount, price, categories) " +
                                "values ('" + name + "', " + amount + ", " + price + ", '" + categories + "');";
                    }else{
                        int inStock = rs.getInt("amount");
                        inStock += amt;
                        s = "update items set amount = " + inStock + " where name = '" + name + "';";
                    }
                    stm.executeUpdate(s);
                    JOptionPane.showMessageDialog(null, "Item added!");
                    txtName.setText(""); txtAmt.setText(""); txtPrice.setText(""); cbboxCategories.setSelectedIndex(-1);
                    setTblItems();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText(), amount = txtAmt.getText(), categories = "";
                if(cbboxCategories.getSelectedIndex() >= 0) categories = cbboxCategories.getSelectedItem().toString();
                if(name.isEmpty() || txtAmt.getText().isEmpty() || txtPrice.getText().isEmpty() || cbboxCategories.getSelectedIndex() < 0){
                    JOptionPane.showMessageDialog(null, "Missing information!");
                    return;
                }
                int amt;
                double price;
                try{
                    amt = Integer.parseInt(txtAmt.getText());
                    price = Double.parseDouble(txtPrice.getText());
                } catch (NumberFormatException n){
                    JOptionPane.showMessageDialog(null, "Wrong amount or price format!");
                    return;
                }
                // Sua thong tin items
                try{
                    String s = "update items set name = '" + name + "', amount = " + amt + ", price = " + price + ", categories = '" + categories + "' where name = '" + name + "';";
                    stm.executeUpdate(s);
                    JOptionPane.showMessageDialog(null, "Item editted!");
                    setTblItems();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tblItems.getSelectedRow() >= 0){
                    try{
                        int selectedRow = tblItems.getSelectedRow();
                        String name = tblItems.getValueAt(selectedRow, 1).toString();
                        String s = "delete from items where name = '" + name + "';";
                        stm.executeUpdate(s);
                        JOptionPane.showMessageDialog(null, "Item deleted!");
                        setTblItems();
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Select on a row to delete!");
                    return;
                }
            }
        });
    }
}
