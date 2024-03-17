/*     */ package com.connector.cludodb;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
/*     */ import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/*     */ 
/*     */ public class Window extends Thread {
/*     */   int windowWidth;
/*     */   int windowHeight;
/*     */   static JPanel panel;
/*     */   static JComboBox clientsList;
/*     */   String windowTitle;
/*     */   static JPanel buttonPanel, upperPanel;
/*     */   static JFrame frame;
/*     */   static DefaultComboBoxModel comboModel;
/*     */   static JTable loadedTable;
/*     */   static JTable table;
            static JButton allList;
/*     */   static JScrollPane scrollPane;
/*     */   static DatabaseConnection con;
/*  36 */   static String[] nazwy = new String[] { "Nazwa produktu", "Klucz produktu", "Wkład własny?", "Wysokość wkładu własnego", "Dofinansowanie?", "Uwagi" ,"Data realizacji", "Stan zamówienia"};
/*  37 */   static String[] nazwy_klientów = new String[] { "Nazwa klienta", "Numer telefonu", "Email" };
/*     */   
/*     */   public Window(String windowTitle, int windowWidth, int windowHeight, DatabaseConnection con) {
/*  40 */     this.windowTitle = windowTitle;
/*  41 */     this.windowWidth = windowWidth;
/*  42 */     this.windowHeight = windowHeight;
/*  43 */     Window.con = con;
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     try {
/*  48 */       frame = dataListWindow();
/*  49 */     } catch (SQLException e) {
/*  50 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JFrame dataListWindow() throws SQLException {
/*  56 */     JButton[] btns = { new JButton("Dodaj"), new JButton("Edytuj"), new JButton("Usuń"), new JButton("Dodaj klienta") };
/*     */     upperPanel = new JPanel();
              upperPanel.setMaximumSize(new Dimension(this.windowWidth, this.windowHeight/10));
              upperPanel.setBackground(Color.decode("#2c3e50"));
              allList = new JButton("Wszystkie zamówienia");
              allList.setFont(new Font("Arial", 0, 21));
              allList.setBackground(Color.decode("#34495e"));
              allList.setForeground(Color.WHITE);
              allList.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						String[] head = {"Nazwa klienta", "Nazwa produktu", "Data zamówienia", "Stan zamówienia"};
						table = new JTable(new DefaultTableModel(DatabaseConnection.getAllClients(), head));
						TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
						table.setRowSorter(sorter);
						List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
						sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING));
						sorter.setSortKeys(sortKeys);
						scrollPane.getViewport().setView(table);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table.setRowHeight(30);
					table.setBackground(Color.decode("#34495e"));
					table.setFont(new Font("Arial", 0, 24));
					table.setForeground(Color.WHITE);
					table.setBorder(null);
					table.getTableHeader().setFont(new Font("Arial", 0, 20));
					table.setBackground(Color.decode("#34495e"));
					table.getTableHeader().setBackground(Color.decode("#34495e"));
					table.getTableHeader().setForeground(Color.WHITE);
					table.getTableHeader().setBorder(null);
					scrollPane.setBackground(Color.decode("#34495e"));
					scrollPane.getViewport().setBackground(Color.decode("#34495e"));
					panel.setBackground(Color.decode("#2c3e50"));
					scrollPane.setBorder(null);
				}
              });
/*  58 */     comboModel = new DefaultComboBoxModel<>(DatabaseConnection.getTableList());
/*     */     
/*  60 */     clientsList = new JComboBox(comboModel);
/*  61 */     clientsList.getModel().setSelectedItem("");
/*  62 */     clientsList.setMaximumSize(new Dimension(this.windowWidth / 4, this.windowHeight / 20));
/*  63 */     clientsList.setFont(new Font("Arial", 0, 22));
/*  64 */     clientsList.setBackground(Color.decode("#34495e"));
/*  65 */     clientsList.setForeground(Color.WHITE);
/*  66 */     clientsList.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/*     */             try {
						  String[] headers = Window.clientsList.getSelectedItem().toString().equals("Dane_Kontaktowe") ? Window.nazwy_klientów : Window.nazwy;
/*  70 */                 Window.table = DataDisplay.createDataTable(Window.con, Window.clientsList.getSelectedItem().toString(), headers);
/*  72 */                 Window.scrollPane.getViewport().setView(Window.table);
/*  73 */                 Styling.setStyle();
/*  80 */             } catch (SQLException e1) {
/*  81 */               e1.printStackTrace();
/*     */             } 
/*     */           }
/*     */         });
/*     */     upperPanel.add(clientsList);
              upperPanel.add(allList);
/*  86 */     JFrame mainFrame = new JFrame();
/*  87 */     panel = new JPanel();
/*  88 */     panel.setLayout(new BoxLayout(panel, 1));
/*  89 */     buttonPanel = buttonsPanel(btns);
/*  90 */     addButtonFunctions(btns);
/*  91 */     table = new JTable(new DefaultTableModel((Object[][])new String[0][0], (Object[])nazwy));
/*  92 */     table.getTableHeader().setFont(new Font("Arial", 0, 20));
/*  93 */     scrollPane = new JScrollPane(table);
/*  94 */     //panel.add(clientsList);
			  panel.add(upperPanel);
/*  95 */     panel.add(scrollPane);
/*  96 */     panel.add(buttonPanel);
/*     */     
/*  98 */     mainFrame.add(panel);
			  mainFrame.setIconImage(new ImageIcon("C:\\Users\\adamk\\git\\CludoDB\\CludoDB\\Image\\cludodb.ico").getImage());
/*  99 */     mainFrame.setSize(this.windowWidth, this.windowHeight);
/* 100 */     mainFrame.setVisible(true);
/* 101 */     mainFrame.setLocationRelativeTo((Component)null);
/* 102 */     mainFrame.setDefaultCloseOperation(3);
/* 103 */     mainFrame.setResizable(true);
/* 104 */     mainFrame.setTitle(this.windowTitle);
/* 105 */     mainFrame.setVisible(true);
/*     */     
/* 107 */     Styling.setStyle();
/* 108 */     return mainFrame;
/*     */   }
/*     */   public JPanel buttonsPanel(JButton[] buttons) {
/* 111 */     JPanel panel = new JPanel();
/*     */     
/* 113 */     GridBagConstraints c = new GridBagConstraints();
/* 114 */     panel.setLayout(new GridBagLayout());
/* 115 */     panel.setMaximumSize(new Dimension(this.windowWidth, 100));
/* 116 */     for (int i = 0; i < 3; i++) {
/* 117 */       buttons[i].setFont(new Font("Arial", 0, 28));
/* 118 */       buttons[i].setBackground(Color.decode("#34495e"));
/* 119 */       buttons[i].setForeground(Color.WHITE);
/* 120 */       c.gridx = i;
/* 121 */       c.insets = new Insets(10, 0, 10, 10);
/* 122 */       panel.add(buttons[i], c);
/*     */     } 
/* 124 */     buttons[3].setFont(new Font("Arial", 0, 28));
/* 125 */     buttons[3].setBackground(Color.decode("#34495e"));
/* 126 */     buttons[3].setForeground(Color.WHITE);
/* 127 */     c.gridx = 4;
/* 128 */     c.insets = new Insets(0, 60, 0, 0);
/* 129 */     panel.add(buttons[3], c);
/* 130 */     return panel;
/*     */   }
/*     */   
/*     */   public void addButtonFunctions(JButton[] buttons) {
/* 134 */     buttons[0].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 136 */             if (!Window.clientsList.getSelectedItem().equals("Dane_Kontaktowe") && !Window.clientsList.getSelectedItem().equals("")) {
							new AddWindow();
						};
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 141 */     buttons[1].addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
						new EditWindow(table.getSelectedRow());
					}
/*     */         });
/*     */     
/* 146 */     buttons[2].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 148 */             int row = Window.table.getSelectedRow();
/*     */             
/*     */             try {
/* 151 */               String value = DatabaseConnection.returnRowId(Window.clientsList.getSelectedItem().toString(), Window.table.getSelectedRow());
/* 152 */               DatabaseConnection.removeRow(Window.clientsList.getSelectedItem().toString(), value);
/*     */               
/* 154 */               DataDisplay.refreshTable();
/* 155 */             } catch (SQLException e1) {
/* 156 */               e1.printStackTrace();
/*     */             } 
/*     */           }
/*     */         });
/* 160 */     buttons[3].addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
						new AddClientWindow();
					}
				});
/*     */   }
/*     */ }