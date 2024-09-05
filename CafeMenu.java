package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class CafeMenu extends JFrame {


    String imgPath = FileUtils.IMG_CAFE;
    private JTable table;
    private int totalCost;
    private JTextArea textArea;


    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "scott";
    private static final String PASS = "tiger";

    public CafeMenu() {
        JFrame frame = new JFrame("카페 키오스크");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1170, 958);

      
        JPanel centerPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        centerPanel.setBackground(Color.LIGHT_GRAY);
        String[] menu = {"ICE 아메리카노", "HOT 아메리카노", "ICE 카페라떼", "HOT 카페라떼", "ICE 카라멜마끼아또", "HOT 카라멜마끼아또"};
        String[] menu_1 = {"2000 원", "1500 원", "3000 원", "3000 원", "3500 원", "3500 원"};
        JButton[] buttons = new JButton[menu.length];

        textArea = new JTextArea(10, 20); // textArea 초기화
        JScrollPane scrollPane = new JScrollPane(textArea);

        for (int i = 0; i < menu.length; i++) {
            buttons[i] = new JButton("<html>" + menu[i] + "<hr>" + menu_1[i] + "<html>");
   
            ImageIcon icon = new ImageIcon(imgPath + menu[i] + ".jpg");
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            buttons[i].setIcon(icon);
            centerPanel.add(buttons[i]);
        }

        for (int i = 0; i < buttons.length; i++) {
            int finalI = i; 
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedMenu = menu[finalI];
                    String price = menu_1[finalI].replace(" 원", "");
                    int intPrice = Integer.parseInt(price);

                   
                    textArea.append(selectedMenu + " 선택 (" + price + ")\n");

                    
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int rowIndex = findRow(model, selectedMenu); 
                    if (rowIndex != -1) { 
                        int quantity = (int) model.getValueAt(rowIndex, 2);
                        quantity++;
                        model.setValueAt(quantity, rowIndex, 2);
                        int totalPrice = quantity * intPrice;
                        model.setValueAt(totalPrice, rowIndex, 3);
                    } else { 
                        model.addRow(new Object[]{selectedMenu, price, 1, intPrice}); 
                    }

                    // 총 금액 업데이트
                    totalCost += intPrice;
                }
            });
        }

       
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(scrollPane, BorderLayout.CENTER); 
        frame.add(southPanel, BorderLayout.SOUTH);

        
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new GridLayout(4, 1, 0, 10));
        JButton[] orderButtons = {new JButton("close"), new JButton("reset"), new JButton("buy"), new JButton("Login"), new JButton("Point")};
        for (JButton button : orderButtons) {
            button.setBackground(Color.WHITE);
            eastPanel.add(button);
        }

        JPanel southPanel1 = new JPanel();
        southPanel1.setLayout(new BorderLayout());

        String[][] data = new String[0][0]; 
        String[] title = {"상품명", "단가", "수량", "총 금액"};
        DefaultTableModel model = new DefaultTableModel(data, title);
        table = new JTable(model); // 테이블 객체 생성
        JScrollPane scrollPane1 = new JScrollPane(table);
        scrollPane1.setPreferredSize(new Dimension(1000, 200));
        southPanel1.add(scrollPane1, BorderLayout.CENTER);

        // 종료 버튼
        orderButtons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
             
            }
        });

        // 초기화 버튼
        orderButtons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "선택한 메뉴를 초기화합니다.", "reset", JOptionPane.INFORMATION_MESSAGE);
                totalCost = 0; // 결제 후 총 금액 초기화

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                // 스크롤을 맨 위로 이동
                scrollPane.getVerticalScrollBar().setValue(0);
            }
        });

        // 구매 버튼
        orderButtons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 데이터베이스에 구매 내역 저장
                savePurchaseHistory();

           
                JOptionPane.showMessageDialog(null, "총 금액: " + totalCost + "원\n결제를 진행합니다.", "buy", JOptionPane.INFORMATION_MESSAGE);
                totalCost = 0;

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

          
                scrollPane.getVerticalScrollBar().setValue(0);
            }
        });

        // 로그인 버튼
        orderButtons[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginSwing();
            }
        });
        
        orderButtons[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 JOptionPane.showMessageDialog(null, "현재 적립된 100point 를 사용하시겠습니까?", "Point", JOptionPane.INFORMATION_MESSAGE);
 
                 totalCost = 0;
            }
        });

        frame.add(southPanel1, BorderLayout.SOUTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(eastPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

 
    private int findRow(DefaultTableModel model, String selectedMenu) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String menu = (String) model.getValueAt(i, 0);
            if (menu.equals(selectedMenu)) {
                return i;
            }
        }
        return -1;
    }

 
    private void savePurchaseHistory() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO C_MENU (C_NAME, C_PAY, TOTAL_PRICE) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String menuName = (String) model.getValueAt(i, 0);
                String price = (String) model.getValueAt(i, 1);
                int totalPrice = (int) model.getValueAt(i, 3); 

                pstmt.setString(1, menuName);
                pstmt.setString(2, price);
                pstmt.setInt(3, totalPrice);
                pstmt.executeUpdate();
            }

            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CafeMenu();
            }
        });
    }
}

