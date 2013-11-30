package auth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * a request for last login info about all users adminName administrates.
 * effectively several lastlogin GETINFO requests.
 * @author kAG0
 *
 */
public class LIST extends Request implements Serializable {
	public List<Map<String, Object>> reply; 
	
	public LIST(String adminName, byte[] adminPass) {
		super(Operation.LIST);
		this.adminName= adminName;
		this.admin = true;
		adminPW = adminPass;
	}

	@Override
	public void setReply(Object reply) {
		this.reply = (List<Map<String, Object>>) reply;	
	}
	public String getOpUserName(){
		return adminName;
	}
	public byte[] getOpUserPW(){
		return adminPW;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\nLIST [reply=" + reply + "]";
	}

}
