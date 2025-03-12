
import javax.swing.*;
import java.awt.*;


public class   MainFrame extends JFrame {
    private StockManager stockManager = new StockManager();

    public MainFrame() {
        setTitle("Stock Management System");
        setSize(800, 600); // Increased window size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel bgLabel = new JLabel(new ImageIcon("file:///C:/Users/intellij_codes/Stocks_manage/Inventory-management-1024x536.png")); // Ensure "background.jpg" is in the project folder
        bgLabel.setLayout(new BorderLayout());

        // Navbar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(70, 130, 180)); // Steel blue color
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JMenu menu = new JMenu("Menu");
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Arial", Font.BOLD, 14));

        JMenuItem addStockMenu = new JMenuItem("Add Stock");
        JMenuItem removeStockMenu = new JMenuItem("Remove Stock");
        JMenuItem viewStockMenu = new JMenuItem("View Stock");

        menu.add(addStockMenu);
        menu.add(removeStockMenu);
        menu.add(viewStockMenu);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Panels for each feature
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        JPanel homePanel = createHomePanel();
        JPanel addStockPanel = createAddStockPanel();
        JPanel removeStockPanel = createRemoveStockPanel();
        JPanel viewStockPanel = createViewStockPanel();

        mainPanel.add(homePanel, "Home");
        mainPanel.add(addStockPanel, "Add Stock");
        mainPanel.add(removeStockPanel, "Remove Stock");
        mainPanel.add(viewStockPanel, "View Stock");

        add(mainPanel);

        CardLayout cl = (CardLayout) mainPanel.getLayout();

        // Menu actions
        addStockMenu.addActionListener(e -> cl.show(mainPanel, "Add Stock"));
        removeStockMenu.addActionListener(e -> cl.show(mainPanel, "Remove Stock"));
        viewStockMenu.addActionListener(e -> cl.show(mainPanel, "View Stock"));

        // Default to home panel
        cl.show(mainPanel, "Home");
    }

    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Color.WHITE);

        // Add background image
        JLabel bgLabel = new JLabel(new ImageIcon("C:/Users/adibs/intellij_codes/Stocks_manage/Inventory-management-1024x536.png")); // Ensure "background.jpg" is in the project folder
        bgLabel.setLayout(new BorderLayout());




        JLabel welcomeLabel = new JLabel("Welcome to Stock Management System", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Black", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setOpaque(false); // Transparent background for the label
        welcomeLabel.setVerticalAlignment(JLabel.CENTER); // Center vertically
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER); // Center horizontally


        bgLabel.add(welcomeLabel, BorderLayout.NORTH); // Add welcome text over image
        homePanel.add(bgLabel);

        return homePanel;
    }

    private JPanel createAddStockPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255)); // Alice blue

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(15);

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(15);

        JButton addButton = new JButton("Add Stock");
        addButton.setBackground(new Color(50, 205, 50)); // Lime green
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0; gbc.gridy = 0; panel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(quantityLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(quantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(priceLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(priceField, gbc);

        gbc.gridx = 1; gbc.gridy = 3; panel.add(addButton, gbc);

        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                stockManager.addStock(new Stock(name, quantity, price));
                JOptionPane.showMessageDialog(this, "Stock added successfully!");
                nameField.setText("");
                quantityField.setText("");
                priceField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel createRemoveStockPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245)); // Light gray

        JLabel instructionLabel = new JLabel("Enter Stock Name to Remove:", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JTextField searchField = new JTextField(20);
        JButton removeButton = new JButton("Remove Stock");
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);

        JPanel inputPanel = new JPanel();
        inputPanel.add(searchField);
        inputPanel.add(removeButton);

        panel.add(instructionLabel, BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.CENTER);

        removeButton.addActionListener(e -> {
            String name = searchField.getText();
            stockManager.removeStock(name);
            JOptionPane.showMessageDialog(this, "Stock removed successfully!");
        });

        return panel;
    }

    private JPanel createViewStockPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        JTable stockTable = new JTable();

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Search Stock:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(stockTable), BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String query = searchField.getText();
            var stocks = stockManager.searchStock(query);
            String[][] data = new String[stocks.size()][3];
            String[] columns = {"Name", "Quantity", "Price"};

            for (int i = 0; i < stocks.size(); i++) {
                Stock stock = stocks.get(i);
                data[i][0] = stock.getName();
                data[i][1] = String.valueOf(stock.getQuantity());
                data[i][2] = String.valueOf(stock.getPrice());
            }

            stockTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        });

        JButton viewAllButton = new JButton("View All");
        topPanel.add(viewAllButton);

        viewAllButton.addActionListener(e -> {
            var stocks = stockManager.getAllStocks();
            String[][] data = new String[stocks.size()][3];
            String[] columns = {"Name", "Quantity", "Price"};

            for (int i = 0; i < stocks.size(); i++) {
                Stock stock = stocks.get(i);
                data[i][0] = stock.getName();
                data[i][1] = String.valueOf(stock.getQuantity());
                data[i][2] = String.valueOf(stock.getPrice());
            }

            stockTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        });


        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
