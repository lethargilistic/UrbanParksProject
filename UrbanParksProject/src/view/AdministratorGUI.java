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
	private final JScrollPane scrollPane = new JScrollPane();
	private JTable table;

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
		scrollPane.setBounds(0, 26, 434, 235);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
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
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"First Name", "Last Name", "Email"
			}
		));
		scrollPane.setViewportView(table);
	}
}
