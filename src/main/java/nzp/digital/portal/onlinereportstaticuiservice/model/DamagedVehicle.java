package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.ArrayList;
import java.util.List;

public class DamagedVehicle {
	 private boolean anyDamaged;
	 private List < Vehicle > vehicles = new ArrayList < Vehicle > ();
	 
	public boolean isAnyDamaged() {
		return anyDamaged;
	}
	public void setAnyDamaged(boolean anyDamaged) {
		this.anyDamaged = anyDamaged;
	}
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}	
	 
}
