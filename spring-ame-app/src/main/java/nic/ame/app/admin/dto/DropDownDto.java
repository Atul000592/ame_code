package nic.ame.app.admin.dto;

import java.math.BigInteger;

public class DropDownDto {

	
	
	private Integer id;
	private String boardId;
	private String value;
	private String cValue;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public DropDownDto(Integer id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public DropDownDto(String value, String cValue) {
		super();
		this.value = value;
		this.cValue = cValue;
	}
	public DropDownDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getcValue() {
		return cValue;
	}
	public void setcValue(String cValue) {
		this.cValue = cValue;
	}

}
