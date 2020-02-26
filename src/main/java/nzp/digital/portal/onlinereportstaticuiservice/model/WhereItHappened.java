package nzp.digital.portal.onlinereportstaticuiservice.model;

public class WhereItHappened {
	 private String locationType;
	 private String locationName;	 
	 private AddressInfo address;
	 private String howCloseToAddress;
	 private String additionalInformation;
	 private boolean stolenFromVehicle;
	 private String licencePlate;
	 private String vehicleLocation;


	 // Getter Methods 

	 public String getLocationType() {
	  return locationType;
	 }

	 public AddressInfo getAddress() {
	  return address;
	 }

	 public String getHowCloseToAddress() {
	  return howCloseToAddress;
	 }

	 public String getAdditionalInformation() {
	  return additionalInformation;
	 }

	 // Setter Methods 

	 public void setLocationType(String locationType) {
	  this.locationType = locationType;
	 }

	 public void setAddress(AddressInfo address) {
	  this.address = address;
	 }

	 public void setHowCloseToAddress(String howCloseToAddress) {
	  this.howCloseToAddress = howCloseToAddress;
	 }

	 public void setAdditionalInformation(String additionalInformation) {
	  this.additionalInformation = additionalInformation;
	 }

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public boolean isStolenFromVehicle() {
		return stolenFromVehicle;
	}

	public void setStolenFromVehicle(boolean stolenFromVehicle) {
		this.stolenFromVehicle = stolenFromVehicle;
	}

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}

	public String getVehicleLocation() {
		return vehicleLocation;
	}

	public void setVehicleLocation(String vehicleLocation) {
		this.vehicleLocation = vehicleLocation;
	}
	
}
