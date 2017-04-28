package cn.cerc.jmis.services;

public class BookInfoRecord {
	//状态
	private int status;
	private String code;
	//简化名称
	private String shortName;
	//名称
	private String name;
	//类型
	private int corpType;
	//地址
	private String address;
	//电话
	private String tel;
	//主管电话
	private String managerPhone;
	private String startHost;
	//摘要
	private String contact;
	private boolean authentication;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCorpType() {
		return corpType;
	}

	public void setCorpType(int corpType) {
		this.corpType = corpType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStartHost() {
		return startHost;
	}

	public void setStartHost(String startHost) {
		this.startHost = startHost;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public boolean isAuthentication() {
		return authentication;
	}

	public void setAuthentication(boolean authentication) {
		this.authentication = authentication;
	}
}
