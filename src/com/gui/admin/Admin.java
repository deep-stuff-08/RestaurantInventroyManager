package com.gui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import com.database.AdminConnector;

public class Admin {
	JFrame frame;
	JTabbedPane tabs;
	JPanel indgredient;
	JPanel recipe;
	JPanel ptitle;
	JPanel recipename;
	JComboBox<String> option;
	JLabel 	title;
	static AdminConnector connect;
	AddRecipe addrec;
	AddIndgredients addind;
	RemIndgredient remind;
	RemRecipe remrec;
	UpdIndgredient updind;
	UpdRecipeName updrecnam;
	UpdRecipe updrec;
	public Admin() throws Exception {
		frame=new JFrame();
		title=new JLabel("Admin Page", SwingConstants.CENTER);
		title.setFont(new Font("Sans", Font.BOLD, 28));
		option=new JComboBox<String>(new String[]{"Add","Remove","Update"});
		ptitle=new JPanel(new BorderLayout());
		tabs=new JTabbedPane();
		connect=new AdminConnector("InvMan");
		addrec=new AddRecipe();
		addind=new AddIndgredients();
		remind=new RemIndgredient();
		remrec=new RemRecipe();
		updind=new UpdIndgredient();
		updrecnam=new UpdRecipeName();
		updrec=new UpdRecipe();
		ptitle.add(option,BorderLayout.LINE_END);
		ptitle.add(title,BorderLayout.CENTER);
		ptitle.setBorder(BorderFactory.createMatteBorder(0,0,5,0,Color.BLACK));
		option.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		frame.setLayout(new BorderLayout());
		option.addItemListener(getItemListner());
		createTabbedPaneAdd();
		frame.add(ptitle,BorderLayout.PAGE_START);
		frame.setResizable(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private ItemListener getItemListner() {
		ItemListener item=new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(option.getSelectedItem().equals("Add")) {
					createTabbedPaneAdd();
				}
				else if(option.getSelectedItem().equals("Remove")) {
					createTabbedPaneSub();
				}
				else {
					createTabbedPaneUpd();
				}
			}
		};
		return item;
	}
	
	public void createTabbedPaneSub() {
		frame.remove(tabs);
		tabs=new JTabbedPane();
		recipe=remrec.getMain();
		indgredient=remind.getMain();
		tabs.addTab("Remove Recipe", recipe);
		tabs.addTab("Remove Indgredient", indgredient);
		frame.add(tabs,BorderLayout.CENTER);
		frame.revalidate();
	}
	public void createTabbedPaneAdd() {
		frame.remove(tabs);
		tabs=new JTabbedPane();
		recipe=addrec.getMain();
		indgredient=addind.getMain();
		tabs.addTab("Add Recipe", recipe);
		tabs.addTab("Add Indgredient", indgredient);
		frame.add(tabs,BorderLayout.CENTER);
		frame.revalidate();
	}
	public void createTabbedPaneUpd() {
		frame.remove(tabs);
		tabs=new JTabbedPane();
		recipe=updrec.getMain();
		recipename=updrecnam.getMain();
		indgredient=updind.getMain();
		tabs.addTab("Update Recipe", recipe);
		tabs.addTab("Update Indgredient", indgredient);
		tabs.addTab("Update Recipe Name", recipename);
		frame.add(tabs,BorderLayout.CENTER);
		frame.revalidate();	
	}
	public static AdminConnector getConnect() {
		return connect;
	}
	
}
