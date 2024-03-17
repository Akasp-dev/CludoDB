package com.connector.cludodb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

public class DatabaseConnection extends Thread {
	String address;
	String host;
	String pass;
	static Connection con;
	 
	public DatabaseConnection(String address, String host, String pass) throws SQLException {
		this.address = address;
		this.host = host;
		this.pass = pass;
		con = DriverManager.getConnection(this.address, this.host, this.pass);
	}
	public static Object[][] showTableData(String tableName) throws SQLException {
		Object[][] results = new Object[rowCount(tableName)][columnCount(tableName)];
		ResultSet rs = con.createStatement().executeQuery("select * from " + tableName);
					 
		int x = 0;
		while (rs.next()) {
			for (int i = tableName.equals("Dane_Kontaktowe") ? 2 : 3; i <= columnCount(tableName); i++) {
				int y = tableName.equals("Dane_Kontaktowe") ? 2 : 3;
				results[x][i - y] = rs.getString(i);
			}
			x++;   
		} 
		return results;
	}
	public static int rowCount(String tableName) throws SQLException {
		ResultSet rs = con.createStatement().executeQuery("select count(*) from " + tableName);
		rs.next();
		return rs.getInt(1);
	}
	private static int columnCount(String tableName) throws SQLException {
		return con.createStatement().executeQuery("select * from " + tableName).getMetaData().getColumnCount();
	}
	private static int getNumberOfTables() throws SQLException{
		ResultSet rs = con.createStatement().executeQuery("select count(*) as Total_Number from information_schema.tables WHERE table_schema = 'i7328_clients'");
		rs.next();
		return rs.getInt(1);
	}
	public static void addToDataBase(String tableName, String nazwa, String klucz, String wklad, String wysWk, String dof, String data) throws SQLException {
		String get_id = "(select id from Dane_Kontaktowe where nazwa_klienta='"+String.join(" ", tableName.split("_"))+"')";
		con.createStatement().executeUpdate("insert into " + tableName + "(id_klienta,nazwa_produktu, klucz_produktu, wklad_wlasny, wysokosc_wkladu, dofinansowanie, uwagi, data_zakupu, stan) values "
		+ "("+get_id+",'" + nazwa + "','" + klucz + "','" + wklad + "','" + wysWk + "','"+ dof + "','','" + data + "','Nieukończone');");
	}
	public static String[] getTableList() throws SQLException {
				 
		String[] list = new String[getNumberOfTables()];
		ResultSet rs = con.createStatement().executeQuery("show tables");
		int i = 0;
		while (rs.next()) {
			list[i] = rs.getString(1);
			i++;
		} 
		return list;
	}
	public static void removeRow(String tableName, String index) throws SQLException {
		con.createStatement().executeUpdate("delete from " + tableName + " where id=" + index);
	}
	public static void addClient(String name, String surname, String phone, String email) throws SQLException {
		con.createStatement().executeUpdate("insert into Dane_Kontaktowe (nazwa_klienta, nr_telefonu, email) values ('" + 
		name + " " + surname + "', '" + phone + "', '" + email + "')");
		con.createStatement().executeUpdate("create table " + name + "_" + surname + "(id int auto_increment primary key, id_klienta int not null, nazwa_produktu varchar(70) not null,klucz_produktu varchar(50), wklad_wlasny varchar(10) not null, wysokosc_wkladu varchar(20), dofinansowanie varchar(10) not null, uwagi varchar(250),data_zakupu varchar(30) not null, stan varchar(30) not null, foreign key(id_klienta) references Dane_Kontaktowe(id));");  
	}
	public static String returnRowId(String tableName, int rowIndex) throws SQLException {
		String id = "";
		String[][] results = new String[rowCount(tableName)][columnCount(tableName)];
		ResultSet rs = con.createStatement().executeQuery("select * from " + tableName);
		int x = 0;
		while (rs.next()) {
			for (int i = 1; i <= columnCount(tableName); i++) {
				results[x][i - 1] = rs.getString(i);
			}
			x++;
		} 
		return results[rowIndex][0];
	}
	public static void editRow(String tableName, String id, String name, String key, String own, String ownpr, String fund, String orderDate, String state) throws SQLException {
		con.createStatement().executeUpdate("update " + tableName + " set nazwa_produktu='" + name + "',klucz_produktu='" + 
		key + "',wklad_wlasny='" + 
		own + "',wysokosc_wkladu='" + 
		ownpr + "',dofinansowanie='" + 
		fund + "',data_zakupu='" + 
		orderDate + "', stan='"+ state + "' where id=" + id);
	}
	public static void editClientRow(String id, String name, String phone, String email) throws SQLException {
		con.createStatement().executeUpdate("update Dane_Kontaktowe set nazwa_klienta='" + name + "', nr_telefonu='" + phone + "',email='" + email + "' where id=" + id);
	}
	public static String getDesc(String tableName, String id) throws SQLException{
		ResultSet rs =  con.createStatement().executeQuery("select uwagi from "+tableName+" where id="+id);
		rs.next();
		return rs.getString(1);
	}
	public static void editDesc(String tableName, String text, String id) throws SQLException{
		con.createStatement().executeUpdate("update "+tableName+" set uwagi='" +text+ "' where id="+id);
	}
	public static int getAllRecordsNumber() throws SQLException{
		int result = 0;
		for(String table: getTableList()) {	   
			if(!table.equals("Dane_Kontaktowe")) {
				ResultSet rs = con.createStatement().executeQuery("select Dane_Kontaktowe.nazwa_klienta,"+table+".nazwa_produktu,"+table+".data_zakupu,"+table+".stan from "+table+" inner join Dane_Kontaktowe on Dane_Kontaktowe.id="+table+".id_klienta");
				while(rs.next()) {
					result++;
				}			   
			}   
		}
		return result;
	}
	public static Object[][] getAllClients() throws SQLException {
		Object[][] result = new Object[getAllRecordsNumber()][4];
		int y = 0;
		for(String table: getTableList()) {	   
			if(!table.equals("Dane_Kontaktowe")) {
				ResultSet rs = con.createStatement().executeQuery("select Dane_Kontaktowe.nazwa_klienta,"+table+".nazwa_produktu,"+table+".data_zakupu,"+table+".stan from "+table+" inner join Dane_Kontaktowe on Dane_Kontaktowe.id="+table+".id_klienta");
				while(rs.next()) {
					for(int i = 1; i <= 4; i++) {
						result[y][i-1] = rs.getString(i);
					}
					y++;
				}					   
			}   
		}  
		return result;
	}
}
