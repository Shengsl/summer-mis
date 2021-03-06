package cn.cerc.jmis.message;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jdb.core.DataSet;
import cn.cerc.jdb.core.IHandle;
import cn.cerc.jdb.jiguang.ClientType;
import cn.cerc.jdb.jiguang.JiguangPush;

/**
 * 获取用户的设备信息
 */
public class JPushRecord {
	private String corpNo;
	private String userCode;
	private String title;
	private String alert;
	private int msgId;

	/**
	 * 往指定用户的所有移动设备发送消息
	 * 
	 * @param corpNo
	 * @param userCode
	 * @param msgId
	 */
	public JPushRecord(String corpNo, String userCode, int msgId) {
		this.corpNo = corpNo;
		this.userCode = userCode;
		this.msgId = msgId;
	}

	public void send(IHandle handle) {
		LocalService srv = new LocalService(handle, "SvrUserLogin.getMachInfo");
		if (!srv.exec("CorpNo_", corpNo, "UserCode_", userCode)) {
			throw new RuntimeException(srv.getMessage());
		}

		// 设置极光推送平台
		JiguangPush push = new JiguangPush(handle);
		push.setMessage(alert);
		push.setMsgId("" + msgId);
		push.setTitle(title);

		// 将消息推送到极光平台
		DataSet dataOut = srv.getDataOut();
		while (dataOut.fetch()) {
			String machineCode = dataOut.getString("MachineCode_");
			int machineType = dataOut.getInt("MachineType_");
			switch (machineType) {
			case 6:
				push.send(ClientType.IOS, machineCode);
				break;
			case 7:
				push.send(ClientType.Android, machineCode);
				break;
			default:
				break;
			}
		}

	}

	public String getCorpNo() {
		return corpNo;
	}

	public JPushRecord setCorpNo(String corpNo) {
		this.corpNo = corpNo;
		return this;
	}

	public String getUserCode() {
		return userCode;
	}

	public JPushRecord setUserCode(String userCode) {
		this.userCode = userCode;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public JPushRecord setTitle(String title) {
		this.title = title;
		return this;
	}

	public JPushRecord setTitle(String format, Object... args) {
		this.title = String.format(format, args);
		return this;
	}

	public String getAlert() {
		return alert;
	}

	public JPushRecord setAlert(String alert) {
		this.alert = alert;
		return this;
	}

	public JPushRecord setAlert(String format, Object... args) {
		this.alert = String.format(format, args);
		return this;
	}

	public int getMsgId() {
		return msgId;
	}

	public JPushRecord setMsgId(int msgId) {
		this.msgId = msgId;
		return this;
	}

}
