package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum VehicleType {
	CAR("Car"),
	COMMERCIAL("Commercial Vehicle"),
	FARM("Farm Vehicle"),
	HEAVY_MOTOR("Heavy Motor Vehicle"),
	MOTORCYCLE("Motorcycle"),
	OTHER("Other"),
	PASSENGER("Passenger Vehicle"),
	SPECIAL("Special Vehicle"),
	TRAILER("Trailer");
	
	private String pegaVehicleType;
	 
	VehicleType(String pegaVehicleType) {
        this.pegaVehicleType = pegaVehicleType;
    }
 
    public String getPegaVehicleType() {
        return pegaVehicleType;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, VehicleType> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(VehicleType vehicleType : VehicleType.values())
        {
            lookup.put(vehicleType.getPegaVehicleType(), vehicleType);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static VehicleType get(String pegaVehicleType) 
    {
        return lookup.get(pegaVehicleType);
    }
}
