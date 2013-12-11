package agui;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import util.Misc;
import auth.*;

public class QResults extends Shell{

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


	public QResults(Display d,String q,AuthClient a, Session b) {
		super(d,SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		display = d;
		query = q;
		portclient = a;
		session = b;
		
		createContents();
	}

	/**
	 * Create contents of the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {

		setSize(788, 437);
		setText("Results");

		Composite _Results_comp = new Composite(this, SWT.NONE);
		_Results_comp.setBounds(0, 0, 772, 398);

		done_button = new Button(_Results_comp, SWT.NONE);
		done_button.setBounds(626, 344, 136, 54);
		done_button.setText("Done");
		done_button.addSelectionListener(new Choicelistener());

		edit_button = new Button(_Results_comp, SWT.NONE);
		edit_button.setBounds(10, 344, 128, 54);
		edit_button.setText("Edit Selected");
		edit_button.addSelectionListener(new Choicelistener());

		table = new Table(_Results_comp, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.VIRTUAL);
		table.addListener(SWT.Selection, new Tablelistener());
		table.setBounds(10, 10, 752, 328);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		handleQuery();

		

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
		// // {
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
				while (!display.readAndDispatch()) 
					display.sleep();
			} else if (e.getSource() == edit_button) {
				AEdit editchild = new AEdit(display);
				editchild.open();
			}
		}
	}

	private void handleQuery() {
		table.setItemCount(0);
		
		try {
		//	portclient.e
						connect = DriverManager.getConnection("jdbc:mysql://"
					+ serverAddress + ":" + PORT + "/" + DATABASE, USERNAME,
					PASSWORD);
			System.out.println("Connecting succesfully");
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);

			buildTable(resultSet);

//			while (resultSet.next()) {
//				TableItem item = new TableItem(table, SWT.NONE);
//				item.setText(new String[] { resultSet.getString(1),
//						resultSet.getString(2), resultSet.getString(3),
//						resultSet.getString(4) });
//			}

			connect.close();
		} catch (SQLException e) {
			System.out.println("Cannot connect to database server");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}

	private void buildTable(ResultSet rs) {
		// List<TableColumn> tablecollist = new ArrayList<TableColumn>();

		ResultSetMetaData metaData = null;
		try {
			metaData = resultSet.getMetaData();
			int count = metaData.getColumnCount();

			TableColumn[] columns = new TableColumn[count + 1];

			for (int i = 0; i < count; i++) {
				TableColumn column = new TableColumn(table, SWT.NONE);
				column.setText(metaData.getColumnLabel(i +1));
				column.setWidth(150);
				column.setMoveable(true);
				column.setResizable(true);
				columns[i] = column;
			}
			
			String[] tableitemsetter = new String[count];
			
			while (resultSet.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				for(int j = 0; j < count; j++){
					tableitemsetter[j] = resultSet.getString(j+1);
				}
					
				item.setText(tableitemsetter);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// try {
		// metaData = resultSet.getMetaData();
		// int count = metaData.getColumnCount(); //number of column
		//
		// for (int i = 1; i <= count; i++)
		// {
		// tablecollist.add(new TableColumn());
		// tablecollist.get(i).setWidth(100);
		// tablecollist.get(i).setText(metaData.getColumnLabel(i));
		// columnName[i-1] = metaData.getColumnLabel(i);
		// }
		//
		// String columnName[] = new String[count];
		//
		// for (int i = 1; i <= count; i++)
		// {
		// columnName[i-1] = metaData.getColumnLabel(i);
		// }
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}
	
	private void buildTablewithRequest(LIST r){
		List<Map<String, Object>> resultlist = null;
		
		resultlist = r.reply;
		int count = resultlist.size();
		
		List<TableColumn> columnlist = new ArrayList<TableColumn>();

		int i = 0;
		for ( String key : resultlist.get(1).keySet() ) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(key);
			column.setWidth(150);
			column.setMoveable(true);
			column.setResizable(true);
			columnlist.add(column);
		}
		
	//	for(String)
		
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
