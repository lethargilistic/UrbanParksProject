package model;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	private JScrollPane tablePane = new JScrollPane();
	private JTextArea volunteerListArea;
	

	/**
	 * Create the frame.
	 */
	public ParkManagerGUI(ParkManager theManager) {
		this.myManager = theManager;
		createFrame();		
	}
	
	private void createFrame() {
		
		setTitle("Welcome " + myManager.getFirstName() + " " + myManager.getLastName() + "!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 475);
		getContentPane().setLayout(null);	
		
		tablePane = new JScrollPane();
		tablePane.setBounds(10, 10, 480, 345);
		getContentPane().add(tablePane);
	
		createTable();
		
		JButton newJobButton = new JButton("Create New Job");
		newJobButton.setFont(new Font("Arial", Font.PLAIN, 18));
		newJobButton.setBounds(133, 366, 209, 46);
		getContentPane().add(newJobButton);
		
		JScrollPane volunteerListPane = new JScrollPane();
		volunteerListPane.setBounds(515, 11, 324, 402);
		getContentPane().add(volunteerListPane);
		
		volunteerListArea = new JTextArea();
		volunteerListPane.setViewportView(volunteerListArea);
		volunteerListArea.setEditable(false);
		
	}		
	
	
	private void createTable() {
		String[] columnNames = {"ID",
                "Park",
                "Light",
                "Medium",
                "Heavy", "Start", "End"};		
		
		Object[][] data = myManager.getJobArray();
		jobTable = new JTable(data, columnNames);
		
		jobTable.getColumnModel().getColumn(0).setMaxWidth(30);
		jobTable.getColumnModel().getColumn(2).setMaxWidth(55);
		jobTable.getColumnModel().getColumn(3).setMaxWidth(55);
		jobTable.getColumnModel().getColumn(4).setMaxWidth(55);
		jobTable.getColumnModel().getColumn(5).setMaxWidth(120);
		jobTable.getColumnModel().getColumn(6).setMaxWidth(120);
		
		tablePane.setViewportView(jobTable);
		
		jobTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        int row = jobTable.getSelectedRow();
		        displayVolunteers(row);
		    }
		});
	}
	
	
	
	private void displayVolunteers(int theRow) {
		int jobID = (int) jobTable.getValueAt(theRow, 0);
		String volunteerString = myManager.getVolunteerString(jobID);
		volunteerListArea.setText(volunteerString);
	}
}
