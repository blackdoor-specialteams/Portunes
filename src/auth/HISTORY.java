package auth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class HISTORY extends Request implements Serializable {
	List<Map<String, Object>> reply;
	//also needs userPW or (adminName and adminPW) to be set
	public HISTORY(String userName) {
		super(Operation.HISTORY);
		username = userName;
	}

}
