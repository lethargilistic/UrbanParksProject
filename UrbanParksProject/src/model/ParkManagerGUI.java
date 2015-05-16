package model;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;

import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.Window.Type;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class ParkManagerGUI extends JFrame {
	private JTable jobTable;
	private ParkManager myManager;
	

	/**
	 * Create the frame.
	 */
	public ParkManagerGUI() {
		List<String> myParkList = new ArrayList<String>();
		String myPark = "Happy Park Land";
		myParkList.add(myPark);
		myManager = new ParkManager("moverby@gmail.com", "Mike", "Overby", myParkList);
		
		setTitle("Welcome!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 475);
		getContentPane().setLayout(null);
		
	
		createTable();
		

	
		
		JButton newJobButton = new JButton("Create New Job");
		newJobButton.setFont(new Font("Arial", Font.PLAIN, 18));
		newJobButton.setBounds(107, 367, 209, 46);
		getContentPane().add(newJobButton);
		
		JScrollPane volunteerListPane = new JScrollPane();
		volunteerListPane.setBounds(472, 11, 367, 402);
		getContentPane().add(volunteerListPane);
		
		JTextArea volunteerListArea = new JTextArea();
		volunteerListPane.setViewportView(volunteerListArea);
		volunteerListArea.setEditable(false);
		
	}
		
	
	
	private void createTable() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 452, 345);
		getContentPane().add(scrollPane);
		String[] columnNames = {"Job ID",
                "Park",
                "Light Slots",
                "Medium Slots",
                "Heavy Slots"};
		
		
		
		Object[][] data = myManager.getJobArray();
		
		jobTable = new JTable(data, columnNames);
		scrollPane.setViewportView(jobTable);
	}
}
