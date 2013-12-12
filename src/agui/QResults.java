package agui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import auth.*;


public class QResults extends Shell {

	private AuthClient portclient;
	private Session session;

	private Table table;
	private String query;
	private String target;
	private Button done_button;
	private Button edit_button;
	private Display display;

	public QResults(Display d, String q, String t, AuthClient a, Session b) {
		super(d, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		display = d;
		query = q;
		target = t;
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
				// TODO add the done button functionality
			} else if (e.getSource() == edit_button) {
				AEdit editchild = new AEdit(display);
				editchild.open();
			}
		}
	}

	private void handleQuery() {
		table.setItemCount(0);
		try {
			switch (query) {
			case "history":
				HISTORY req = new HISTORY(target);
				req.adminName = session.getName();
				req.adminPW = session.getPassHash();
				req = (HISTORY) portclient.exchange(req);
				buildtable_HISTORY(req);
				break;
			case "getinfo":
				GETINFO req1 = new GETINFO(6, target);
				req1.adminName = session.getName();
				req1.adminPW = session.getPassHash();
				req1 = (GETINFO) portclient.exchange(req1);
				buildtable_GETINFO(req1);
				break;
			case "list":
				LIST req2 = new LIST(session.getName(), session.getPassHash());
				req2 = (LIST) portclient.exchange(req2);
				buildtable_LIST(req2);
				break;
			}

		} catch (Exception e) {

		}
	}


	private void buildtable_HISTORY(HISTORY r) {
		listofmaps(r.reply);
	}

	private void buildtable_LIST(LIST r) {

		listofmaps(r.reply);
	}

	private void buildtable_GETINFO(GETINFO r) {

		tablewithmap(r.reply);
	}

	private void listofmaps(List r) {
		List<Map<String, Object>> resultlist = r;

		List<TableColumn> columnlist = new ArrayList<TableColumn>();

		// sets up the columns and their names
		for (String key : resultlist.get(1).keySet()) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(key);
			column.setWidth(150);
			column.setMoveable(true);
			column.setResizable(true);
			columnlist.add(column);
		}
		int count = columnlist.size();
		String[] tableitemsetter = new String[count];
		int i = 0;
		for (Map<String, Object> tuplemap : resultlist) {
			TableItem item = new TableItem(table, SWT.NONE);
			for (TableColumn col : columnlist) {
				tableitemsetter[i] = tuplemap.get(col.getText()).toString();
				i++;
			}
			item.setText(tableitemsetter);
			i = 0;
		}
	}

	private void tablewithmap(Map r) {
		Map<String, Object> resultlist = r;

		List<TableColumn> columnlist = new ArrayList<TableColumn>();

		// sets up the columns and their names
		for (String key : resultlist.keySet()) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(key);
			column.setWidth(150);
			column.setMoveable(true);
			column.setResizable(true);
			columnlist.add(column);
		}
		int count = columnlist.size();
		String[] tableitemsetter = new String[count];
		int i = 0;
		TableItem item = new TableItem(table, SWT.NONE);
		for (TableColumn col : columnlist) {
			tableitemsetter[i] = resultlist.get(col.getText()).toString();
			i++;
		}
		item.setText(tableitemsetter);
		i = 0;
	}

	// for(String)

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
