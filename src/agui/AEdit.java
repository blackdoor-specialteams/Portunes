package agui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;

public class AEdit extends Shell {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Display display;
	private Button btnChange;
	private Button btnChangeName;
	private Button btnChangePassword;
	private Button btnGiveAdminPermissions;
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public AEdit(Display d) {
		super(d, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		display = d;
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(0, 0, 444, 216);
		
		btnChangeName = new Button(composite, SWT.CHECK);
		btnChangeName.setBounds(27, 28, 111, 16);
		btnChangeName.setText("Change Username:");
		btnChangeName.addSelectionListener(new checklistner());
		
		btnChange = new Button(composite, SWT.CHECK);
		btnChange.setText("Change Name:");
		btnChange.setBounds(27, 60, 92, 16);
		btnChange.addSelectionListener(new checklistner());
		
		btnChangePassword = new Button(composite, SWT.CHECK);
		btnChangePassword.setText("Change Password:");
		btnChangePassword.setBounds(27, 92, 111, 16);
		btnChangePassword.addSelectionListener(new checklistner());
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setBounds(10, 168, 99, 38);
		btnNewButton.setText("Delete User");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setBounds(313, 161, 121, 45);
		btnNewButton_1.setText("Accept Changes");
		
		Button btnNewButton_2 = new Button(composite, SWT.NONE);
		btnNewButton_2.setBounds(215, 168, 92, 38);
		btnNewButton_2.setText("Cancel");
		btnNewButton_2.addSelectionListener(new Choicelistener());
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(146, 28, 201, 19);
		text.setEnabled(false);
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setBounds(125, 59, 222, 19);
		text_1.setEnabled(false);
		
		
		text_2 = new Text(composite, SWT.BORDER);
		text_2.setBounds(140, 89, 207, 19);
		text_2.setEnabled(false);
		
		btnGiveAdminPermissions = new Button(composite, SWT.CHECK);
		btnGiveAdminPermissions.setBounds(24, 125, 215, 16);
		btnGiveAdminPermissions.setText("Give Administrative Permissions.");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Edit User Info");
		setSize(450, 241);

	}
	
	public class Choicelistener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
				while (!display.readAndDispatch()) 
					display.sleep();

			}
		}
	public class checklistner extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if(btnChangeName.getSelection()){
				text.setEnabled(true);
			}
		if(btnChange.getSelection()){
			text_1.setEnabled(true);
		}
		if(btnChangePassword.getSelection()){
			text_2.setEnabled(true);
		}
		if(!btnChangeName.getSelection()){
			text.setEnabled(false);
		}
		if(!btnChange.getSelection()){
		text_1.setEnabled(false);
		}
		if(!btnChangePassword.getSelection()){
		text_2.setEnabled(false);
	}
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
