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
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class AddRecipe {

	JPanel main;
	JPanel list;
	JLabel lrecipename;
	JLabel lindgredient;
	JLabel lquantity;
	JTextField recipename;
	JFormattedTextField quantity; 
	JComboBox<String> indgredient; 
	JList<String> finallist;
	JButton addind;
	JButton submit;
	DefaultListModel<String> model;
	
	public AddRecipe() throws SQLException, ParseException {
		main=new JPanel(new FlowLayout());
		lrecipename=new JLabel("Enter recipie Name");
		lindgredient=new JLabel("Select indgredient");
		lquantity=new JLabel("Enter quantity");
		recipename=new JTextField(10);
		String[] s = Admin.getConnect().getAllIndgredients();
		indgredient=new JComboBox<String>(s);
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
		main.add(lrecipename);
		main.add(recipename);
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
					String name=recipename.getText();
					if (name.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Error: Recipe name cannot be empty", "Error Occurred", JOptionPane.ERROR_MESSAGE);;
					}
					else if (size==0) {
						JOptionPane.showMessageDialog(null, "Error: No indgrediets added", "Error Occurred", JOptionPane.ERROR_MESSAGE);;
					}
					else {
						String s[]=new String[size];
						for (int i = 0; i < size; i++) {
							s[i]=finallist.getModel().getElementAt(i);
						}
						try {
							Admin.getConnect().addRecipe(s,name);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
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
