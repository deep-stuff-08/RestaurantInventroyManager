package com.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
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
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/InvMan", "Employee", "Inv808");
		statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
	
	private ArrayList<String> checkDatabase(Map<String, Integer> m) throws SQLException {
		ArrayList<String> unavailableList = new ArrayList<String>();
		String[] req=Arrays.copyOf(m.keySet().toArray(),m.keySet().toArray().length,String[].class);
		Integer[] sto=getSelectedQuantity(req);
		for(int i=0;i<req.length||i<sto.length;i++) {
			if(sto[i]<m.get(req[i])) {
				unavailableList.add(req[i]);
			}
		}
		return unavailableList;
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
	
	public String getRecipe(int id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("select rec_recipe from tbl_recipe where rec_id = ?");
		ps.setInt(1, id);
		ResultSet resultset = ps.executeQuery();
		resultset.next();
		String s=resultset.getString(1);
		return s;
	}
	
	public ArrayList<String> isQuantityAdd(int id) throws FileNotFoundException, SQLException {
		String a=getRecipe(id);
		if(a.equalsIgnoreCase(""))
			return new ArrayList<String>();
		return checkDatabase(getRecipeMap(a));
	}
	
	public int[] getRecipeIDs() throws SQLException, ClassNotFoundException {
		ResultSet resultset=statement.executeQuery("select rec_id from  tbl_recipe");
		resultset.last();
		int[] s=new int[resultset.getRow()];
		resultset.beforeFirst();
		for(int i=0;resultset.next();i++) {
			s[i]=resultset.getInt(1);
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
	
	public void updateDatabase(int id,String sign) throws SQLException, FileNotFoundException {
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
			preparestmt.executeUpdate();
		}
	}
}
