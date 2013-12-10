package agui;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableColumn;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;

import auth.*;

public class QResults {

	protected Shell qresultshell;
	protected Shell parentshell;
	private AuthClient portclient;
	private static java.sql.Connection connect;
	private static java.sql.Statement statement;
	private static ResultSet resultSet;

	private static final String serverAddress = "vodkapi.dyndns.info";
	private static final int PORT = 3306;
	private static final String DATABASE = "Portunes";
	private static String query = "Select * from User";
	private static final String USERNAME = "nate";
	private static final String PASSWORD = "pass";
	private Table table;
	private Session session;
	
	private Button done_button;
	private Button edit_button;
	private Display display;

	public QResults(AuthClient a,Session b) {
		portclient = a;
		session = b;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = display.getDefault();
		createContents();
		qresultshell.open();
		qresultshell.layout();
		while (!qresultshell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {

		qresultshell = new Shell();
		qresultshell.setSize(788, 437);
		qresultshell.setText("Results");

		Composite _Results_comp = new Composite(qresultshell, SWT.NONE);
		_Results_comp.setBounds(0, 0, 772, 398);

		done_button = new Button(_Results_comp, SWT.NONE);
		done_button.setBounds(626, 334, 136, 54);
		done_button.setText("Done");
		done_button.addSelectionListener(new Choicelistener());

		edit_button = new Button(_Results_comp, SWT.NONE);
		edit_button.setBounds(10, 334, 128, 54);
		edit_button.setText("Edit Selected");
		edit_button.addSelectionListener(new Choicelistener());

		table = new Table(_Results_comp, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		table.addListener(SWT.Selection, new Tablelistener());
		table.setBounds(10, 0, 752, 328);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		handleQuery();

		// table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
		// 1));
		// {
		// TableColumn tblclmnNos = new TableColumn(table, SWT.NONE);
		// tblclmnNos.setWidth(100);
		// tblclmnNos.setText("Nos");
		// }
		// {
		// TableColumn tblclmnEno = new TableColumn(table, SWT.NONE);
		// tblclmnEno.setWidth(100);
		// tblclmnEno.setText("Admin Type");
		// }
		// {
		// TableColumn tblclmnEname = new TableColumn(table, SWT.NONE);
		// tblclmnEname.setWidth(100);
		// tblclmnEname.setText("Admin Name");
		// }
		// {
		// TableColumn tblclmnAge = new TableColumn(table, SWT.NONE);
		// tblclmnAge.setWidth(100);
		// tblclmnAge.setText("Login Name");
		// }
		// {
		// TableColumn tblclmnPosition = new TableColumn(table, SWT.NONE);
		// tblclmnPosition.setWidth(100);
		// tblclmnPosition.setText("Login Password");
		// }

	}

	public class Tablelistener implements Listener {
		@Override
		public void handleEvent(org.eclipse.swt.widgets.Event event) {

		}
	}
	
	public class Choicelistener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (e.getSource() == done_button) {
				 display.dispose();
			} else if (e.getSource() == edit_button) {

			}
		}
	}

	private void handleQuery() {
		table.setItemCount(0);
		try {
			connect = DriverManager.getConnection("jdbc:mysql://"
					+ serverAddress + ":" + PORT + "/" + DATABASE, USERNAME,
					PASSWORD);
			System.out.println("Connecting succesfully");
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			
			buildTable(resultSet);
			
			
			
			while (resultSet.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { resultSet.getString(1),
						resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4)});
			}
			
			
			connect.close();
		} catch (SQLException e) {
			System.out.println("Cannot connect to database server");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	private void buildTable(ResultSet rs){
		List<TableColumn> tablecollist = new ArrayList<TableColumn>();
		
		ResultSetMetaData metaData = null;
		try {
			metaData = resultSet.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			String columnName[] = new String[count];

			for (int i = 1; i <= count; i++)
			{
			   columnName[i-1] = metaData.getColumnLabel(i); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
