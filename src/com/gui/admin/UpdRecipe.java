package com.gui.admin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

public class UpdRecipe {
	JPanel main;
	JPanel list;
	JLabel lindgredient;
	JLabel lquantity;
	JFormattedTextField quantity; 
	JComboBox<String> indgredient; 
	JList<String> finallist;
	JComboBox<String> recname;
	JButton addind;
	JButton submit;
	DefaultListModel<String> model;
	
	public UpdRecipe() throws SQLException, ParseException {
		main=new JPanel(new FlowLayout());
		lindgredient=new JLabel("Select indgredient");
		lquantity=new JLabel("Enter quantity");
		String[] st1=Admin.getConnect().getAllIndgredients();
		indgredient=new JComboBox<String>(st1);
		String[] st2=Admin.getConnect().getAllRecipeIdsAndNames();
		recname=new JComboBox<String>(st2);
		NumberFormat numf=NumberFormat.getIntegerInstance();
		NumberFormatter nf=new NumberFormatter(numf);
		nf.setValueClass(Integer.class);
		nf.setMinimum(0);
		nf.setMaximum(Integer.MAX_VALUE);
		nf.setCommitsOnValidEdit(true);
		nf.setAllowsInvalid(false);
		quantity=new JFormattedTextField(nf);
		quantity.setText("0");
		quantity.setColumns(2);
		list=new JPanel(new FlowLayout());
		model=new DefaultListModel<String>();
		finallist=new JList<String>(model);
		addind=new JButton("Add Indgredient");
		addind.addActionListener(getAddIndAction());
		submit=new JButton("Submit");
		submit.addActionListener(getSubAction());
		main.add(recname);
		main.add(lindgredient);
		main.add(indgredient);
		main.add(lquantity);
		main.add(quantity);
		main.add(addind);
		list.add(finallist);
		main.add(list);
		main.add(submit);
	}
	
	private ActionListener getAddIndAction() {
		ActionListener ac=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String qua=quantity.getText().replaceAll(",", "");
				if(Integer.parseInt(qua)>0) {
					String s=indgredient.getSelectedItem()+"-"+qua;
					model.addElement(s);
					quantity.setText("0");
					indgredient.setSelectedIndex(0);
				}
				else {
					JOptionPane.showMessageDialog(null, "Error: Quantity cannot be zero", "Error Occurred", JOptionPane.ERROR_MESSAGE);;
				}
			}
		};
		return ac;
	}
	
	private ActionListener getSubAction() {
		ActionListener ac=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int a=JOptionPane.showConfirmDialog(null, "Are you sure you want to add this recipe?");
				if(a==JOptionPane.YES_OPTION) {
					int size=finallist.getModel().getSize();
					String name=(String)recname.getSelectedItem();
					name=name.substring(0, 5);
					String s[]=new String[size];
					for (int i = 0; i < size; i++) {
						s[i]=finallist.getModel().getElementAt(i);
					}
					try {
						Admin.getConnect().updateRecipe(s,name);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		return ac;
	}
	
	public JPanel getMain() {
		return main;
	}
}
