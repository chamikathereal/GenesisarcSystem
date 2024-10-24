/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lk.genesisarcsystem.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.ui.FlatListCellBorder;
import com.mysql.cj.protocol.x.ReusableOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.servlet.jsp.tagext.TagAttributeInfo.ID;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import lk.genesisarcsystem.connection.MYSQL;

/**
 *
 * @author Asiri
 */
public class Areca_nuts_out extends javax.swing.JFrame {

    /**
     * Creates new form Areca_nuts_out
     */
    public Areca_nuts_out() {
        initComponents();
        generateID();
        loadRecord3();
        loadRecord(); 
        loadCategories();
        loadRecord1(); 
        generatepayID();
       
    }
    
    
    private void payAmmount(){
            try {
    // Get input dates from text fields
    String p_date_str = jTextField5.getText();
    String o_date_str = jTextField6.getText(); // End date

    // Parse input strings to java.sql.Date
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format as needed
    java.sql.Date p_date = null;
    java.sql.Date o_date = null;

    try {
        p_date = new java.sql.Date(dateFormat.parse(p_date_str).getTime());
        o_date = new java.sql.Date(dateFormat.parse(o_date_str).getTime());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
        return; // Exit the method on invalid date format
    }

    // Query to retrieve total quantity from the providing table within the specified date range
    ResultSet resultSet = MYSQL.execute("SELECT SUM(quantity) AS total_qty , SUM(price) AS total_price FROM providing WHERE date BETWEEN '" + p_date + "' AND '" + o_date + "'");
    

    // Check if resultSet has rows before accessing
    if (resultSet.next()) {
        double price = resultSet.getDouble("total_price");
        String totalPrice = String.valueOf(price);
        int totalQty = resultSet.getInt("total_qty");
        String totlqty = String.valueOf(totalQty);
        // Display the total quantity
        
        JOptionPane.showMessageDialog(this, "Total Price: " + totalPrice, "Total Price", JOptionPane.INFORMATION_MESSAGE);
        jTextField7.setText(totlqty);
        jLabel17.setText(String.valueOf(totalPrice));

    } else {
        System.out.println("No data found for the specified date range.");
        JOptionPane.showMessageDialog(this, "No data found for the specified date range.", "No Data", JOptionPane.WARNING_MESSAGE);
    }
} catch (Exception e) {
    e.printStackTrace(); // Print the exception for debugging
}
    }
    
private void getSelectedRowData() {
    // Ensure a row is selected before proceeding
    int selectedRow = jTable2.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Ensure the selected row index is valid within the table model bounds
    if (selectedRow >= 0 && selectedRow < jTable2.getRowCount()) {
        // Retrieve data from the selected row (ID from column 0)
        String id = jTable2.getValueAt(selectedRow, 0).toString(); 
        
        // Set the ID to the respective text field
        jTextField2.setText(id);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid row selection.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
private void getSelectedRowData1() {
    // Ensure a row is selected before proceeding
    int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Ensure the selected row index is valid within the table model bounds
    if (selectedRow >= 0 && selectedRow < jTable1.getRowCount()) {
        // Retrieve data from the selected row (ID from column 0)
        String id = jTable1.getValueAt(selectedRow, 0).toString(); 
        
        // Set the ID to the respective text field
        jTextField1.setText(id);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid row selection.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void getSelectedRowData2() {
    // Ensure a row is selected before proceeding
    int selectedRow = jTable3.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a row from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Ensure the selected row index is valid within the table model bounds
    if (selectedRow >= 0 && selectedRow < jTable3.getRowCount()) {
        // Retrieve data from the selected row (ID from column 0)
        String id = jTable3.getValueAt(selectedRow, 0).toString(); 
        
        // Set the ID to the respective text field
        jTextField4.setText(id);
    } else {
        JOptionPane.showMessageDialog(this, "Invalid row selection.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
private void generatepayID() {
            long id = System.currentTimeMillis();  // Get current time in milliseconds
            String idStr = String.valueOf(id);     // Convert to String
            String numericPart = idStr.substring(idStr.length() - 3);  // Get the last 3 characters of the ID

            // Generate 3 random uppercase letters
            Random random = new Random();
            StringBuilder letterPart = new StringBuilder(3);
            for (int i = 0; i < 6; i++) {
                char randomLetter = (char) ('A' + random.nextInt(26));  // Random letter between A-Z
                letterPart.append(randomLetter);
            }

            // Combine numeric and letter parts
            String invoiceNumber = letterPart.toString() + numericPart;

            jTextField4.setText(invoiceNumber);  // Set the value to the text field
        }
    
        private void generateID() {
            long id = System.currentTimeMillis();  // Get current time in milliseconds
            String idStr = String.valueOf(id);     // Convert to String
            String numericPart = idStr.substring(idStr.length() - 3);  // Get the last 3 characters of the ID

            // Generate 3 random uppercase letters
            Random random = new Random();
            StringBuilder letterPart = new StringBuilder(3);
            for (int i = 0; i < 3; i++) {
                char randomLetter = (char) ('A' + random.nextInt(26));  // Random letter between A-Z
                letterPart.append(randomLetter);
            }

            // Combine numeric and letter parts
            String invoiceNumber = letterPart.toString() + numericPart;

            jTextField1.setText(invoiceNumber);  // Set the value to the text field
        }
        
        // Load Categories into jComboBox1 (for categories)
public void loadCategories() {
    try {
        ResultSet resultSet = MYSQL.execute("SELECT id, name FROM category");

        Vector<String> categories = new Vector<>();
        categories.add("Select");  // Add a default option

        while (resultSet.next()) {
            String categoryID = resultSet.getString("id");
            String categoryName = resultSet.getString("name");
            categories.add(categoryID + " - " + categoryName);  // Add category ID and name
        }

        DefaultComboBoxModel<String> categoryModel = (DefaultComboBoxModel<String>) jComboBox1.getModel();
        categoryModel.removeAllElements();
        categoryModel.addAll(categories);
        jComboBox1.setSelectedIndex(0);  // Set default selection

        // Add ActionListener for category selection
        jComboBox1.addActionListener(e -> {
            if (jComboBox1.getSelectedIndex() > 0) {
                String selectedCategory = (String) jComboBox1.getSelectedItem();
                if (selectedCategory != null) {
                    String[] parts = selectedCategory.split(" - ");
                    if (parts.length > 0) {
                        String selectedCategoryID = parts[0];  // Extract category ID
                        loadBrandsForCategory(selectedCategoryID);  // Load related brands
                    }
                }
            }
        });

    } catch (Exception e) {
        e.printStackTrace();
    }
}

// Load Brands into jComboBox2 based on selected category
public void loadBrandsForCategory(String categoryID) {
    try {
        String query = "SELECT id, name FROM brand WHERE category_id = '" + categoryID + "'";
        ResultSet resultSet = MYSQL.execute(query);

        Vector<String> brands = new Vector<>();
        brands.add("Select");  // Add a default option

        while (resultSet.next()) {
            String brandID = resultSet.getString("id");
            String brandName = resultSet.getString("name");
            brands.add(brandID + " - " + brandName);  // Add brand ID and name
        }

        DefaultComboBoxModel<String> brandModel = (DefaultComboBoxModel<String>) jComboBox2.getModel();
        brandModel.removeAllElements();
        brandModel.addAll(brands);
        jComboBox2.setSelectedIndex(0);  // Set default selection

        // Add ActionListener for brand selection
        jComboBox2.addActionListener(e -> {
            if (jComboBox2.getSelectedIndex() > 0) {
                String selectedBrand = (String) jComboBox2.getSelectedItem();
                if (selectedBrand != null) {
                    String[] parts = selectedBrand.split(" - ");
                    if (parts.length > 0) {
                        String selectedBrandID = parts[0];  // Extract brand ID
                        String selectedCategory = (String) jComboBox1.getSelectedItem();
                        String[] categoryParts = selectedCategory.split(" - ");
                        if (categoryParts.length > 0) {
                            String selectedCategoryID = categoryParts[0];  // Get category ID
                            loadStockForBrandAndCategory(selectedCategoryID, selectedBrandID);  // Load stock
                        }
                    }
                }
            }
        });

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void loadStockForBrandAndCategory(String categoryID, String brandID) {
    try {
        // SQL query to fetch the stock items based on category and brand
        String query = "SELECT stock.id, product.name FROM stock "
                     + "INNER JOIN product ON stock.product_id = product.id "
                     + "WHERE product.category_id = '" + categoryID + "' "
                     + "AND product.brand_id = '" + brandID + "'";

        ResultSet resultSet = MYSQL.execute(query);

        Vector<String> stockItems = new Vector<>();
        stockItems.add("Select");  // Add default option

        while (resultSet.next()) {
            String stockID = resultSet.getString("id");
            String productName = resultSet.getString("name");
            stockItems.add(stockID + " - " + productName);  // Add stock ID and product name
        }

        DefaultComboBoxModel<String> stockModel = (DefaultComboBoxModel<String>) jComboBox3.getModel();
        stockModel.removeAllElements();  // Clear existing items
        stockModel.addAll(stockItems);   // Add new items

        // Ensure the combo box has items before setting the selected index
        if (stockModel.getSize() > 0) {
            jComboBox3.setSelectedIndex(0);  // Set default selection
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}



     private void loadRecord() {
        try {
            // Modify the SQL query to join with category and brand tables to get their names
            String query = "SELECT p.id, c.name AS category_name, b.name AS brand_name, p.quantity, p.date " +
                           "FROM providing p " +
                           "JOIN category c ON p.category_id = c.id " +
                           "JOIN brand b ON p.brand_id = b.id";

            ResultSet resultSet = MYSQL.execute(query);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);  // Clear existing rows

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("id"));  // Providing record ID
                vector.add(resultSet.getString("category_name"));  // Category name
                vector.add(resultSet.getString("brand_name"));  // Brand name
                vector.add(resultSet.getString("quantity"));  // Quantity
                vector.add(resultSet.getString("date"));  // Date
                model.addRow(vector);  // Add the row to the table
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRecord1() {
    try {
        // Modify the SQL query to join with category and brand tables to get their names
        String query = "SELECT p.id, c.name AS category_name, b.name AS brand_name, p.quantity, p.date, " +
                       "p.o_date, p.o_qty, p.price " +
                       "FROM providing p " +
                       "JOIN category c ON p.category_id = c.id " +
                       "JOIN brand b ON p.brand_id = b.id";

        ResultSet resultSet = MYSQL.execute(query);

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);  // Clear existing rows

        while (resultSet.next()) {
            Vector<String> vector = new Vector<>();
            vector.add(resultSet.getString("id"));  // Providing record ID
            vector.add(resultSet.getString("category_name"));  // Category name
            vector.add(resultSet.getString("brand_name"));  // Brand name
            vector.add(resultSet.getString("quantity"));  // Quantity
            vector.add(resultSet.getString("date"));  // Date
            vector.add(resultSet.getString("o_date"));  // Other date
            vector.add(resultSet.getString("o_qty"));  // Other quantity
            vector.add(resultSet.getString("price"));  // Price
            model.addRow(vector);  // Add the row to the table
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void loadRecord3() {
    try {
        // Query to fetch all records from `payfor_areca`
        String query = "SELECT * FROM `payfor_areca`";

        ResultSet resultSet = MYSQL.execute(query);

        // Get the model of the table and clear existing rows
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);  // Clear the table to prevent duplicate entries

        // Loop through the ResultSet and populate the table
        while (resultSet.next()) {
            Vector<String> vector = new Vector<>();
            vector.add(resultSet.getString("id"));         // Payfor_areca record ID
            vector.add(resultSet.getString("p_date"));     // Payment date
            vector.add(resultSet.getString("o_date"));     // Order date
            vector.add(resultSet.getString("totqty"));     // Total quantity
            vector.add(resultSet.getString("ammount"));    // Amount

            // Add the row to the table model
            model.addRow(vector);
        }

    } catch (SQLException e) {
        // Catch and print SQL exceptions
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "SQL error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        // Catch any other exceptions
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

     
      
     
     private void reset() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(-1);
        jSpinner1.setValue(0);
        jTable1.clearSelection();
        jTextField1.grabFocus();
        jComboBox3.setSelectedIndex(-1);

        jTable1.clearSelection();

        //jButton1.setEnabled(true);
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(51, 51, 51));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Providing for the ");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel8.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 230, -1));

        jLabel2.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("cleaning of Areca nut");
        jPanel8.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 250, 30));

        jLabel3.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel3.setText("Category");

        jLabel4.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel4.setText("Quantity");

        jLabel5.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel5.setText("Rank");

        jLabel7.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel7.setText("ID");

        jButton1.setBackground(new java.awt.Color(0, 204, 255));
        jButton1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 102, 255));
        jButton2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 51, 51));
        jButton3.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel18.setText("Stock");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Category", "Rank", "Quantity", "Date"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Providing", jPanel3);

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        jLabel6.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Obtaining a purified Areca");

        jLabel9.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel9.setText("Quantity");

        jLabel10.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel10.setText("Price");

        jButton4.setBackground(new java.awt.Color(0, 204, 255));
        jButton4.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Save");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel8.setText("ID");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField3)
                    .addComponent(jSpinner2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Category", "Rank", "Quantity", "Date", "O-Date", "O-Quantity", "Price"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Obtaining", jPanel5);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        jLabel11.setFont(new java.awt.Font("Serif", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Payments");

        jLabel12.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel12.setText("ID");

        jLabel13.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel13.setText("P-Date");

        jLabel14.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel14.setText("O-Date");

        jLabel15.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jLabel15.setText("Quantity");

        jLabel17.setFont(new java.awt.Font("Serif", 1, 20)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton8.setBackground(new java.awt.Color(255, 51, 51));
        jButton8.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Delete");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(102, 102, 255));
        jButton9.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Save");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 204, 255));
        jButton10.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Proccess");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Serif", 1, 20)); // NOI18N
        jLabel19.setText("Ammount :");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField5)
                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "P-Date", "O-Date", "Total Quantity", "Total Price"
            }
        ));
        jTable3.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTable3AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Payment", jPanel4);

        jPanel2.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 550));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );

        jButton7.setBackground(new java.awt.Color(153, 0, 0));
        jButton7.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Close");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 153, 204));
        jButton5.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Print");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void payReset(){
       jTextField4.setText("");
       jTextField5.setText("");
       jTextField6.setText("");
       jTextField7.setText("");
       jLabel17.setText("");
    }
    
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String ID = jTextField4.getText();
        String p_date = jTextField5.getText();
        String o_date = jTextField6.getText();
        String qty = jTextField7.getText();
        String amount = jLabel17.getText(); // Corrected the spelling from ammount to amount

        try {
            // Checking if any fields are empty
            if(ID.isEmpty()){
                JOptionPane.showMessageDialog(this, "ID is Not Set", "Warning", JOptionPane.ERROR_MESSAGE);
            } else if(p_date.isEmpty()){
                JOptionPane.showMessageDialog(this, "P-Date is Not Set", "Warning", JOptionPane.ERROR_MESSAGE);
            } else if(o_date.isEmpty()){
                JOptionPane.showMessageDialog(this, "O-Date is Not Set", "Warning", JOptionPane.ERROR_MESSAGE);
            } else if(qty.isEmpty()){
                JOptionPane.showMessageDialog(this, "Quantity is Not Set", "Warning", JOptionPane.ERROR_MESSAGE);
            } else if(amount.isEmpty()){
                JOptionPane.showMessageDialog(this, "Amount is Not Set", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                // Check if the record already exists
                ResultSet resultSet = MYSQL.execute("SELECT * FROM `payfor_areca` WHERE `id` = '"+ID+"'");

                if(resultSet.next()) {
                    // Record with this ID already exists
                    JOptionPane.showMessageDialog(this, "Record with ID already exists!", "Warning", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Insert a new record into the table
                    MYSQL.execute("INSERT INTO `payfor_areca` (`id`, `p_date`, `o_date`, `totqty`, `ammount`) VALUES "
                        + "('"+ID+"', '"+p_date+"', '"+o_date+"', '"+qty+"', '"+amount+"')");

                    JOptionPane.showMessageDialog(this, "Successfully Added", "Done", JOptionPane.INFORMATION_MESSAGE);
                    payReset();
                    loadRecord3();
                    generatepayID();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Insert Fail", "Error", JOptionPane.ERROR_MESSAGE);
        }

        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String ID = jTextField1.getText();  // This is the provided record ID

// Ensure valid category selection
if (jComboBox1.getItemCount() == 0) {
    JOptionPane.showMessageDialog(this, "Category combo box is empty.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
    return;
}
if (jComboBox1.getSelectedIndex() == -1) {
    JOptionPane.showMessageDialog(this, "Please select a valid category.", "Category Error", JOptionPane.ERROR_MESSAGE);
    return;
}
String ctg = (String) jComboBox1.getSelectedItem();  // Selected category
String selectedCategoryID = null;

// Extract category ID
String[] parts = ctg.split(" - ");
if (parts.length > 0) {
    selectedCategoryID = parts[0];  // Extract category ID
}

// Get selected stock (betel nuts) from jComboBox3
if (jComboBox3.getItemCount() == 0) {
    JOptionPane.showMessageDialog(this, "Stock combo box is empty.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
    return;
}
if (jComboBox3.getSelectedIndex() == -1) {
    JOptionPane.showMessageDialog(this, "Please select a valid stock item.", "Stock Error", JOptionPane.ERROR_MESSAGE);
    return;
}
String stockData = (String) jComboBox3.getSelectedItem();
String selectedStockID = null;

// Extract the stock ID (for betel nuts)
parts = stockData.split(" - ");
if (parts.length > 0) {
    selectedStockID = parts[0];  // Extract the stock ID
}

// Get quantity to clear
int qty = (Integer) jSpinner1.getValue();
String rank = (String) jComboBox2.getSelectedItem();  // Rank selection
String selectedRankID = null;
String date = LocalDate.now().toString();  // Get current date

// Ensure valid rank selection
if (jComboBox2.getItemCount() == 0) {
    JOptionPane.showMessageDialog(this, "Rank combo box is empty.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
    return;
}
if (jComboBox2.getSelectedIndex() == -1) {
    JOptionPane.showMessageDialog(this, "Please select a valid rank.", "Rank Error", JOptionPane.ERROR_MESSAGE);
    return;
}
if (!"Select".equals(rank)) {
    parts = rank.split(" - ");
    if (parts.length > 0) {
        selectedRankID = parts[0];  // Extract rank ID
    }
}

// Check for valid ID and quantity input
if (ID.isEmpty()) {
    JOptionPane.showMessageDialog(this, "Please refresh the page, ID is empty.", "Empty Warning", JOptionPane.WARNING_MESSAGE);
} else if (qty <= 0) {
    JOptionPane.showMessageDialog(this, "Please add a valid quantity.", "Quantity Warning", JOptionPane.WARNING_MESSAGE);
} else {
    try {
        // Check the current stock quantity
        ResultSet rs = MYSQL.execute("SELECT qty FROM stock WHERE id = '" + selectedStockID + "'");
        if (rs.next()) {
            int currentQty = rs.getInt("qty");
            int newQty = currentQty - qty;

            if (newQty >= 0) {
                // Update the stock quantity to reflect the cleared amount
                MYSQL.execute("UPDATE stock SET qty = '" + newQty + "' WHERE id = '" + selectedStockID + "'");

                // Check if record already exists in 'providing'
                ResultSet resultSet = MYSQL.execute("SELECT * FROM providing WHERE id = '" + ID + "'");
                if (!resultSet.next()) {
                    // Perform the INSERT query to add the providing record
                    MYSQL.execute("INSERT INTO providing(id, quantity, date, category_id, brand_id, stock_id) "
                            + "VALUES ('" + ID + "', '" + qty + "', '" + date + "', '" + selectedCategoryID + "', '" + selectedRankID + "', '"+ selectedStockID +"')");

                    JOptionPane.showMessageDialog(this, "Record inserted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    reset();  // Reset the form
                    loadRecord();  // Reload records
                    generateID();  // Generate new ID
                    loadRecord1(); //Reload records2
                } else {
                    JOptionPane.showMessageDialog(this, "Record with this ID already exists.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Insufficient stock, skip insertion
                JOptionPane.showMessageDialog(this, "Insufficient stock to clear the requested amount.", "Stock Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No stock found for the selected product.", "Stock Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        reset();
        generateID();
        payReset();
        generatepayID();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        reset();
        generateID();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       int qty = (Integer) jSpinner2.getValue(); // Get Quantity
       String date = LocalDate.now().toString();  // Get current date
       String price = jTextField3.getText();      // Get Price
       String ID = jTextField2.getText();         // Get Id

            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Add Quantity", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (ID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select Row You Want for Get Id", "Warning", JOptionPane.WARNING_MESSAGE);
            } else if (price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Add Price", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                // Try to parse the price into a numeric value
                double priceValue = Double.parseDouble(price);
                if (priceValue <= 0) {
                    JOptionPane.showMessageDialog(this, "Price must be greater than 0", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Proceed with the logic if all checks are passed
                }
            }
       
        try {
            ResultSet resultSet = MYSQL.execute("SELECT * FROM `providing` WHERE id = '"+ID+"'");
            
            if(resultSet.next()){
                MYSQL.execute("UPDATE `providing` SET `o_id`='" + ID + "', `o_qty`='" + qty + "', `o_date`='" + date + "', `price`='" + price + "' WHERE  `id`='" + ID + "'");
                JOptionPane.showMessageDialog(this, "Update Successfully", "success", HEIGHT);
                reset();
                loadRecord1();
            }
       } catch (Exception e) {
        }
       
       
      
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String selectedID = jTextField1.getText();
        // Ensure valid category selection
        if (jComboBox1.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Category combo box is empty.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (jComboBox1.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a valid category.", "Category Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String ctg = (String) jComboBox1.getSelectedItem();  // Selected category
        String selectedCategoryID = null;

        // Extract category ID
        String[] parts = ctg.split(" - ");
        if (parts.length > 0) {
            selectedCategoryID = parts[0];  // Extract category ID
        }

        // Get selected stock (betel nuts) from jComboBox3
        if (jComboBox3.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Stock combo box is empty.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (jComboBox3.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a valid stock item.", "Stock Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String stockData = (String) jComboBox3.getSelectedItem();
        String selectedStockID = null;

        // Extract the stock ID (for betel nuts)
        parts = stockData.split(" - ");
        if (parts.length > 0) {
            selectedStockID = parts[0];  // Extract the stock ID
        }

        // Get quantity to clear
        int qty = (Integer) jSpinner1.getValue();
        String rank = (String) jComboBox2.getSelectedItem();  // Rank selection
        String selectedRankID = null;
        String date = LocalDate.now().toString();  // Get current date

        // Ensure valid rank selection
        if (jComboBox2.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Rank combo box is empty.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (jComboBox2.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a valid rank.", "Rank Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!"Select".equals(rank)) {
            parts = rank.split(" - ");
            if (parts.length > 0) {
                selectedRankID = parts[0];  // Extract rank ID
            }
        }

        // Check for valid ID and quantity input
        if (ID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please refresh the page, ID is empty.", "Empty Warning", JOptionPane.WARNING_MESSAGE);
        } else if (qty <= 0) {
            JOptionPane.showMessageDialog(this, "Please add a valid quantity.", "Quantity Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            
             try {
                // Check the current stock quantity
                ResultSet rs = MYSQL.execute("SELECT qty FROM stock WHERE id = '" + selectedStockID + "'");
                if (rs.next()) {
                    int currentQty = rs.getInt("qty");

                    // Check if record already exists in 'providing'
                    ResultSet resultSet = MYSQL.execute("SELECT quantity FROM providing WHERE id = '" + selectedID + "'");
                    if (resultSet.next()) {
                        // Get the previously added quantity
                        int previousQty = resultSet.getInt("quantity");

                        // Restore the previously added quantity back to stock
                        int restoredQty = currentQty + previousQty;

                        // Calculate the new stock quantity after reducing the new amount
                        int newQty = restoredQty - qty;

                        if (newQty >= 0) {
                            // Update the stock quantity to reflect the new changes
                            MYSQL.execute("UPDATE stock SET qty = '" + newQty + "' WHERE id = '" + selectedStockID + "'");

                            // Update the 'providing' record with the new quantity and other details
                            MYSQL.execute("UPDATE providing SET quantity = '" + qty + "', date = '" + date + "', category_id = '" + selectedCategoryID + "', brand_id = '" + selectedRankID + "', stock_id = '" + selectedStockID + "' WHERE id = '" + selectedID + "'");

                            JOptionPane.showMessageDialog(this, "Record updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            reset();  // Reset the form
                            loadRecord();  // Reload records
                            generateID();  // Generate new ID
                            loadRecord1(); // Reload records2
                        } else {
                            // Insufficient stock after the reduction
                            JOptionPane.showMessageDialog(this, "Insufficient stock to clear the requested amount.", "Stock Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No record found to update.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No stock found for the selected product.", "Stock Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        getSelectedRowData();
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String selectedID = jTextField1.getText();

        try {
            // Fetch the record from 'providing' based on the selected ID
            ResultSet resultSet = MYSQL.execute("SELECT * FROM `providing` WHERE id = '" + selectedID + "'");

            if (resultSet.next()) {
                // Get the stock_id and previously added quantity
                String stockID = resultSet.getString("stock_id");
                int previousQty = resultSet.getInt("quantity");

                // Fetch the current stock quantity
                ResultSet rs = MYSQL.execute("SELECT qty FROM stock WHERE id = '" + stockID + "'");
                if (rs.next()) {
                    int currentQty = rs.getInt("qty");

                    // Restore the previously added quantity back to stock
                    int restoredQty = currentQty + previousQty;

                    // Check if the restored quantity is valid
                    if (restoredQty >= 0) {
                        // Update the stock quantity to reflect the restored amount
                        MYSQL.execute("UPDATE stock SET qty = '" + restoredQty + "' WHERE id = '" + stockID + "'");

                        // Delete the record from 'providing'
                        MYSQL.execute("DELETE FROM providing WHERE id = '" + selectedID + "'");

                        JOptionPane.showMessageDialog(this, "Record deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        reset();  // Reset the form
                        loadRecord();  // Reload records
                        generateID();  // Generate new ID
                        loadRecord1(); // Reload records2
                    } else {
                        JOptionPane.showMessageDialog(this, "Restored quantity is invalid.", "Stock Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No stock found for the selected stock ID.", "Stock Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No record found in providing for the selected ID.", "Record Not Found", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        getSelectedRowData1();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        payAmmount();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String ID = jTextField4.getText();
String p_date = jTextField5.getText();
String o_date = jTextField6.getText();
String qty = jTextField7.getText();
String amount = jLabel17.getText();

try {
    ResultSet resultSet = MYSQL.execute("SELECT * FROM `payfor_areca` WHERE `id` = '"+ID+"'");

    if (resultSet.next()) {
        // Correct DELETE SQL query
        MYSQL.execute("DELETE FROM `payfor_areca` WHERE id = '"+ID+"'");

        JOptionPane.showMessageDialog(this, "Record Deleted Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
        
        // Reset form fields, reload records, and regenerate pay ID
        payReset();
        loadRecord3();
        generatepayID();
    } else {
        JOptionPane.showMessageDialog(this, "No record found with ID: " + ID, "Warning", JOptionPane.WARNING_MESSAGE);
    }

} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Delete Operation Failed", "Error", JOptionPane.ERROR_MESSAGE);
}

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jTable3AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTable3AncestorAdded
        
    }//GEN-LAST:event_jTable3AncestorAdded

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        getSelectedRowData2();
    }//GEN-LAST:event_jTable3MouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatDarkLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Areca_nuts_out().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton10;
    public javax.swing.JButton jButton2;
    public javax.swing.JButton jButton3;
    public javax.swing.JButton jButton4;
    public javax.swing.JButton jButton5;
    public javax.swing.JButton jButton7;
    public javax.swing.JButton jButton8;
    public javax.swing.JButton jButton9;
    public javax.swing.JComboBox<String> jComboBox1;
    public javax.swing.JComboBox<String> jComboBox2;
    public javax.swing.JComboBox<String> jComboBox3;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel11;
    public javax.swing.JPanel jPanel12;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel8;
    public javax.swing.JPanel jPanel9;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JSpinner jSpinner1;
    public javax.swing.JSpinner jSpinner2;
    public javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable jTable1;
    public javax.swing.JTable jTable2;
    public javax.swing.JTable jTable3;
    public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;
    public javax.swing.JTextField jTextField3;
    public javax.swing.JTextField jTextField4;
    public javax.swing.JTextField jTextField5;
    public javax.swing.JTextField jTextField6;
    public javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
