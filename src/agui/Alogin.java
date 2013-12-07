package agui;

import java.awt.event.ActionListener;

import auth.*;

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
	private AuthClient portclient;
	/**
	 * Open the window.
	 */
	public Alogin(AuthClient tempclient){
		portclient = tempclient;
	}
	
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
		Alogin.setSize(376, 204);
		Alogin.setText("Portunes | Administer Login");
		
		Group grpEnterAdminInformation = new Group(Alogin, SWT.NONE);
		grpEnterAdminInformation.setBounds(10, 10, 338, 144);
		
		Label lblAdministrator = new Label(grpEnterAdminInformation, SWT.NONE);
		lblAdministrator.setBounds(10, 27, 73, 15);
		lblAdministrator.setText("Administrator:");
		
		Label lblPassword = new Label(grpEnterAdminInformation, SWT.NONE);
		lblPassword.setBounds(10, 58, 55, 15);
		lblPassword.setText("Password:");
		
		text = new Text(grpEnterAdminInformation, SWT.BORDER);
		text.setBounds(89, 24, 235, 21);
		
		text_1 = new Text(grpEnterAdminInformation, SWT.BORDER);
		text_1.setBounds(71, 58, 253, 22);
		
		Button _login_button = new Button(grpEnterAdminInformation, SWT.NONE);
		_login_button.setBounds(201, 95, 123, 37);
		_login_button.setText("Login");
		_login_button.addActionListener(new ButtonListener());

	}
	private class ButtonListener implements ActionListener
	  {
	       public void actionPerformed(ActionEvent event)
	        {
	            Object bpress = event.getSource();

	        if(bpress == addPet){
	            for (int p = 1; p < selectList.size(); p++){
	                boolean check = false;
	                String selectedPet =(String) petList.getSelectedValue();
	                String petCheck =(String) selectList.get(p);
	                if(selectedPet.equals(petCheck)){
	                    check = true;
	                    break;
	                } else if(added == false){
	                    petsAvail.add(selectedPet);
	                    petsAvail.updateUI();
	                    numPets++;
	                }
	            }
	        } else if (bpress == remove){

	            numPets--;
	        }
	        }
	  }
	
}
