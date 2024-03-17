/*    */ package com.connector.cludodb;
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Font;

import javax.swing.BoxLayout;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ public class FrameUtilities {
/* 11 */   static Dimension inputSize = new Dimension(300, 30);
/* 12 */   static Dimension panelSize = new Dimension(800, 140);
/* 13 */   static Font labelFont = new Font("Arial", 0, 25);
/* 14 */   static Font inputFont = new Font("Arial", 0, 23);
/*    */   
/*    */   public static JTextField createTextPanel(JPanel masterPanel, String labelText) {
/* 17 */     Font labelFont = new Font("Arial", 0, 25);
/*    */     
/* 19 */     JPanel panel = new JPanel();
/* 20 */     panel.setPreferredSize(panelSize);
/* 21 */     panel.setBackground(Color.decode("#2c3e50"));
/* 22 */     panel.setLayout(new BoxLayout(panel, 0));
/*    */     
/* 24 */     JLabel label = new JLabel(labelText);
/* 25 */     label.setMaximumSize(new Dimension(350, 100));
/* 26 */     label.setForeground(Color.WHITE);
/* 27 */     label.setFont(labelFont);
/*    */     
/* 29 */     JTextField textField = new JTextField();
/* 30 */     textField.setMaximumSize(inputSize);
/* 31 */     textField.setForeground(Color.WHITE);
/* 32 */     textField.setBackground(Color.decode("#34495e"));
/* 33 */     textField.setFont(inputFont);
			 textField.setCaretColor(Color.WHITE);
/*    */     
/* 35 */     panel.add(label);
/* 36 */     panel.add(textField);
/* 37 */     masterPanel.add(panel);
/*    */     
/* 39 */     return textField;
/*    */   }
/*    */   
/*    */   public static JComboBox createComboBoxPanel(JPanel masterPanel, String labelText, String[] choices) {
/* 43 */     JPanel panel = new JPanel();
/* 44 */     panel.setBackground(Color.decode("#2c3e50"));
/* 45 */     panel.setPreferredSize(panelSize);
/* 46 */     panel.setLayout(new BoxLayout(panel, 0));
/*    */     
/* 48 */     JLabel label = new JLabel(labelText);
/* 49 */     label.setForeground(Color.WHITE);
/* 50 */     label.setMaximumSize(new Dimension(350, 100));
/* 51 */     label.setFont(labelFont);
/*    */     
/* 53 */     JComboBox<String> comboBox = new JComboBox<>(choices);
/* 54 */     comboBox.setBackground(Color.decode("#34495e"));
/* 55 */     comboBox.setForeground(Color.WHITE);
/* 56 */     comboBox.setMaximumSize(inputSize);
/* 57 */     comboBox.setFont(inputFont);
/*    */     
/* 59 */     panel.add(label);
/* 60 */     panel.add(comboBox);
/* 61 */     masterPanel.add(panel);
/* 62 */     return comboBox;
/*    */   }
/*    */ }