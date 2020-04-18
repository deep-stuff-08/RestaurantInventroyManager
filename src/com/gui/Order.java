package com.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.database.Connector;

public class Order {
	JFrame frame;
	JPanel pt;
	JPanel pm;
	JLabel title;
	JScrollPane scroll;
	Entity[] entities;
	Connector connect;
	
	public Order() throws Exception {
		frame=new JFrame("Deep's AJP Micro Project");
		frame.setLayout(new BorderLayout());
		pt=new JPanel(new FlowLayout());
		title=new JLabel("Deep's Pizza Place");
		connect=new Connector();
		title.setAlignmentX(SwingConstants.CENTER);
		title.setFont(new Font("sans", Font.BOLD, 32));
		pt.add(title);
		String names[]=connect.getRecipeNames();
		String id[]=connect.getRecipeIDs();
		entities=new Entity[names.length];
		int a=names.length/2;
		pm=new JPanel(new GridLayout(a,2));
		for (int i = 0; i < names.length; i++) {
			entities[i]=new Entity(names[i],id[i]);
			pm.add(entities[i]);
		}
		scroll=new JScrollPane(pm,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		frame.add(pt,BorderLayout.PAGE_START);
		frame.add(scroll,BorderLayout.CENTER);
		frame.setResizable(false);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
