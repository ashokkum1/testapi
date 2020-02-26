package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum OwnerDisplayType {
	ME("I"),
	OTHER_PERSON("Somebody"),
	ORGANISATION("Organisation");

	private String pegaOwnerDisplayType;
	 
	OwnerDisplayType(String pegaOwnerDisplayType) {
        this.pegaOwnerDisplayType = pegaOwnerDisplayType;
    }
 
    public String getPegaOwnerDisplayType() {
        return pegaOwnerDisplayType;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, OwnerDisplayType> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(OwnerDisplayType ownerDisplayType : OwnerDisplayType.values())
        {
            lookup.put(ownerDisplayType.getPegaOwnerDisplayType(), ownerDisplayType);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static OwnerDisplayType get(String pegaOwnerDisplayType) 
    {
        return lookup.get(pegaOwnerDisplayType);
    }
}
