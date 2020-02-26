package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.ArrayList;
import java.util.List;

public class StolenItems {
	private boolean stolenfromVehicleIndicator;
	private StolenfromVehicle stolenfromVehicle;
	private List<ItemDetail> item = new ArrayList < ItemDetail > ();
	
	public boolean isStolenfromVehicleIndicator() {
		return stolenfromVehicleIndicator;
	}
	public void setStolenfromVehicleIndicator(boolean stolenfromVehicleIndicator) {
		this.stolenfromVehicleIndicator = stolenfromVehicleIndicator;
	}
	public StolenfromVehicle getStolenfromVehicle() {
		return stolenfromVehicle;
	}
	public void setStolenfromVehicle(StolenfromVehicle stolenfromVehicle) {
		this.stolenfromVehicle = stolenfromVehicle;
	}
	public List<ItemDetail> getItem() {
		return item;
	}
	public void setItem(List<ItemDetail> item) {
		this.item = item;
	}

}
