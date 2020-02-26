package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum LocationType {
	SHOP_BUSINESS("A shop/business"),
	ORGANISATION("An organisation"),
	HOME("A home"),
	PUBLIC_PLACE("A public place"),
	PLACE_OF_WORSHIP("Place of worship"),
	EDUCATIONAL_FACILITY("Educational Facility"),
	OTHER("Other");
	
	private String pegaLocationType;
	 
	LocationType(String pegaLocationType) {
        this.pegaLocationType = pegaLocationType;
    }
 
    public String getPegaLocationType() {
        return pegaLocationType;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, LocationType> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(LocationType locationType : LocationType.values())
        {
            lookup.put(locationType.getPegaLocationType(), locationType);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static LocationType get(String pegaLocationType) 
    {
        return lookup.get(pegaLocationType);
    }
}
