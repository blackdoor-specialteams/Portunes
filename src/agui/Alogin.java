package agui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class Alogin {

	protected Shell Alogin;
	private Text text;
	private Text text_1;

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		Alogin.open();
		Alogin.layout();
		while (!Alogin.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		Alogin = new Shell();
		Alogin.setSize(354, 183);
		Alogin.setText("Portunes | Administer Login");
		
		Composite composite = new Composite(Alogin, SWT.NONE);
		composite.setBounds(0, 0, 348, 155);
		
		Group grpEnterAdminInformation = new Group(Alogin, SWT.NONE);
		grpEnterAdminInformation.setBounds(0, 0, 348, 155);
		
		Label lblAdministrator = new Label(grpEnterAdminInformation, SWT.NONE);
		lblAdministrator.setBounds(22, 36, 73, 15);
		lblAdministrator.setText("Administrator:");
		
		Label lblPassword = new Label(grpEnterAdminInformation, SWT.NONE);
		lblPassword.setBounds(22, 67, 55, 15);
		lblPassword.setText("Password:");
		
		text = new Text(grpEnterAdminInformation, SWT.BORDER);
		text.setBounds(101, 33, 223, 21);
		
		text_1 = new Text(grpEnterAdminInformation, SWT.BORDER);
		text_1.setBounds(83, 67, 241, 22);
		
		Button _login_button = new Button(grpEnterAdminInformation, SWT.NONE);
		_login_button.setBounds(215, 106, 123, 37);
		_login_button.setText("Login");

	}
}
