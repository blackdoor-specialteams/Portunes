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
import auth.*;
import auth.Resolver.UserNotFoundException;

public class AHome {

	protected Shell shlPortunesAdministrator;
	private Text _USuname_tbox;
	private Text _USname_tbox;
	private Text ua_uname_text;
	private Text ua_name_text;
	private Text ua_pass_text;
	private Text ua_cpass_text;
	private AuthClient portclient;
	private String query;

	private Button _US_radio;
	private Button _DB_radio;
	private Button _DBquery_button;
	private Button add_button;
	private Button clear_button;
	private Button showResults_button;
	private Group _UserSrch_Grp;
	private Group _DBSrch_Grp;

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
		Display display = Display.getDefault();
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
		shlPortunesAdministrator = new Shell(SWT.CLOSE | SWT.TITLE
				| SWT.PRIMARY_MODAL);
		shlPortunesAdministrator.setSize(388, 411);
		shlPortunesAdministrator.setText("Portunes | Administrator Home");

		TabFolder _home_tabs = new TabFolder(shlPortunesAdministrator, SWT.NONE);
		_home_tabs.setBounds(0, 10, 382, 357);

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

		Label _USname_Label = new Label(_UserSrch_Grp, SWT.NONE);
		_USname_Label.setBounds(20, 65, 38, 15);
		_USname_Label.setText("Name: ");

		_USuname_tbox = new Text(_UserSrch_Grp, SWT.BORDER);
		_USuname_tbox.setBounds(81, 38, 248, 21);

		_USname_tbox = new Text(_UserSrch_Grp, SWT.BORDER);
		_USname_tbox.setBounds(60, 65, 269, 21);

		_DBSrch_Grp = new Group(_Search_comp, SWT.NONE);
		_DBSrch_Grp.setBounds(10, 155, 355, 62);

		_DBquery_button = new Button(_DBSrch_Grp, SWT.NONE);
		_DBquery_button.setBounds(108, 27, 126, 25);
		_DBquery_button.setText("Show query list");
		_DBquery_button.setEnabled(false);

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
		showResults_button.addSelectionListener(new DatabaseSearchButton());

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
		_NWcpw_lbl.setBounds(39, 115, 90, 15);
		_NWcpw_lbl.setText("Confirm Password:");

		ua_uname_text = new Text(_NW_grp, SWT.BORDER);
		ua_uname_text.setBounds(97, 27, 210, 21);

		ua_name_text = new Text(_NW_grp, SWT.BORDER);
		ua_name_text.setBounds(79, 54, 228, 21);

		ua_pass_text = new Text(_NW_grp, SWT.BORDER);
		ua_pass_text.setBounds(97, 87, 210, 21);

		ua_cpass_text = new Text(_NW_grp, SWT.BORDER);
		ua_cpass_text.setBounds(132, 112, 175, 21);

		Label _Err_label = new Label(composite_1, SWT.NONE);
		_Err_label.setBounds(25, 21, 244, 15);
		_Err_label.setText("Error Label right here ");

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

		MenuItem mntmChangeUser = new MenuItem(menu, SWT.NONE);
		mntmChangeUser.setText("Logout");

		MenuItem mntmExit = new MenuItem(menu, SWT.NONE);
		mntmExit.setText("Exit");

		MenuItem mntmEdit = new MenuItem(_home_menu, SWT.CASCADE);
		mntmEdit.setText("Edit");

		Menu menu_1 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_1);

		MenuItem mntmAbout = new MenuItem(_home_menu, SWT.NONE);
		mntmAbout.setText("About");

		MenuItem mntmHelp = new MenuItem(_home_menu, SWT.NONE);
		mntmHelp.setText("Help");

	}

	public class DatabaseSearchButton extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (_US_radio.getSelection()) {
				_USuname_tbox.setEnabled(true);
				_USname_tbox.setEnabled(true);
				_DBquery_button.setEnabled(false);
			} else if (_DB_radio.getSelection()) {
				_USuname_tbox.setEnabled(false);
				_USname_tbox.setEnabled(false);
				_DBquery_button.setEnabled(true);
			} else if (e.getSource() == showResults_button) {
				QResults results = new QResults(portclient);
				if (_US_radio.getSelection()) {
					results.open();
				} else if (_DB_radio.getSelection()) {
					// results.open();
				}
			} else if (e.getSource() == showResults_button) {

			}
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
									session.getName(), Hash.getSHA256(session
											.getPass().getBytes("UTF-8")));
						} catch (UnsupportedEncodingException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							portclient.exchange(newuser);
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
				ua_uname_text.clearSelection();
				ua_name_text.clearSelection();
				ua_pass_text.clearSelection();
				ua_cpass_text.clearSelection();
			}
		}
	}
}
