package com.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminConnector {

	Connection connection;
	Statement statement;
	PreparedStatement preparestmt;
	File file;
	
	public AdminConnector(String pass) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/InvMan", "Admin", pass);
		statement=connection.createStatement(ResultSet.CONCUR_READ_ONLY,ResultSet.TYPE_SCROLL_INSENSITIVE);
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
		ResultSet rs=statement.executeQuery("select rec_id from tbl_recipe");
		rs.last();
		String rc=rs.getString(1);
		int n=Integer.parseInt(rc.substring(2))+1;
		String num;
		if(n<10) {
			num="RC00"+n;
		}
		else if (n<100) {
			num="RC0"+n;
		}
		else {
			num="RC"+n;
		}
		BufferedWriter bw=new BufferedWriter(new FileWriter(file, true));
		bw.append('\n');
		bw.append(num);
		bw.append('\n');
		bw.append(items[0]);
		for (int i = 1; i < items.length; i++) {
			bw.append('|');
			bw.append(items[i]);
		}
		bw.close();
		statement.execute("insert into tbl_recipe(rec_id,rec_name) values('"+num+"','"+name+"')");
	}
	
	public void addIndgredient(String indname, String quantity) throws SQLException {
		statement.execute("insert into tbl_quantity(name,quantity) values('"+indname+"','"+quantity+"')");
	}
	
	public void removeIndgredient(String name) throws SQLException {
		statement.execute("delete from tbl_quantity where name='"+name+"'");
	}

	public void removeRecipe(String name) throws SQLException, IOException {
		statement.execute("delete from tbl_recipe where rec_id='"+name+"'");
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
			if(!line.equals(name)) {
				bw.append(line);
				bw.append('\n');
				line=br.readLine();
				bw.append(line);
			}
			else {
				line=br.readLine();
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
}
