package view;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Administrator;

@SuppressWarnings("serial")
public class AdministratorGUI extends JFrame {
	private Administrator myAdmin;

	private JScrollPane tablePane;
	private JTextField txtSearchName;
	private JTable table;
	private JTable tableLabels;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministratorGUI frame = new AdministratorGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdministratorGUI(Administrator theAdmin) {
		myAdmin = theAdmin;
		createFrame();
	}
	
	private void createTable()
	{
		
	}
	
	private void createSearchBar()
	{
		txtSearchName = new JTextField();
		txtSearchName.setBounds(0, 0, 353, 26);
		getContentPane().add(txtSearchName);
		txtSearchName.setColumns(10);
		
		Button button = new Button("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(txtSearchName.getText()); //TODO: this is a placeholder
			}
		});
		button.setBounds(349, 0, 85, 26);
		getContentPane().add(button);
	}
	
	private void createFrame(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		createSearchBar();
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new String[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"First name", "Last name", "Email"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setBounds(0, 46, 434, 215);
		getContentPane().add(table);
		
		tableLabels = new JTable();
		tableLabels.setModel(new DefaultTableModel(
			new String[][] {
				{"First Name", "Last Name", "Email"},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class<String> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableLabels.setBounds(0, 26, 434, 16);
		getContentPane().add(tableLabels);
	}
}
