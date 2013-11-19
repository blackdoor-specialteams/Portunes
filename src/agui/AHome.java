package agui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabItem;

public class AHome {

	protected Shell shlPortunesAdministrator;

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
		shlPortunesAdministrator.setSize(568, 410);
		shlPortunesAdministrator.setText("Portunes | Administrator Home");
		
		TabFolder _home_tabs = new TabFolder(shlPortunesAdministrator, SWT.NONE);
		_home_tabs.setBounds(0, 0, 552, 351);
		
		TabItem tbtmSearch = new TabItem(_home_tabs, SWT.NONE);
		tbtmSearch.setText("Search");
		
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
