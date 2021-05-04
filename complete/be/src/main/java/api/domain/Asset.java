package api.domain;

import java.util.Date;

public class Asset {

    private String serial;
	private String name;
	private String description;
	private String assigneeEmail;
	private Date dateAssignment;
    private Date dateRegistered;

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssigneeEmail() {
		return this.assigneeEmail;
	}

	public void setAssigneeEmail(String assigneeEmail) {
		this.assigneeEmail = assigneeEmail;
	}

	public Date getDateAssignment() {
		return this.dateAssignment;
	}

	public void setDateAssignment(Date dateAssignment) {
		this.dateAssignment = dateAssignment;
	}

	public Date getDateRegistered() {
		return this.dateRegistered;
	}

	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}

	

}
