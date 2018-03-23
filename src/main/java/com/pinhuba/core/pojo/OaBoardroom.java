package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_BOARDROOM
 */
public class OaBoardroom extends BaseBean implements java.io.Serializable {

	/**
	 * 会议室信息表
	 */
	private static final long serialVersionUID = 2436140603367530414L;
	private String oaBoardroomName; // 会议室名称
	private Integer oaBoardroomCapacity; // 可容纳人数
	private String oaBoardroomEquipment; // 会议室设备
	private String oaBoardroomDescribe; // 会议室描述
	private String oaBoardroomAddress; // 会议室位置
	private String recordId;//记录人
	private String recordDate;//记录时间
	private String lastmodiId;//最后修改人
	private String lastmodiDate;//最后修改时间
	private Integer companyId;//公司主键
	// 临时存储
	private Integer roomStatus;// 会议室状态

	public Integer getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}

	// 默认构造方法
	public OaBoardroom() {
		super();
	}

	// get和set方法
	public String getOaBoardroomName() {
		return oaBoardroomName;
	}

	public void setOaBoardroomName(String aOaBoardroomName) {
		this.oaBoardroomName = aOaBoardroomName;
	}

	public Integer getOaBoardroomCapacity() {
		return oaBoardroomCapacity;
	}

	public void setOaBoardroomCapacity(Integer aOaBoardroomCapacity) {
		this.oaBoardroomCapacity = aOaBoardroomCapacity;
	}

	public String getOaBoardroomEquipment() {
		return oaBoardroomEquipment;
	}

	public void setOaBoardroomEquipment(String aOaBoardroomEquipment) {
		this.oaBoardroomEquipment = aOaBoardroomEquipment;
	}

	public String getOaBoardroomDescribe() {
		return oaBoardroomDescribe;
	}

	public void setOaBoardroomDescribe(String aOaBoardroomDescribe) {
		this.oaBoardroomDescribe = aOaBoardroomDescribe;
	}

	public String getOaBoardroomAddress() {
		return oaBoardroomAddress;
	}

	public void setOaBoardroomAddress(String aOaBoardroomAddress) {
		this.oaBoardroomAddress = aOaBoardroomAddress;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String aRecordId) {
		this.recordId = aRecordId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String aRecordDate) {
		this.recordDate = aRecordDate;
	}

	public String getLastmodiId() {
		return lastmodiId;
	}

	public void setLastmodiId(String aLastmodiId) {
		this.lastmodiId = aLastmodiId;
	}

	public String getLastmodiDate() {
		return lastmodiDate;
	}

	public void setLastmodiDate(String aLastmodiDate) {
		this.lastmodiDate = aLastmodiDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer aCompanyId) {
		this.companyId = aCompanyId;
	}

}