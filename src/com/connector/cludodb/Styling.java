/*    */ package com.connector.cludodb;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Styling
/*    */ {
/*    */   public static void setStyle() {
/* 10 */     Window.panel.setBackground(Color.decode("#2c3e50"));
/* 11 */     Window.scrollPane.setBackground(Color.decode("#34495e"));
/* 12 */     Window.scrollPane.setBorder(null);
/* 13 */     Window.scrollPane.getViewport().setBackground(Color.decode("#34495e"));
/* 14 */     Window.table.setBackground(Color.decode("#34495e"));
/* 15 */     Window.table.getTableHeader().setBackground(Color.decode("#34495e"));
/* 16 */     Window.table.getTableHeader().setForeground(Color.WHITE);
/* 17 */     Window.table.getTableHeader().setBorder(null);
/* 18 */     Window.buttonPanel.setBackground(Color.decode("#2c3e50"));
/*    */   }
/*    */ }