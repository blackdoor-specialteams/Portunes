package agui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class AadvancedQuery extends Shell {
	private Text query_text;
	public String query;
	/**
	 * Create the shell.
	 * @param display
	 */
	public AadvancedQuery(Display display) {
		super(display, SWT.CLOSE | SWT.TITLE);
		query = "";
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(0, 0, 474, 110);
		
		query_text = new Text(composite, SWT.BORDER);
		query_text.setBounds(49, 22, 415, 19);
		
		Label lblEnter = new Label(composite, SWT.NONE);
		lblEnter.setBounds(10, 25, 34, 13);
		lblEnter.setText("Query:");
		
		Button cancel_button = new Button(composite, SWT.NONE);
		cancel_button.setBounds(10, 61, 87, 39);
		cancel_button.setText("Cancel");
		cancel_button.addSelectionListener(new cancellistener());
		
		Button accept_button = new Button(composite, SWT.NONE);
		accept_button.setText("Accept");
		accept_button.setBounds(377, 61, 87, 39);
		accept_button.addSelectionListener(new acceptbuttonlistener());
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Advanced Query");
		setSize(482, 136);

	}
	
	public class acceptbuttonlistener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			query = query_text.getText();
			throwaway();
		}
	}
	public class cancellistener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			query = "";
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
