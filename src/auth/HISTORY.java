package auth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class HISTORY extends Request implements Serializable {
	List<Map<String, Object>> reply;
	public HISTORY() {
		super(Operation.HISTORY);
	}

}
