/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lk.genesisarcsystem.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.mysql.cj.xdevapi.Statement;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.table.DefaultTableModel;
import lk.genesisarcsystem.connection.MYSQL;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asiri
 */
public class CashierDashboard extends javax.swing.JFrame {

    /**
     * Creates new form CashierDashboard
     */
    public CashierDashboard() {
        initComponents();
        //loadproducts();
        //loadrank();
        //loadcategory();
        loadstockpaytype();
        //loadstock();
        generateInvoiceNumber();
        stockWarning();
        loadCategories();
    }
    

// Load Categories into jComboBox2
public void loadCategories() {
    try {
        ResultSet resultSet = MYSQL.execute("SELECT id, name FROM category");

        Vector<String> categories = new Vector<>();
        categories.add("Select");  // Add a default option

        while (resultSet.next()) {
            String categoryName = resultSet.getString("name");
            categories.add(categoryName);  // Only show category name
        }

        DefaultComboBoxModel<String> categoryModel = (DefaultComboBoxModel<String>) jComboBox2.getModel();
        categoryModel.removeAllElements();
        categoryModel.addAll(categories);
        jComboBox2.setSelectedIndex(0);  // Set default selection

        // Add ActionListener for category selection
        jComboBox2.addActionListener(e -> {
            if (jComboBox2.getSelectedIndex() > 0) {
                String selectedCategory = (String) jComboBox2.getSelectedItem();
                loadBrandsForCategory(selectedCategory);  // Load related brands
            }
        });

    } catch (Exception e) {
        e.printStackTrace();
    }
}

// Load Brands into jComboBox5 based on selected category
public void loadBrandsForCategory(String categoryName) {
    try {
        String query = "SELECT name FROM brand WHERE category_id = (SELECT id FROM category WHERE name = '" + categoryName + "')";
        ResultSet resultSet = MYSQL.execute(query);

        Vector<String> brands = new Vector<>();
        brands.add("Select");  // Add a default option

        while (resultSet.next()) {
            String brandName = resultSet.getString("name");
            brands.add(brandName);  // Only show brand name
        }

        DefaultComboBoxModel<String> brandModel = (DefaultComboBoxModel<String>) jComboBox5.getModel();
        brandModel.removeAllElements();  // Clear existing items
        brandModel.addAll(brands);  // Add new brand items
        jComboBox5.setSelectedIndex(0);  // Set default selection

        // Remove previous ActionListeners to avoid duplicate triggers
        for (ActionListener al : jComboBox5.getActionListeners()) {
            jComboBox5.removeActionListener(al);
        }

        // Add ActionListener for brand selection
        jComboBox5.addActionListener(e -> {
            if (jComboBox5.getSelectedIndex() > 0) {
                String selectedBrand = (String) jComboBox5.getSelectedItem();
                loadStockForBrandAndCategory(categoryName, selectedBrand);  // Load stock for selected category and brand
                loadProductsForBrand(selectedBrand);  // Load products for the selected brand
            }
        });

    } catch (Exception e) {
        e.printStackTrace();
    }
}

// Load Stock items into jComboBox4 based on selected category and brand
public void loadStockForBrandAndCategory(String categoryName, String brandName) {
    try {
        String query = "SELECT stock.id, product.name FROM stock "
                     + "INNER JOIN product ON stock.product_id = product.id "
                     + "WHERE product.category_id = (SELECT id FROM category WHERE name = '" + categoryName + "') "
                     + "AND product.brand_id = (SELECT id FROM brand WHERE name = '" + brandName + "')";

        ResultSet resultSet = MYSQL.execute(query);

        Vector<String> stockItems = new Vector<>();
        stockItems.add("Select");  // Add default option

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            //String productName = resultSet.getString("name");
            stockItems.add(id );  // Only show product name
        }

        DefaultComboBoxModel<String> stockModel = (DefaultComboBoxModel<String>) jComboBox4.getModel();
        stockModel.removeAllElements();  // Clear existing items
        stockModel.addAll(stockItems);  // Add new stock items

        if (stockModel.getSize() > 0) {
            jComboBox4.setSelectedIndex(0);  // Set default selection
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

// Load Products into jComboBox1 based on selected brand
public void loadProductsForBrand(String brandName) {
    try {
    String query = "SELECT id, name FROM product WHERE brand_id = (SELECT id FROM brand WHERE name = '" + brandName + "')";
    ResultSet resultSet = MYSQL.execute(query);

    Vector<String> products = new Vector<>();
    products.add("Select");  // Add default option

    while (resultSet.next()) {
        String id = resultSet.getString("id");  // Get product ID
        String productName = resultSet.getString("name");  // Get product name
        products.add(id + " - " + productName);  // Combine product ID and name in the format 'id - name'
    }

    DefaultComboBoxModel<String> productModel = (DefaultComboBoxModel<String>) jComboBox1.getModel();
    productModel.removeAllElements();  // Clear existing items
    productModel.addAll(products);  // Add new product items with both ID and name

    if (productModel.getSize() > 0) {
        jComboBox1.setSelectedIndex(0);  // Set default selection
    }

} catch (Exception e) {
    e.printStackTrace();
}

}



    
    //HashMap<String, InvoiceItem> invoiceItemMap = new HashMap<>();
    HashMap<String, String> paymentMethodMap = new HashMap<>();
    
     private void generateInvoiceNumber() {
        long id = System.currentTimeMillis();  // Get current time in milliseconds
        String idStr = String.valueOf(id);     // Convert to String
        String invoiceNumber = idStr.substring(idStr.length() - 9);  // Get the last 10 characters

        jTextField1.setText(invoiceNumber);    // Set the value to the text field
     }
     
     
     private void stockWarning() {
         
         //int Qty = (int)jSpinner1.getValue();
         
         try {
            // SQL query to retrieve product ID, product name, brand name, and total quantity
            ResultSet resultSet = MYSQL.execute("SELECT product.id, product.name, brand.name AS brand_name, SUM(stock.qty) AS total_qty "
                        + "FROM stock "
                        + "INNER JOIN product ON stock.product_id = product.id "
                        + "INNER JOIN brand ON product.brand_id = brand.id "
                        + "GROUP BY product.id");

            // Loop through the result set
            while (resultSet.next()) {
                String productName = resultSet.getString("product.name");  // Get product name
                String brandName = resultSet.getString("brand_name");      // Get brand name
                int productID = resultSet.getInt("product.id");            // Get product ID
                int totalQty = resultSet.getInt("total_qty");              // Get total quantity for this product

                // If total quantity is less than or equal to 10, show the message
                if (totalQty <= 500) {
                    String message = "Product ID " + productID + " (" + productName + ") from Rank " + brandName 
                            + " has a low total quantity: " + totalQty;
                    
                }
            }
        } catch (Exception ex) {
            // Improved error handling
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error checking stock: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    
     
   private void finalAmmount() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    
    // Initialize a variable to hold the total amount
    double totalDue = 0;

    // Loop through all rows in the table
    for (int i = 0; i < model.getRowCount(); i++) {
        // Get the value from the "Total" column (assuming it's the 5th column, index 4)
        Object totalObject = model.getValueAt(i, 6);  // Adjust column index if needed
        
        if (totalObject != null) {  // Check if the cell value is not null
            String totalString = totalObject.toString();  // Convert the object to string
            
            try {
                // Convert the total string to a double and add to totalDue
                double total = Double.parseDouble(totalString);
                totalDue += total;
            } catch (NumberFormatException e) {
                // Handle invalid number formats
                JOptionPane.showMessageDialog(this, "Invalid number format in the Total column at row " + (i + 1), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Handle null values in the "Total" column
            JOptionPane.showMessageDialog(this, "Empty value in the Total column at row " + (i + 1), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Now you have the total due
    JOptionPane.showMessageDialog(this, "Total Due: " + totalDue, "Total Due", JOptionPane.INFORMATION_MESSAGE);
    
    // You can also set this total to a label or any other component
    jLabel16.setText(String.format("%.2f", totalDue));  // Assuming you have a JLabel for total due
}

    public void loadstockpaytype() {

        try {

            ResultSet resultSet = MYSQL.execute("SELECT * FROM payment_method");

            Vector v = new Vector();
            v.add("Select");  // Add the default option

            while (resultSet.next()) {
                String id = resultSet.getString("id");        // Get the id
                String method = resultSet.getString("method");  // Get the method
                v.add(id + " - " + method);  // Concatenate both fields into a string and add it to the Vector
            }

            DefaultComboBoxModel model = (DefaultComboBoxModel) jComboBox3.getModel();
            model.removeAllElements();

            model.addAll(v);
            jComboBox3.setSelectedIndex(0);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    
    
    
private double price;
private double total = 0;

public void calculateTotal() {
    // Check if the price field is empty
    if (jTextField5.getText().trim().isEmpty()) {
        price = 0; // Default to 0 if the price field is empty
    } else {
        // Get the quantity from the spinner
        int qty = (Integer) jSpinner1.getValue();
        
        // Get the price from the text field
        String getPrice = jTextField5.getText().trim(); // Trim any leading/trailing spaces
        
        // Parse the price as a double
        try {
            price = Double.parseDouble(getPrice); // Convert to double
        } catch (NumberFormatException e) {
            // Show error message if the price is not a valid number
            JOptionPane.showMessageDialog(this, "Invalid price format.", "Warning", JOptionPane.ERROR_MESSAGE);
            price = 0;
        }
        
        // Calculate total
        total = price * qty;
        
        // Update the label to show the total
        jLabel8.setText(String.format("%.2f", total)); // Set the total with two decimal places
    }
}

private double tendered;
private double change = 0;

private void calculateChange() {
    // Check if the tendered amount field is empty
    if (jTextField2.getText().isEmpty()) {
        tendered = 0;
    } else {
        // Get the total from the label (which is in String form)
        String ttl = jLabel16.getText();
        // Get the tendered amount from the text field
        String tdd = jTextField2.getText();
        
        try {
            // Parse the tendered amount as a double
            tendered = Double.parseDouble(tdd);
            // Parse the total amount as a double
            double totalAmount = Double.parseDouble(ttl);
            
            // Calculate change if tendered is greater than or equal to the total
            if (tendered >= totalAmount) {
                change = tendered - totalAmount;
                //JOptionPane.showMessageDialog(this, "Change: " + change);
                jTextField3.setText(String.valueOf(change));
            } else {
                 jTextField3.setText(String.valueOf("Insufficient"));
                // If tendered amount is less than the total, show an error message
                //JOptionPane.showMessageDialog(this, "Insufficient amount tendered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            // Handle invalid number formats
            JOptionPane.showMessageDialog(this, "Invalid price format.", "Warning", JOptionPane.ERROR_MESSAGE);
            tendered = 0;
        }
    }
}

     private void reset() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField5.setText("");
        jLabel8.setText("");
        jLabel16.setText("");
        jComboBox4.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jComboBox3.setSelectedIndex(0);
        jSpinner1.setValue(0);
        
        jTextField1.grabFocus();
        

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
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Serif", 1, 30)); // NOI18N
        jLabel1.setText("Genesisarc Group");

        jPanel2.setBackground(new java.awt.Color(47, 47, 47));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel2.setText("Invoice ID");

        jLabel4.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel4.setText("Product");

        jComboBox1.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel5.setText("Quantity");

        jSpinner1.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jSpinner1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSpinner1KeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel7.setText("Price");

        jLabel9.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Tendered :");

        jLabel10.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Change :");

        jLabel11.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Mode of Pay :");

        jButton2.setBackground(new java.awt.Color(0, 51, 255));
        jButton2.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 153, 255));
        jButton3.setFont(new java.awt.Font("Serif", 1, 16)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Insert");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton3KeyPressed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(51, 255, 51));
        jButton4.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Print");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(51, 51, 51));
        jButton5.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Add New Stock");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });

        jComboBox3.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel6.setText("Category");

        jComboBox2.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel13.setText("Stock");

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setText("Reset Table");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jComboBox4.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Total :");

        jLabel8.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jLabel8KeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Serif", 1, 25)); // NOI18N
        jLabel14.setText("Last Due ");

        jLabel15.setFont(new java.awt.Font("Serif", 1, 30)); // NOI18N
        jLabel15.setText("Rs.");

        jLabel16.setFont(new java.awt.Font("Serif", 1, 25)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel18.setFont(new java.awt.Font("Serif", 1, 18)); // NOI18N
        jLabel18.setText("Rank");

        jComboBox5.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField3)
                                            .addComponent(jTextField2)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(7, 7, 7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGap(0, 19, Short.MAX_VALUE)))))
                .addGap(16, 16, 16))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)))
                .addGap(17, 17, 17))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6)))
        );

        jPanel4.setBackground(new java.awt.Color(94, 94, 94));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice ID", "Product", "Category", "Rank", "Price", "Quantity", "Total"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 255, 204));
        jLabel17.setText("Low Quantity");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Serif", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Invoice");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jLabel17)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int selectedRow = jTable1.getSelectedRow(); // Get selected row from JTable
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an invoice to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;  // Stop execution if no row is selected
        }

        // Get the invoice ID from the selected row (assuming it's in the first column)
        String invoiceID = jTable1.getValueAt(selectedRow, 0).toString(); // Modify the index as needed

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete invoice ID: " + invoiceID + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;  // Stop execution if deletion is not confirmed
        }

        try {
            // Step 1: Retrieve invoice items to update stock quantity
            String getInvoiceItemsQuery = "SELECT qty, stock_id FROM `invoice_item` WHERE `invoice_id` = '" + invoiceID + "'";
            ResultSet invoiceItemsResultSet = MYSQL.execute(getInvoiceItemsQuery);

            // Step 2: Update stock quantities based on the invoice items
            while (invoiceItemsResultSet.next()) {
                int qty = invoiceItemsResultSet.getInt("qty");
                String stockID = invoiceItemsResultSet.getString("stock_id");

                // Update stock quantity in the stock table
                String updateStockQuery = "UPDATE `stock` SET `qty` = `qty` + " + qty + " WHERE `id` = '" + stockID + "'";
                MYSQL.execute(updateStockQuery);
            }

            // Step 3: Delete from invoice_item table using invoice ID
            String deleteInvoiceItemsQuery = "DELETE FROM `invoice_item` WHERE `invoice_id` = '" + invoiceID + "'";
            MYSQL.execute(deleteInvoiceItemsQuery);

            // Step 4: Delete from invoice table using invoice ID
            String deleteInvoiceQuery = "DELETE FROM `invoice` WHERE `id` = '" + invoiceID + "'";
            MYSQL.execute(deleteInvoiceQuery);

            // Remove the row from the JTable model
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(selectedRow);

            // Show success message
            JOptionPane.showMessageDialog(this, "Invoice deleted successfully, stock quantities updated.", "Delete Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting invoice: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Step 1: Retrieve values from the form components
String stockData = (String) jComboBox4.getSelectedItem();
String selectedStockID = null;

if (!"Select".equals(stockData)) {
    // Assuming 'stockID - stockName' format in the ComboBox
    String[] parts = stockData.split(" - ");  // Split the string to extract the stock ID and name
    if (parts.length > 0) {
        selectedStockID = parts[0].trim();  // Extract stock ID and trim any whitespace
        System.out.println("Selected Stock ID: " + selectedStockID);
    }

    // Validate if selectedStockID is an integer (if stock_id is an INT in your database)
    try {
        Integer.parseInt(selectedStockID);  // Ensures stock ID is a valid integer
    } catch (NumberFormatException e) {
        System.out.println("Invalid Stock ID: " + selectedStockID);
        JOptionPane.showMessageDialog(this, "Invalid Stock ID selected", "Error", JOptionPane.ERROR_MESSAGE);
        return;  // Stop execution if the Stock ID is invalid
    }
} else {
    System.out.println("No valid stock selected.");
    JOptionPane.showMessageDialog(this, "Please select a valid stock.", "Error", JOptionPane.ERROR_MESSAGE);
    return;  // Stop execution if no stock is selected
}

String invoiceID = jTextField1.getText();
String productData = (String) jComboBox1.getSelectedItem();
String selectedProductID = null;

if (!"Select".equals(productData)) {
    // Assuming 'productID - productName' format in the ComboBox
    String[] parts = productData.split(" - ");  // Split the string to extract the product ID and name
    if (parts.length > 0) {
        selectedProductID = parts[0].trim();  // Extract product ID and trim any whitespace
        System.out.println("Selected Product ID: " + selectedProductID);
    }

    // Validate if selectedProductID is an integer (if product_id is an INT in your database)
    try {
        Integer.parseInt(selectedProductID);
    } catch (NumberFormatException e) {
        System.out.println("Invalid Product ID: " + selectedProductID);
        JOptionPane.showMessageDialog(this, "Invalid Product ID selected", "Error", JOptionPane.ERROR_MESSAGE);
        return;  // Stop execution if the Product ID is invalid
    }
} else {
    System.out.println("No valid product selected.");
    JOptionPane.showMessageDialog(this, "Please select a valid product.", "Error", JOptionPane.ERROR_MESSAGE);
    return;  // Stop execution if no product is selected
}

String ctg = (String) jComboBox2.getSelectedItem();
int qty = (Integer) jSpinner1.getValue();
String qtyStr = String.valueOf(qty);
String price = jTextField5.getText();
String total = jLabel8.getText();
String tendered = jTextField2.getText();
String change = jTextField3.getText();
String date = LocalDate.now().toString();
String rank = (String) jComboBox5.getSelectedItem();
double discount = 0;

// Step 2: Retrieve the selected payment method ID from the ComboBox
String pay_method = (String) jComboBox3.getSelectedItem();
String selectedPaymentMethodID = null;

if (!"Select".equals(pay_method)) {
    String[] parts = pay_method.split(" - ");  // Split the string to extract the payment method ID
    if (parts.length > 0) {
        selectedPaymentMethodID = parts[0].trim();  // Extract payment method ID and trim any whitespace
        System.out.println("Selected Payment Method ID: " + selectedPaymentMethodID);
    }
} else {
    System.out.println("No valid payment method selected.");
    JOptionPane.showMessageDialog(this, "Please select a valid payment method.", "Error", JOptionPane.ERROR_MESSAGE);
    return;  // Stop execution if no payment method is selected
}

try {
    // Insert data into `invoice` and `invoice_item` tables
    try {
        // Insert into `invoice` table
        String insertInvoiceQuery = "INSERT INTO `invoice`(`id`, `date`, `discount`, `paid_ammount`, `payment_method_id`) VALUES('"
                + invoiceID + "','" + date + "', '" + discount + "', '" + total + "', '" + selectedPaymentMethodID + "')";
        MYSQL.execute(insertInvoiceQuery);

        // Insert into `invoice_item` table
        String insertInvoiceItemQuery = "INSERT INTO `invoice_item`(`qty`, `stock_id`, `invoice_id`, `sell_price`) VALUES('"
                + qtyStr + "','" + selectedStockID + "','" + invoiceID + "','" + price + "')";
        MYSQL.execute(insertInvoiceItemQuery);

        // Step 3: Update stock quantity
        String updateStockQuery = "UPDATE `stock` SET `qty` = `qty` - " + qtyStr + " WHERE `id` = '" + selectedStockID + "'";
        MYSQL.execute(updateStockQuery);
        
        // Continue with other logic...

        JOptionPane.showMessageDialog(this, "Success", "Insert Data", JOptionPane.INFORMATION_MESSAGE);

        // Add the data to the JTable and reset form fields, generate new invoice number, etc.
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{invoiceID, productData, ctg, rank, price, qtyStr, total});

        reset();
        generateInvoiceNumber();
        finalAmmount();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error inserting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
} catch (Exception ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
}

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //Stock(productManagement's stock)
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        //reset();
        
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed
    
    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
         calculateTotal();
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        calculateChange();
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        reset();
        generateInvoiceNumber();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        //reset Table
        
        reset();
        generateInvoiceNumber();
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        while(model.getRowCount() > 0){            
            for (int i = 0; i < model.getRowCount(); i++) {
                model.removeRow(i);
            }
        }
        
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        //finalAmmount();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
       LowStock lowstock = new LowStock();
       lowstock.setVisible(true);
       this.disableEvents(EXIT_ON_CLOSE);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jButton3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton3KeyPressed
        // TODO add your handling code here: insert key press
    }//GEN-LAST:event_jButton3KeyPressed

    private void jLabel8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel8KeyReleased

    }//GEN-LAST:event_jLabel8KeyReleased

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jSpinner1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSpinner1KeyPressed
        
    }//GEN-LAST:event_jSpinner1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatDarkLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CashierDashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
