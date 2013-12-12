package agui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;

import util.Sanitizer;
import auth.AuthClient;

public class AEdit extends Shell {
	private Display display;
	private Button name_chkbx;
	private Button pass_chkbx;
	private Button remove_chkbox;
	private Button accept_button;
	private Button cancel_button;
	private Text name_tbox;
	private Text pass_tbox;

	private boolean removechecked = false;

	private User user;
	private Session session;
	private AuthClient portclient;

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public AEdit(Display d, User u, AuthClient a, Session s) {
		super(d, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		new Sanitizer();
		portclient = a;
		session = s;
		user = u;
		display = d;

		// //////

		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(0, 0, 410, 216);

		name_chkbx = new Button(composite, SWT.CHECK);
		name_chkbx.setText("Change Name:");
		name_chkbx.setBounds(26, 61, 92, 16);
		name_chkbx.addSelectionListener(new chkboxlistener());

		name_tbox = new Text(composite, SWT.BORDER);
		name_tbox.setBounds(124, 60, 232, 19);
		name_tbox.setEnabled(false);
		name_tbox.setText(user.getName());

		pass_chkbx = new Button(composite, SWT.CHECK);
		pass_chkbx.setText("Change Password:");
		pass_chkbx.setBounds(26, 93, 111, 16);
		pass_chkbx.addSelectionListener(new chkboxlistener());

		pass_tbox = new Text(composite, SWT.BORDER);
		pass_tbox.setBounds(143, 90, 213, 19);
		pass_tbox.setEnabled(false);

		remove_chkbox = new Button(composite, SWT.CHECK);
		remove_chkbox.setBounds(26, 132, 248, 16);
		remove_chkbox.setText("Remove user from the Database.");
		remove_chkbox.addSelectionListener(new remove_listener());

		accept_button = new Button(composite, SWT.NONE);
		accept_button.setBounds(280, 165, 121, 45);
		accept_button.setText("Accept Changes");
		accept_button.addSelectionListener(new acceptlistener());

		cancel_button = new Button(composite, SWT.NONE);
		cancel_button.setBounds(182, 168, 92, 38);
		cancel_button.setText("Cancel");
		cancel_button.addSelectionListener(new cancellistener());

		Label edituser_label = new Label(composite, SWT.NONE);
		edituser_label.setBounds(26, 25, 330, 13);
		edituser_label.setText("Now Editing Information for the user: "
				+ user.getUname());
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Edit User Info");
		setSize(414, 241);

	}

	public class chkboxlistener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (name_chkbx.getSelection()) {
				name_tbox.setEnabled(true);

			} else if (!name_chkbx.getSelection()) {
				name_tbox.setEnabled(false);
			}
			if (pass_chkbx.getSelection()) {
				pass_tbox.setEnabled(true);
			} else if (!pass_chkbx.getSelection()) {
				pass_tbox.setEnabled(false);
			}
		}
	}

	public class remove_listener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (remove_chkbox.getSelection()) {
				removechecked = true;
				name_tbox.setEnabled(false);
				pass_chkbx.setEnabled(false);
				name_chkbx.setEnabled(false);
				pass_tbox.setEnabled(false);
			} else if (!remove_chkbox.getSelection()) {
				removechecked = false;
				name_tbox.setEnabled(true);
				pass_chkbx.setEnabled(true);
				name_chkbx.setEnabled(true);
				pass_tbox.setEnabled(true);
			}
		}
	}
	
	public class cancellistener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			throwaway();
		}
	}
		

	public class acceptlistener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			boolean everythingworked = true;
			if (removechecked) {
				everythingworked = user.Remove();

			} else {
				String newpass = pass_tbox.getText();
				String newname = name_tbox.getText();
				if (pass_chkbx.getSelection()
						&& Sanitizer.isCleanInput(newpass)) {
					everythingworked = user.Changepass(newpass);
				}
				if (name_chkbx.getSelection()
						&& Sanitizer.isCleanInput(newname)) {
					everythingworked = user.Changename(newname);
				}
			}
			if (!everythingworked) {
				System.out.println("Did not work----- sorry");
			}
			throwaway();
		}
	}
	private void throwaway(){
		this.dispose();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
