package auth;

import java.io.Serializable;
import java.util.Map;
/**
 * a request to get information about a specific login of userName, specifically 
 * the login that happened time login's ago.
 * @author kAG0
 *
 */
public class GETINFO extends Request implements Serializable {
	public Map<String, Object> reply;
	public int time; // the number of log-ins ago to get info for
	//also needs userPW or (adminName and adminPW) to be set
	public GETINFO(int time, String userName) {
		super(Operation.GETINFO);
		username = userName;
		this.time = time;
		 //this.operation = Operation.GETINFO;
	}
	@Override
	public void setReply(Object reply) {
		this.reply = (Map<String, Object>) reply;
	}
	public String getOpUserName(){
		return username;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\nGETINFO [reply=" + reply + ", time=" + time + "]";
	}
	
}
