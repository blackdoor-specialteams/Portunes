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

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String args[]) {
//		try {
//			Display display = Display.getDefault();
//			AEdit shell = new AEdit(display);
//			shell.open();
//			shell.layout();
//			while (!shell.isDisposed()) {
//				if (!display.readAndDispatch()) {
//					display.sleep();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public AEdit(Display d) {
		super(d, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		display = d;
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(0, 0, 444, 216);
		
		Button btnChangeName = new Button(composite, SWT.CHECK);
		btnChangeName.setBounds(23, 47, 111, 16);
		btnChangeName.setText("Change Username:");
		
		Button btnChange = new Button(composite, SWT.CHECK);
		btnChange.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnChange.setText("Change Name:");
		btnChange.setBounds(23, 79, 92, 16);
		
		Button btnChangePassword = new Button(composite, SWT.CHECK);
		btnChangePassword.setText("Change Password:");
		btnChangePassword.setBounds(23, 111, 111, 16);
		
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
		text.setBounds(142, 47, 201, 19);
		
		text_1 = new Text(composite, SWT.BORDER);
		text_1.setBounds(121, 78, 222, 19);
		
		text_2 = new Text(composite, SWT.BORDER);
		text_2.setBounds(136, 108, 207, 19);
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
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
