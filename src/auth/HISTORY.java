package auth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * a request for a user's entire login history.
 * effectively GETINFO requests for the users entire history.
 * @author kAG0
 *
 */
public class HISTORY extends Request implements Serializable {
	public List<Map<String, Object>> reply;
	//also needs userPW or (adminName and adminPW) to be set
	public HISTORY(String userName) {
		super(Operation.HISTORY);
		username = userName;
	}
	@Override
	public void setReply(Object reply) {
		this.reply = (List<Map<String, Object>>) reply;
	}
	public String getOpUserName(){
		return username;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\nHISTORY [reply=" + reply + "]";
	}

}
