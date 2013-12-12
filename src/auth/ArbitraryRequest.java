package auth;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ArbitraryRequest extends Request implements Serializable {
	public List<Map<String, Object>> reply;
	String query;
	public ArbitraryRequest(String query, String adminName, byte[] adminPW) {
		super(null);
		this.adminName = adminName;
		this.adminPW = adminPW;		
		this.query = query;
	}
	
		public String getAuthUserName(){
			return adminName;
		}
		public byte[] getAuthUserPW(){
			return adminPW;
		}
		public String getOpUserName(){
			return adminName;
		}
		public byte[] getOpUserPW(){
			return adminPW;
		}

		@Override
		public void setReply(Object reply) {
			this.reply = (List<Map<String, Object>>) reply;
			
		}

}
