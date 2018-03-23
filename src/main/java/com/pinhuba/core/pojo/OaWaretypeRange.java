package com.pinhuba.core.pojo;

/**
 * 数据库表名：OA_WARETYPE_RANGE
 */
public class OaWaretypeRange extends BaseBean implements java.io.Serializable {

	/**
	 * 知识类型表
	 */
	private static final long serialVersionUID = -1344353964979311417L;
	private Integer oaWareTypeId; // 知识类型ID

	private OaWareType wareType; // 知识类型

	private String hrmEmployeeId; // 人ID

	private HrmEmployee hrmEmployee;// 人

	private Integer companyId;

	private Integer rangeType;// 类型 与type类型匹配，方便查询

	public OaWareType getWareType() {
		return wareType;
	}

	public void setWareType(OaWareType wareType) {
		this.wareType = wareType;
	}

	public HrmEmployee getHrmEmployee() {
		return hrmEmployee;
	}

	public void setHrmEmployee(HrmEmployee hrmEmployee) {
		this.hrmEmployee = hrmEmployee;
	}

	// 默认构造方法
	public OaWaretypeRange() {
		super();
	}

	// get和set方法
	public Integer getOaWareTypeId() {
		return oaWareTypeId;
	}

	public void setOaWareTypeId(Integer aOaWareTypeId) {
		this.oaWareTypeId = aOaWareTypeId;
	}

	public String getHrmEmployeeId() {
		return hrmEmployeeId;
	}

	public void setHrmEmployeeId(String aHrmEmployeeId) {
		this.hrmEmployeeId = aHrmEmployeeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getRangeType() {
		return rangeType;
	}

	public void setRangeType(Integer rangeType) {
		this.rangeType = rangeType;
	}

}