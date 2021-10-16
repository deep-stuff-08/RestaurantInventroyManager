package com.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminConnector {

	Connection connection;
	Statement statement;
	PreparedStatement preparestmt;
	File file;
	
	public AdminConnector(String pass) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/InvMan", "Admin", pass);
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		file=new File("src/com/database/Recipe.txt");
	}
	
	public String[] getAllIndgredients() throws SQLException {
		ResultSet rs=statement.executeQuery("select name from tbl_quantity");
		rs.last();
		String s[]=new String[rs.getRow()];
		rs.beforeFirst();
		for (int i=0;rs.next();i++) {
			s[i]=rs.getString(1);
		}
		return s;
	}
	
	public String[] getAllRecipeIdsAndNames() throws SQLException {
		ResultSet rs=statement.executeQuery("select * from tbl_recipe");
		rs.last();
		String s[]=new String[rs.getRow()];
		rs.beforeFirst();
		for (int i=0;rs.next();i++) {
			s[i]=rs.getString(1)+"-"+rs.getString(2);
		}
		return s;
	}
	
	public void addRecipe(String[] items,String name) throws IOException, SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("insert into tbl_recipe(rec_name, rec_recipe) values(?, ?)");
		String s = items[0];
		for (int i = 1; i < items.length; i++) {
			s += "|";
			s += items[i];
		}
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, s);
		preparedStatement.executeUpdate();
	}
	
	public void addIndgredient(String indname, String quantity) throws SQLException {
		statement.execute("insert into tbl_quantity(name,quantity) values('"+indname+"','"+quantity+"')");
	}
	
	public void removeIndgredient(String name) throws SQLException {
		statement.execute("delete from tbl_quantity where name='"+name+"'");
	}

	public void removeRecipe(int id) throws SQLException, IOException {
		PreparedStatement ps = connection.prepareStatement("delete from tbl_recipe where rec_id=?");
		ps.setInt(1, id);
		ps.executeUpdate();
	}
	
	public void updateIndgredients(String nname,String oname) throws SQLException, IOException {
		statement.execute("update tbl_quantity set name='"+nname+"' where name='"+oname+"'");
		File temp=new File("src/com/database/Temp.txt");
		temp.createNewFile();
		BufferedReader br=new BufferedReader(new FileReader(file));
		BufferedWriter bw=new BufferedWriter(new FileWriter(temp));
		String line=br.readLine();
		while(line!=null) {
			String newline=line.replace(oname, nname);
			bw.append(newline);
			bw.append('\n');
			line=br.readLine();
		}
		br.close();
		bw.close();
		file.delete();
		temp.renameTo(file);
		file=temp;
	}
	public void updateRecipeName(String nname,String oname) throws SQLException {
		statement.execute("update tbl_recipe set rec_name='"+nname+"' where rec_name='"+oname+"'");
	}
	public void updateRecipe(String[] items,String id) throws SQLException, IOException {
		File temp=new File("src/com/database/Temp.txt");
		temp.createNewFile();
		BufferedReader br=new BufferedReader(new FileReader(file));
		BufferedWriter bw=new BufferedWriter(new FileWriter(temp));
		String line=br.readLine();
		int i=0;
		while(line!=null)
		{
			if(i>0) {
				bw.append('\n');
			}
			bw.append(line);
			bw.append('\n');
			if(!line.equals(id)) {
				line=br.readLine();
				bw.append(line);
			}
			else {
				bw.append(items[0]);
				for (int j = 1; j < items.length; j++) {
					bw.append('|');
					bw.append(items[j]);
				}
				br.readLine();
			}
			line=br.readLine();
			i++;
		}
		br.close();
		bw.close();
		file.delete();
		temp.renameTo(file);
		file=temp;
	}
	
	public void updateQuantity(String name, int qty) throws SQLException, FileNotFoundException {
		preparestmt=connection.prepareStatement("update tbl_quantity set quantity=quantity+? where name=?");
		preparestmt.setInt(1, qty);
		preparestmt.setString(2, name);
		preparestmt.executeUpdate();
	}
	
	public Object[][] getAllTblQuantity() throws SQLException {
		ArrayList<Object[]> arr = new ArrayList<Object[]>();
		ResultSet rs = statement.executeQuery("select name, quantity from tbl_quantity");
		while(rs.next()) {
			arr.add(new Object[]{rs.getString(1), rs.getInt(2)});
		}
		return arr.toArray(new Object[0][0]);
	}
	
	public Object[][] getAllTblRecipe() throws SQLException {
		ArrayList<Object[]> arr = new ArrayList<Object[]>();
		ResultSet rs = statement.executeQuery("select rec_id, rec_name, rec_recipe from tbl_recipe");
		while(rs.next()) {
			arr.add(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3)});
		}
		return arr.toArray(new Object[0][0]);
	}
}
