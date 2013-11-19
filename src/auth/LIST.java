package auth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class LIST extends Request implements Serializable {
	public List<Map<String, Object>> reply; 
	
	public LIST(String adminName, byte[] adminPass) {
		super(Operation.LIST);
		admin= adminName;
		adminPW = adminPass;
	}

}
