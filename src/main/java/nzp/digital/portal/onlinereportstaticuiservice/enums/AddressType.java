package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum AddressType {
	HOME("Home address"),
	CONTACT("Contact address"),
	WORK("Work address");
	
	private String pegaAddressType;
	 
	AddressType(String pegaAddressType) {
        this.pegaAddressType = pegaAddressType;
    }
 
    public String getPegaAddressType() {
        return pegaAddressType;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, AddressType> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(AddressType addressType : AddressType.values())
        {
            lookup.put(addressType.getPegaAddressType(), addressType);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static AddressType get(String pegaAddressType) 
    {
        return lookup.get(pegaAddressType);
    }
}

