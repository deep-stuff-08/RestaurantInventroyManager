package com.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Connector {
	Connection connection;
	Statement statement;
	PreparedStatement preparestmt;
	File file;
	public Connector() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/InvMan", "Employee", "");
		statement=connection.createStatement(ResultSet.CONCUR_READ_ONLY,ResultSet.TYPE_SCROLL_INSENSITIVE);
		file=new File("src/com/database/Recipe.txt");
	}
	
	private Integer[] getSelectedQuantity(String[] names) throws SQLException {
		ResultSet resultset=statement.executeQuery("select * from tbl_quantity");
		Integer[] s=new Integer[names.length];
		for(int i=0;i<names.length;) {
			resultset.next();
			if (names[i].equals(resultset.getString(1))) {
				s[i]=resultset.getInt(2);
				i++;
			}
			// Write rs.last() if SQL reached end of database reached exception occurs and the file is accurate
		}
		return s;
	}
	
	private boolean checkDatabase(Map<String, Integer> m) throws SQLException {
		Integer[] req=Arrays.copyOf(m.values().toArray(),m.values().toArray().length,Integer[].class);
		Integer[] sto=getSelectedQuantity(Arrays.copyOf(m.keySet().toArray(),m.keySet().toArray().length,String[].class));
		for(int i=0;i<req.length||i<sto.length;i++) {
			if(sto[i]<req[i]) {
				return false;
			}
		}
		return true;
	}
	
	private Map<String, Integer> getRecipeMap(String recipe) throws SQLException {
		StringTokenizer tokenizer=new StringTokenizer(recipe, "|");
		Map<String,Integer> rec=new TreeMap<String, Integer>();
		while (tokenizer.hasMoreElements()) {
			String name = tokenizer.nextToken();
			StringTokenizer st=new StringTokenizer(name, "-");
			String i=st.nextToken();
			String j=st.nextToken();
			rec.put(i, Integer.parseInt(j));
		}
		return rec;
	}
	
	public String getRecipe(String id) throws FileNotFoundException {
		Scanner sc=new Scanner(file);
		while (sc.hasNextLine()) {
			String line=sc.nextLine();
			String recipe=sc.nextLine();
			if (id.equals(line)) {
				sc.close();
				return recipe;
			}
		}
		sc.close();
		return "";
	}
	
	public boolean isQuantityAdd(String id) throws FileNotFoundException, SQLException {
		String a=getRecipe(id);
		if(a.equalsIgnoreCase(""))
			return false;
		return checkDatabase(getRecipeMap(a));
	}
	
	public String[] getRecipeIDs() throws SQLException, ClassNotFoundException {
		ResultSet resultset=statement.executeQuery("select rec_id from  tbl_recipe");
		resultset.last();
		String[] s=new String[resultset.getRow()];
		resultset.beforeFirst();
		for(int i=0;resultset.next();i++) {
			s[i]=resultset.getString(1);
		}
		return s;
	}
	
	public String[] getRecipeNames() throws SQLException, ClassNotFoundException {
		ResultSet resultset=statement.executeQuery("select rec_name from  tbl_recipe");
		resultset.last();
		String[] s=new String[resultset.getRow()];
		resultset.beforeFirst();
		for(int i=0;resultset.next();i++) {
			s[i]=resultset.getString(1);
		}
		return s;
	}
	
	public void updateDatabase(String id,String sign) throws SQLException, FileNotFoundException {
		preparestmt=connection.prepareStatement("update tbl_quantity set quantity=quantity"+sign+"? where name=?");
		String a=getRecipe(id);
		if(a.equalsIgnoreCase("")) {
			return;
		}
		Map<String, Integer> map=getRecipeMap(a);
		Set<String> s=map.keySet();
		Iterator<String> it=s.iterator();
		while (it.hasNext()) {
			String string = (String) it.next();
			preparestmt.setInt(1, map.get(string));
			preparestmt.setString(2, string);
		}
	}
	
}
