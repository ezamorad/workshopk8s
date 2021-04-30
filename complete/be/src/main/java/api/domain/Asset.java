package api.domain;

public class Asset {
    private Long id;
	private String name;
	private String serial;
	private String details;
	private String owner;
    private String status;
	
	public Asset(Long id, String name, String serial, String details, String owner, String status) {
		super();
		this.id = id;
		this.name = name;
		this.serial = serial;
        this.details = details;
		this.owner = owner;
        this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
    public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
    public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
