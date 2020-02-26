package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.ArrayList;
import java.util.List;

public class StolenVehicle {
	private boolean anyStolen;	
	private List<MissingVehicle> vehicles = new ArrayList < MissingVehicle > ();
	
	public boolean isAnyStolen() {
		return anyStolen;
	}
	public void setAnyStolen(boolean anyStolen) {
		this.anyStolen = anyStolen;
	}
	public List<MissingVehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<MissingVehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	

}
