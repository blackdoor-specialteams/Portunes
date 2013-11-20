package agui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
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

public class AHome {

	protected Shell shlPortunesAdministrator;
	private Text text;
	private Text text_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AHome window = new AHome();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
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
		shlPortunesAdministrator = new Shell();
		shlPortunesAdministrator.setSize(393, 410);
		shlPortunesAdministrator.setText("Portunes | Administrator Home");
		
		TabFolder _home_tabs = new TabFolder(shlPortunesAdministrator, SWT.NONE);
		_home_tabs.setBounds(0, 0, 394, 351);
		
		TabItem tbtmSearch = new TabItem(_home_tabs, SWT.NONE);
		tbtmSearch.setText("Search");
		
		Composite composite = new Composite(_home_tabs, SWT.NONE);
		tbtmSearch.setControl(composite);
		
		Group grpSearchForSpecifc = new Group(composite, SWT.NONE);
		grpSearchForSpecifc.setBounds(10, 27, 355, 96);
		
		Label lblNewLabel = new Label(grpSearchForSpecifc, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 426, 15);
		lblNewLabel.setText("You are able to search by either a username or the user's name. ");
		
		Label lblUsername = new Label(grpSearchForSpecifc, SWT.NONE);
		lblUsername.setBounds(20, 41, 55, 15);
		lblUsername.setText("Username: ");
		
		Label lblNewLabel_1 = new Label(grpSearchForSpecifc, SWT.NONE);
		lblNewLabel_1.setBounds(20, 65, 38, 15);
		lblNewLabel_1.setText("Name: ");
		
		text = new Text(grpSearchForSpecifc, SWT.BORDER);
		text.setBounds(81, 38, 248, 21);
		
		text_1 = new Text(grpSearchForSpecifc, SWT.BORDER);
		text_1.setBounds(60, 65, 269, 21);
		
		Group grpSearchByAdvanced = new Group(composite, SWT.NONE);
		grpSearchByAdvanced.setBounds(10, 155, 355, 62);
		
		Button btnNewButton = new Button(grpSearchByAdvanced, SWT.NONE);
		btnNewButton.setBounds(109, 27, 126, 25);
		btnNewButton.setText("Show query list");
		
		Button btnSearchForUser = new Button(composite, SWT.RADIO);
		btnSearchForUser.setBounds(10, 10, 130, 16);
		btnSearchForUser.setText("Search For User");
		
		Button btnRadioButton = new Button(composite, SWT.RADIO);
		btnRadioButton.setBounds(10, 133, 257, 16);
		btnRadioButton.setText("Seach database using advanced queries");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setBounds(235, 261, 130, 52);
		btnNewButton_1.setText("Show Results");
		
		TabItem tbtmAddNew = new TabItem(_home_tabs, SWT.NONE);
		tbtmAddNew.setText("Add New User");
		
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
}
