/*     */ package com.connector.cludodb;
/*     */ import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.sql.SQLException;

/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ 
/*     */ public class EditWindow extends JFrame {
/*     */   JTextField name;
/*     */   JTextField surname;
/*     */   JTextField key;
/*     */   JTextField ownpr;
/*     */   
/*     */   public EditWindow(int rowIndex) {
/*  19 */     createWindow(rowIndex);
/*     */   } JTextField orderDate; JTextField email; JTextField phone; JComboBox own; JComboBox fund; JComboBox state;
/*     */   public void createWindow(final int rowIndex) {
/*  22 */     String[] choices = { "Tak", "Nie" };
			  String[] stany = {"Ukończone", "Nieukończone"};
/*     */     
/*  24 */     JPanel mainPanel = new JPanel();
/*  25 */     mainPanel.setBackground(Color.decode("#2c3e50"));
/*  26 */     JPanel btnPanel = new JPanel();
/*  27 */     btnPanel.setBackground(Color.decode("#2c3e50"));
/*  28 */     JButton edytuj = new JButton("Edytuj");
/*  29 */     String[] rowData = new String[Window.table.getColumnCount()];
/*  30 */     for (int i = 0; i < Window.table.getColumnCount(); i++) {
/*  31 */       rowData[i] = (String)Window.table.getValueAt(rowIndex, i);
/*     */     }
/*  33 */     btnPanel.setPreferredSize(new Dimension(700, 150));
/*  34 */     btnPanel.setLayout(new BoxLayout(btnPanel, 0));
/*  35 */     mainPanel.setLayout(new BoxLayout(mainPanel, 1));
/*  36 */     if (!Window.clientsList.getSelectedItem().toString().equals("Dane_Kontaktowe")) {
/*  37 */       this.name = FrameUtilities.createTextPanel(mainPanel, "Nazwa produktu: ");
/*  38 */       this.name.setText(rowData[0]);
/*  39 */       this.key = FrameUtilities.createTextPanel(mainPanel, "Klucz produktu: ");
/*  40 */       this.key.setText(rowData[1]);
/*  41 */       this.own = FrameUtilities.createComboBoxPanel(mainPanel, "Wkład własny?: ", choices);
/*  42 */       this.own.setSelectedItem(rowData[2]);
/*  43 */       this.ownpr = FrameUtilities.createTextPanel(mainPanel, "Wysokość wkładu własnego: ");
/*  44 */       this.ownpr.setText(rowData[3]);
/*  45 */       this.fund = FrameUtilities.createComboBoxPanel(mainPanel, "Dofinansowanie?: ", choices);
/*  46 */       this.fund.setSelectedItem(rowData[4]);
/*  47 */       this.orderDate = FrameUtilities.createTextPanel(mainPanel, "Data zamówienia: ");
/*  48 */       this.orderDate.setText(rowData[6]);
				this.state = FrameUtilities.createComboBoxPanel(mainPanel, "Stan zamówienia: ", stany);
				this.state.setSelectedItem(rowData[7]);
/*  49 */       edytuj.setFont(new Font("Arial", 0, 23));
/*  50 */       edytuj.setBackground(Color.decode("#34495e"));
/*  51 */       edytuj.setForeground(Color.WHITE);
/*  52 */       btnPanel.add(edytuj);
/*  53 */       mainPanel.add(btnPanel);
/*     */       
/*  55 */       edytuj.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent e) {
/*     */               try {
/*  58 */                 DatabaseConnection.editRow(Window.clientsList.getSelectedItem().toString(), 
/*  59 */                     DatabaseConnection.returnRowId(Window.clientsList.getSelectedItem().toString(), rowIndex), 
/*  60 */                     EditWindow.this.name.getText(), EditWindow.this.key.getText(), EditWindow.this.own.getSelectedItem().toString(), EditWindow.this.ownpr.getText(), EditWindow.this.fund.getSelectedItem().toString(), 
							  EditWindow.this.orderDate.getText(), state.getSelectedItem().toString());
/*  61 */                 DataDisplay.refreshTable();
/*  62 */               } catch (SQLException e1) {
/*     */                 
/*  64 */                 e1.printStackTrace();
/*     */               } 
/*     */             }
/*     */           });
/*     */     } else {
/*  69 */       String[] arr = rowData[0].split(" ");
/*  70 */       this.name = FrameUtilities.createTextPanel(mainPanel, "Imie: ");
/*  71 */       this.name.setText(arr[0]);
/*  72 */       this.surname = FrameUtilities.createTextPanel(mainPanel, "Nazwisko: ");
/*  73 */       this.surname.setText(arr[1]);
/*  74 */       this.phone = FrameUtilities.createTextPanel(mainPanel, "Numer telefonu: ");
/*  75 */       this.phone.setText(rowData[1]);
/*  76 */       this.email = FrameUtilities.createTextPanel(mainPanel, "Email: ");
/*  77 */       this.email.setText(rowData[2]);
/*  78 */       edytuj.setFont(new Font("Arial", 0, 23));
/*  79 */       edytuj.setBackground(Color.decode("#34495e"));
/*  80 */       edytuj.setForeground(Color.WHITE);
/*  81 */       btnPanel.add(edytuj);
/*  82 */       mainPanel.add(btnPanel);
/*     */       
/*  84 */       edytuj.addActionListener(new ActionListener() {
/*     */             public void actionPerformed(ActionEvent e) {
/*     */               try {
/*  87 */                 DatabaseConnection.editClientRow(DatabaseConnection.returnRowId(Window.clientsList.getSelectedItem().toString(), rowIndex), 
/*  88 */                     new String(EditWindow.this.name.getText() + " " + EditWindow.this.surname.getText()), EditWindow.this.phone.getText(), EditWindow.this.email.getText());
/*  89 */                 DataDisplay.refreshTable();
/*  90 */               } catch (SQLException e1) {
/*     */                 
/*  92 */                 e1.printStackTrace();
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/* 101 */     add(mainPanel);
/* 102 */     setTitle("Edytuj");
/* 103 */     setSize(700, 450);
/* 104 */     setLocationRelativeTo((Component)null);
/* 105 */     setResizable(false);
/* 106 */     setVisible(true);
/*     */   }
/*     */ }


