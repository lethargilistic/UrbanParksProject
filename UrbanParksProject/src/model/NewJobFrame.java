package model;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.Color;


public class NewJobFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public NewJobFrame() {
		setTitle("New Job Creation");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 314, 561);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox parkComboBox = new JComboBox();
		parkComboBox.setBounds(31, 56, 136, 20);
		contentPane.add(parkComboBox);
		
		JLabel selectParkLabel = new JLabel("Select a Park");
		selectParkLabel.setBounds(31, 37, 85, 14);
		contentPane.add(selectParkLabel);
		
		JLabel volunteersLabel = new JLabel("Volunteers");
		volunteersLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		volunteersLabel.setBounds(31, 121, 104, 14);
		contentPane.add(volunteersLabel);
		
		JLabel lightLabel = new JLabel("Light");
		lightLabel.setBounds(31, 146, 46, 14);
		contentPane.add(lightLabel);
		
		JLabel mediumLabel = new JLabel("Medium");
		mediumLabel.setBounds(98, 146, 46, 14);
		contentPane.add(mediumLabel);
		
		JLabel heavyLabel = new JLabel("Heavy");
		heavyLabel.setBounds(180, 146, 46, 14);
		contentPane.add(heavyLabel);
		
		JSpinner lightSpinner = new JSpinner();
		lightSpinner.setBounds(31, 171, 40, 20);
		contentPane.add(lightSpinner);
		
		JSpinner mediumSpinner = new JSpinner();
		mediumSpinner.setBounds(98, 171, 40, 20);
		contentPane.add(mediumSpinner);
		
		JSpinner heavySpinner = new JSpinner();
		heavySpinner.setBounds(180, 171, 40, 20);
		contentPane.add(heavySpinner);
		
		JLabel startDateLabel = new JLabel("Start Date");
		startDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		startDateLabel.setBounds(31, 227, 104, 14);
		contentPane.add(startDateLabel);
		
		JLabel endDateLabel = new JLabel("End Date");
		endDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		endDateLabel.setBounds(31, 327, 104, 14);
		contentPane.add(endDateLabel);
		
		JLabel startMonthLabel = new JLabel("Month");
		startMonthLabel.setBounds(31, 252, 46, 14);
		contentPane.add(startMonthLabel);
		
		JLabel startDayLabel = new JLabel("Day");
		startDayLabel.setBounds(98, 252, 46, 14);
		contentPane.add(startDayLabel);
		
		JLabel startYearLabel = new JLabel("Year");
		startYearLabel.setBounds(154, 252, 46, 14);
		contentPane.add(startYearLabel);
		
		JComboBox startMonthComboBox = new JComboBox();
		startMonthComboBox.setBounds(31, 277, 28, 20);
		contentPane.add(startMonthComboBox);
		
		JComboBox startDayComboBox = new JComboBox();
		startDayComboBox.setBounds(98, 277, 28, 20);
		contentPane.add(startDayComboBox);
		
		JComboBox startYearComboBox = new JComboBox();
		startYearComboBox.setBounds(154, 277, 28, 20);
		contentPane.add(startYearComboBox);
		
		JLabel endMonthLabel = new JLabel("Month");
		endMonthLabel.setBounds(31, 352, 46, 14);
		contentPane.add(endMonthLabel);
		
		JLabel endDayLabel = new JLabel("Day");
		endDayLabel.setBounds(98, 352, 46, 14);
		contentPane.add(endDayLabel);
		
		JLabel endYearLabel = new JLabel("Year");
		endYearLabel.setBounds(154, 352, 46, 14);
		contentPane.add(endYearLabel);
		
		JComboBox endMonthComboBox = new JComboBox();
		endMonthComboBox.setBounds(31, 377, 28, 20);
		contentPane.add(endMonthComboBox);
		
		JComboBox endDayComboBox = new JComboBox();
		endDayComboBox.setBounds(98, 377, 28, 20);
		contentPane.add(endDayComboBox);
		
		JComboBox endYearComboBox = new JComboBox();
		endYearComboBox.setBounds(154, 377, 28, 20);
		contentPane.add(endYearComboBox);
		
		JButton createJobButton = new JButton("Create Job");
		createJobButton.setBounds(31, 476, 89, 23);
		contentPane.add(createJobButton);
		
		JLabel errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(31, 451, 46, 14);
		contentPane.add(errorLabel);
	}
}
