package com.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.database.Connector;

@SuppressWarnings("serial")
public class Entity extends JPanel {
	
	final static String positive="+";
	final static String negative="-";
	JLabel name;
	JButton add;
	JButton sub;
	JTextField quantity;
	JPanel pn;
	JPanel ps;
	Font f;
	String id;
	Connector con;
	
	
	private void createActionListner() {
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (con.isQuantityAdd(id)) {
						quantity.setText(Integer.toString(Integer.parseInt(quantity.getText())+1));		
						con.updateDatabase(id, Entity.negative);
					}
					else {
						JOptionPane.showMessageDialog(null, "Not enough indgredients");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		sub.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(quantity.getText())>0) {
					quantity.setText(Integer.toString(Integer.parseInt(quantity.getText())-1));
					try {
						con.updateDatabase(id, Entity.positive);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	public Entity(String text, String id) throws Exception{
		name=new JLabel(text);
		add=new JButton("+");
		sub=new JButton("-");
		quantity=new JTextField(1);
		con=new Connector();
		this.id=id;
		createActionListner();
		quantity.setText("0");
		f=new Font("sans", Font.BOLD, 20);
		add.setMargin(new Insets(0, 1, 0, 1));
		sub.setMargin(new Insets(0, 6, 0, 6));
		name.setFont(f);
		add.setFont(f);
		sub.setFont(f);
		quantity.setFont(f);
		quantity.setAlignmentX(SwingConstants.CENTER);
		ps=new JPanel();
		pn=new JPanel();
		ps.setLayout(new FlowLayout());
		pn.setLayout(new FlowLayout(FlowLayout.LEFT,20,10));
		setLayout(new BorderLayout());
		pn.add(name);
		ps.add(add);
		ps.add(quantity);
		ps.add(sub);
		add(ps,BorderLayout.LINE_END);
		add(pn,BorderLayout.CENTER);
	}
}
