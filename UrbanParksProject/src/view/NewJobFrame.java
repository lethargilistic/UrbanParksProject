package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JSpinner;
import javax.swing.JButton;

import java.awt.Color;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import model.ParkManager;


public class NewJobFrame extends JFrame {

	private JPanel contentPane;
	private ParkManager myManager;

	/**
	 * Create the frame.
	 */
	public NewJobFrame(ParkManager theManager) {
		this.myManager = theManager;
		createFrame();
	}
	
	private void createFrame() {		
		
		setTitle("New Job Creation");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 314, 561);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		createParkSelector();
		createVolunteerSelector();
		createDateSelector();
		createJobButton();
	}
	
	private void createParkSelector() {
		
		JComboBox<String> parkComboBox = new JComboBox<String>();
		parkComboBox.setBounds(31, 56, 195, 20);
		contentPane.add(parkComboBox);
		
		JLabel parkLabel = new JLabel("Park");
		parkLabel.setBounds(31, 37, 85, 14);
		contentPane.add(parkLabel);
		
	}
	
	private void createVolunteerSelector() {
		
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
		
	}
	
	private void createDateSelector() {
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
		startYearLabel.setBounds(167, 252, 46, 14);
		contentPane.add(startYearLabel);
		
		Integer[] monthList = new Integer[12];
		Integer[] dayList = new Integer[31];
		
		for(int i = 0; i < 32; i++) {
			if(i < 13) monthList[i] = i;
			dayList[i] = i;
		}
		
		JComboBox<Integer> startMonthComboBox = new JComboBox<Integer>();
		startMonthComboBox.setModel(new DefaultComboBoxModel<Integer>(monthList));
		startMonthComboBox.setBounds(31, 277, 40, 20);
		contentPane.add(startMonthComboBox);
		
		JComboBox<Integer> startDayComboBox = new JComboBox<Integer>();
		startDayComboBox.setModel(new DefaultComboBoxModel<Integer>(dayList));
		startDayComboBox.setBounds(98, 277, 40, 20);
		contentPane.add(startDayComboBox);
		
		JComboBox<Integer> startYearComboBox = new JComboBox<Integer>();
		startYearComboBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {2015, 2016, 2017, 2018}));
		startYearComboBox.setBounds(167, 277, 59, 20);
		contentPane.add(startYearComboBox);
		
		JLabel endMonthLabel = new JLabel("Month");
		endMonthLabel.setBounds(31, 352, 46, 14);
		contentPane.add(endMonthLabel);
		
		JLabel endDayLabel = new JLabel("Day");
		endDayLabel.setBounds(98, 352, 46, 14);
		contentPane.add(endDayLabel);
		
		JLabel endYearLabel = new JLabel("Year");
		endYearLabel.setBounds(167, 352, 46, 14);
		contentPane.add(endYearLabel);
		
		JComboBox<Integer> endMonthComboBox = new JComboBox<Integer>();
		endMonthComboBox.setModel(new DefaultComboBoxModel<Integer>(monthList));
		endMonthComboBox.setBounds(31, 377, 40, 20);
		contentPane.add(endMonthComboBox);
		
		JComboBox<Integer> endDayComboBox = new JComboBox<Integer>();
		endDayComboBox.setModel(new DefaultComboBoxModel<Integer>(dayList));
		endDayComboBox.setBounds(98, 377, 40, 20);
		contentPane.add(endDayComboBox);
		
		JComboBox<Integer> endYearComboBox = new JComboBox<Integer>();
		endYearComboBox.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {2015, 2016, 2017, 2018}));
		endYearComboBox.setBounds(167, 377, 59, 20);
		contentPane.add(endYearComboBox);
		
	}
	
	private void createJobButton() {
		
		JButton createJobButton = new JButton("Create Job");
		createJobButton.setBounds(31, 476, 113, 23);
		contentPane.add(createJobButton);
		
		JLabel errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(31, 414, 257, 51);
		contentPane.add(errorLabel);
	}
}
