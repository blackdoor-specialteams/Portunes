package agui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;

import auth.*;

public class QResults {

	protected Shell _QResults_swt;
	private Table table;
	private AuthClient portclient;

	public QResults(AuthClient portclient) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		_QResults_swt.open();
		_QResults_swt.layout();
		while (!_QResults_swt.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		_QResults_swt = new Shell();
		_QResults_swt.setSize(788, 437);
		_QResults_swt.setText("Results");
		
		Composite _Results_comp = new Composite(_QResults_swt, SWT.NONE);
		_Results_comp.setBounds(0, 0, 772, 398);
		
		table = new Table(_Results_comp, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 10, 752, 318);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		Button btnNewButton = new Button(_Results_comp, SWT.NONE);
		btnNewButton.setBounds(626, 334, 136, 54);
		btnNewButton.setText("Done");
		
		Button btnNewButton_1 = new Button(_Results_comp, SWT.NONE);
		btnNewButton_1.setBounds(10, 334, 128, 54);
		btnNewButton_1.setText("Edit Selected");

	}
}
