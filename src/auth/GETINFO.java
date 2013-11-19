package auth;

import java.io.Serializable;
import java.util.Map;

public class GETINFO extends Request implements Serializable {
	Map<String, Object> reply;
	public int time; // the number of log-ins ago to get info for
	//also needs userPW or (adminName and adminPW) to be set
	public GETINFO(int time, String userName) {
		super(Operation.GETINFO);
		username = userName;
		this.time = time;
		 //this.operation = Operation.GETINFO;
	}

}
