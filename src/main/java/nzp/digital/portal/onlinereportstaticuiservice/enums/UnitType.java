package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum UnitType {
	NONE(""),
	UNIT("Unit"),
	APARTMENT("Apartment");
	
	private String pegaUnitType;
	 
	UnitType(String pegaUnitType) {
        this.pegaUnitType = pegaUnitType;
    }
 
    public String getPegaUnitType() {
        return pegaUnitType;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, UnitType> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(UnitType unitType : UnitType.values())
        {
            lookup.put(unitType.getPegaUnitType(), unitType);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static UnitType get(String pegaUnitType) 
    {
        return lookup.get(pegaUnitType);
    }
}

