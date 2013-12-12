package agui;

import java.io.UnsupportedEncodingException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import util.Hash;
import util.Sanitizer;
import auth.*;
import auth.Resolver.UserNotFoundException;

public class AHome {

	protected Shell shlPortunesAdministrator;
	private Text _USuname_tbox;
	private Text ua_uname_text;
	private Text ua_name_text;
	private Text ua_pass_text;
	private Text ua_cpass_text;
	private AuthClient portclient;

	private Button _US_radio;
	private Button _DB_radio;
	private Button _DBquery_button;
	private Button add_button;
	private Button clear_button;
	private Button showResults_button;
	private Group _UserSrch_Grp;
	private Group _DBSrch_Grp;
	private Display display;

	private Button gethist_radio;
	private Button getinfo_radio;
	private Button list_radio;
	private String query;

	private Session session;

	public AHome(AuthClient a, Session tempsession) {
		portclient = a;
		session = tempsession;
		new Sanitizer();
	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shlPortunesAdministrator.open();
		shlPortunesAdministrator.layout();
		while (!shlPortunesAdministrator.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlPortunesAdministrator = new Shell(SWT.CLOSE | SWT.TITLE);
		shlPortunesAdministrator.setSize(398, 420);
		shlPortunesAdministrator.setText("Portunes | Administrator Home");

		TabFolder _home_tabs = new TabFolder(shlPortunesAdministrator, SWT.NONE);
		_home_tabs.setBounds(5, 10, 382, 357);

		TabItem _Seach_tab = new TabItem(_home_tabs, SWT.NONE);
		_Seach_tab.setText("Search");

		Composite _Search_comp = new Composite(_home_tabs, SWT.NONE);
		_Seach_tab.setControl(_Search_comp);

		_UserSrch_Grp = new Group(_Search_comp, SWT.NONE);
		_UserSrch_Grp.setBounds(10, 27, 355, 96);

		Label _USInstrc_Label = new Label(_UserSrch_Grp, SWT.NONE);
		_USInstrc_Label.setBounds(10, 10, 335, 15);
		_USInstrc_Label
				.setText("Search by either an exact  username or the user's exact name. ");

		Label _USuname_Label = new Label(_UserSrch_Grp, SWT.NONE);
		_USuname_Label.setBounds(20, 41, 55, 15);
		_USuname_Label.setText("Username: ");

		_USuname_tbox = new Text(_UserSrch_Grp, SWT.BORDER);
		_USuname_tbox.setBounds(81, 38, 248, 21);

		gethist_radio = new Button(_UserSrch_Grp, SWT.RADIO);
		gethist_radio.setBounds(10, 70, 83, 16);
		gethist_radio.setText("Get History.");
		gethist_radio.setSelection(true);

		getinfo_radio = new Button(_UserSrch_Grp, SWT.RADIO);
		getinfo_radio.setBounds(116, 70, 163, 16);
		getinfo_radio.setText("Get Information.");

		_DBSrch_Grp = new Group(_Search_comp, SWT.NONE);
		_DBSrch_Grp.setBounds(10, 155, 355, 62);

		_DBquery_button = new Button(_DBSrch_Grp, SWT.NONE);
		_DBquery_button.setBounds(108, 27, 126, 25);
		_DBquery_button.setText("Show query list");
		_DBquery_button.setEnabled(false);
		_DBquery_button.addSelectionListener(new AdvancedQueryListner());

		_US_radio = new Button(_Search_comp, SWT.RADIO);
		_US_radio.setBounds(10, 10, 130, 16);
		_US_radio.setText("Search For User");
		_US_radio.setSelection(true);
		_US_radio.addSelectionListener(new DatabaseSearchButton());

		_DB_radio = new Button(_Search_comp, SWT.RADIO);
		_DB_radio.setBounds(10, 133, 257, 16);
		_DB_radio.setText("Seach database using advanced queries");
		_DB_radio.addSelectionListener(new DatabaseSearchButton());

		showResults_button = new Button(_Search_comp, SWT.NONE);
		showResults_button.setBounds(235, 282, 130, 39);
		showResults_button.setText("Show Results");

		list_radio = new Button(_Search_comp, SWT.RADIO);
		list_radio.setBounds(10, 244, 240, 16);
		list_radio.setText("Users Administrated by me.");
		showResults_button.addSelectionListener(new ResultButton());

		TabItem _Add_tab = new TabItem(_home_tabs, SWT.NONE);
		_Add_tab.setText("Add New User");

		Composite composite_1 = new Composite(_home_tabs, SWT.NONE);
		_Add_tab.setControl(composite_1);

		Group _NW_grp = new Group(composite_1, SWT.NONE);
		_NW_grp.setText("New User Information");
		_NW_grp.setBounds(10, 46, 354, 153);

		Label _NWunname_lbl = new Label(_NW_grp, SWT.NONE);
		_NWunname_lbl.setBounds(39, 33, 55, 15);
		_NWunname_lbl.setText("Username:");

		Label _NWname_lbl = new Label(_NW_grp, SWT.NONE);
		_NWname_lbl.setBounds(39, 55, 34, 15);
		_NWname_lbl.setText("Name:");

		Label _NWpw_lbl = new Label(_NW_grp, SWT.NONE);
		_NWpw_lbl.setBounds(39, 90, 55, 15);
		_NWpw_lbl.setText("Password:");

		Label _NWcpw_lbl = new Label(_NW_grp, SWT.NONE);
		_NWcpw_lbl.setBounds(39, 115, 100, 15);
		_NWcpw_lbl.setText("Confirm Password:");

		ua_uname_text = new Text(_NW_grp, SWT.BORDER);
		ua_uname_text.setBounds(97, 27, 210, 21);

		ua_name_text = new Text(_NW_grp, SWT.BORDER);
		ua_name_text.setBounds(79, 54, 228, 21);

		ua_pass_text = new Text(_NW_grp, SWT.BORDER);
		ua_pass_text.setBounds(97, 87, 210, 21);

		ua_cpass_text = new Text(_NW_grp, SWT.BORDER);
		ua_cpass_text.setBounds(145, 112, 162, 21);

		add_button = new Button(composite_1, SWT.NONE);
		add_button.setBounds(247, 261, 117, 52);
		add_button.setText("Add User");
		add_button.addSelectionListener(new UserAddListener());

		clear_button = new Button(composite_1, SWT.NONE);
		clear_button.setBounds(10, 261, 117, 52);
		clear_button.setText("Clear");
		clear_button.addSelectionListener(new UserAddListener());

		Menu _home_menu = new Menu(shlPortunesAdministrator, SWT.BAR);
		shlPortunesAdministrator.setMenuBar(_home_menu);

		MenuItem mntmFile = new MenuItem(_home_menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu = new Menu(mntmFile);
		mntmFile.setMenu(menu);

		MenuItem mntmExit = new MenuItem(menu, SWT.NONE);
		mntmExit.setText("Exit");
		mntmExit.addSelectionListener(new MenuListener());

	}

	public class DatabaseSearchButton extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (_US_radio.getSelection()) {
				getinfo_radio.setEnabled(true);
				gethist_radio.setEnabled(true);
				_USuname_tbox.setEnabled(true);
				_DBquery_button.setEnabled(false);
			} else if (_DB_radio.getSelection()) {
				_USuname_tbox.setEnabled(false);
				_DBquery_button.setEnabled(true);
				getinfo_radio.setEnabled(false);
				gethist_radio.setEnabled(false);
			} else if (list_radio.getSelection()) {
				_USuname_tbox.setEnabled(false);
				_DBquery_button.setEnabled(false);
				getinfo_radio.setEnabled(false);
				gethist_radio.setEnabled(false);
			}
		}
	}
	public class AdvancedQueryListner extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			AadvancedQuery newwindow = new AadvancedQuery(display);
			newwindow.open();
			query = newwindow.query; 
		}
	}

	public class ResultButton extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			QResults results = null;
			String tempname = _USuname_tbox.getText();
			if(_US_radio.getSelection()){
			if (gethist_radio.getSelection()
					&& Sanitizer.isCleanInput(tempname)) {
				results = new QResults(display, "history", tempname,
						portclient, session);
				results.open();
			} else if (getinfo_radio.getSelection()
					&& Sanitizer.isCleanInput(tempname)) {
				results = new QResults(display, "getinfo", tempname,
						portclient, session);
				results.open();
			}  else {
				MessageBox messageBox = new MessageBox(
						shlPortunesAdministrator, SWT.ICON_ERROR);
				messageBox.setMessage("Invalid Input(s).");
				messageBox.open();
			}

		}
			else if (list_radio.getSelection()) {
				results = new QResults(display, "list", tempname, portclient,
						session);
				results.open();
			}
			else if (_DB_radio.getSelection()) {
				results = new QResults(display, "arb", query, portclient,
						session);
				results.open();
			}
	}
	}

	public class MenuListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			display.dispose();

		}
	}

	public class UserAddListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (e.getSource() == add_button) {
				String username = ua_uname_text.getText();
				String name = ua_name_text.getText();
				String pass = ua_pass_text.getText();
				String confirm = ua_cpass_text.getText();

				if (pass.equals(confirm)) {
					if (Sanitizer.isCleanInput(username)
							&& Sanitizer.isCleanInput(name)
							&& Sanitizer.isCleanInput(pass)) {
						ADD newuser = null;
						try {
							newuser = new ADD(username, name,
									Hash.getSHA256(pass.getBytes("UTF-8")),
									session.getName(), session.getPassHash());

						} catch (UnsupportedEncodingException e2) {
							System.out.println("nope");
							e2.printStackTrace();
						}
						try {
							System.out.println(portclient.exchange(newuser));
						} catch (UserNotFoundException e1) {
							System.out.println("did not go through, sorry");
							e1.printStackTrace();
						}
					} else {
						MessageBox messageBox = new MessageBox(
								shlPortunesAdministrator, SWT.ICON_ERROR);
						messageBox.setMessage("Invalid Input(s).");
						messageBox.open();
					}

				} else {
					MessageBox messageBox = new MessageBox(
							shlPortunesAdministrator, SWT.ICON_ERROR);
					messageBox.setMessage("Passwords do not match.");
					messageBox.open();

				}
			} else if (e.getSource() == clear_button) {
				ua_uname_text.setText("");
				ua_name_text.setText("");
				ua_pass_text.setText("");
				ua_cpass_text.setText("");
			}
		}
	}
}
