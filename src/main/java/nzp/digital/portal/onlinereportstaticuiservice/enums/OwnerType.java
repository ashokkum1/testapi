package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum OwnerType {
	ME("I do"),
	OTHER_PERSON("Somebody else does"),
	ORGANISATION("An organisation does");

	private String pegaOwnerType;
	 
	OwnerType(String pegaOwnerType) {
        this.pegaOwnerType = pegaOwnerType;
    }
 
    public String getPegaOwnerType() {
        return pegaOwnerType;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, OwnerType> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(OwnerType ownerType : OwnerType.values())
        {
            lookup.put(ownerType.getPegaOwnerType(), ownerType);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static OwnerType get(String pegaOwnerType) 
    {
        return lookup.get(pegaOwnerType);
    }
}

