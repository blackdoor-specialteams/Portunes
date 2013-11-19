package auth;

import java.io.Serializable;
import java.util.Map;

public class GETINFO extends Request implements Serializable {
	Map<String, Object> reply;
	public int time; // the number of log-ins ago to get info for
	public GETINFO(int time) {
		super(Operation.GETINFO);
		this.time = time;
		 //this.operation = Operation.GETINFO;
	}

}
