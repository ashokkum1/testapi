package nzp.digital.portal.onlinereportstaticuiservice.model;

public class Vehicle {
	 private String type;
	 private String make;
	 private String description;
	 private String licencePlate;
	 private String location;
	 private String howAndWhere;
	 private boolean wasMoved;
	 private boolean ignitionTamperedWith;
	 
	 
	 private String whoOwnsIt;
	 private Owner owner;
	 
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLicencePlate() {
		return licencePlate;
	}
	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getHowAndWhere() {
		return howAndWhere;
	}
	public void setHowAndWhere(String howAndWhere) {
		this.howAndWhere = howAndWhere;
	}
	public boolean isWasMoved() {
		return wasMoved;
	}
	public void setWasMoved(boolean wasMoved) {
		this.wasMoved = wasMoved;
	}
	public boolean isIgnitionTamperedWith() {
		return ignitionTamperedWith;
	}
	public void setIgnitionTamperedWith(boolean ignitionTamperedWith) {
		this.ignitionTamperedWith = ignitionTamperedWith;
	}
	public String getWhoOwnsIt() {
		return whoOwnsIt;
	}
	public void setWhoOwnsIt(String whoOwnsIt) {
		this.whoOwnsIt = whoOwnsIt;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}

}
