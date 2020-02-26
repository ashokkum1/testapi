package nzp.digital.portal.onlinereportstaticuiservice.model;

public class Address {
	 private String unitType;
	 private String unitNumber;
	 private String streetNumber;
	 private String streetName;
	 private String streetType;
	 private String town;
	 private String suburb;	 
	 private String country;
	

	 // Getter Methods 

	 public String getUnitType() {
	  return unitType;
	 }

	 public String getUnitNumber() {
	  return unitNumber;
	 }

	 public String getStreetNumber() {
	  return streetNumber;
	 }

	 public String getStreetName() {
	  return streetName;
	 }

	 public String getStreetType() {
	  return streetType;
	 }

	 public String getTown() {
	  return town;
	 }

	 public String getSuburb() {
	  return suburb;
	 }

	 // Setter Methods 

	 public void setUnitType(String unitType) {
	  this.unitType = unitType;
	 }

	 public void setUnitNumber(String unitNumber) {
	  this.unitNumber = unitNumber;
	 }

	 public void setStreetNumber(String streetNumber) {
	  this.streetNumber = streetNumber;
	 }

	 public void setStreetName(String streetName) {
	  this.streetName = streetName;
	 }

	 public void setStreetType(String streetType) {
	  this.streetType = streetType;
	 }

	 public void setTown(String town) {
	  this.town = town;
	 }

	 public void setSuburb(String suburb) {
	  this.suburb = suburb;
	 }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
