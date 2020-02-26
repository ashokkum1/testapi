package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum VehicleLocation {
	DRIVEWAY("Parked on a driveway"),
	GARAGE("Parked in a garage"),
	ROAD("Parked on a road"),
	PUBLIC_CARPARK("Parked in a public carpark"),
	PARKING_LOT("In a parking lot"),
	COMMERCIAL_PROPERTY("Commercial property"),
	BOAT_OR_SHIP("On a boat or ship");
	
	private String pegaVehicleLocation;
	 
	VehicleLocation(String pegaVehicleLocation) {
        this.pegaVehicleLocation = pegaVehicleLocation;
    }
 
    public String getPegaVehicleLocation() {
        return pegaVehicleLocation;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, VehicleLocation> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(VehicleLocation vehicleLocation : VehicleLocation.values())
        {
            lookup.put(vehicleLocation.getPegaVehicleLocation(), vehicleLocation);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static VehicleLocation get(String pegaVehicleLocation) 
    {
        return lookup.get(pegaVehicleLocation);
    }
}

