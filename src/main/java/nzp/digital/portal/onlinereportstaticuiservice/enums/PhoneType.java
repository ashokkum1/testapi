package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum PhoneType {
	HOME("Home"),
	WORK("Work"),
	MOBILE("Mobile");
	
	private String pegaPhoneType;
	 
	PhoneType(String pegaPhoneType) {
        this.pegaPhoneType = pegaPhoneType;
    }
 
    public String getPegaPhoneType() {
        return pegaPhoneType;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, PhoneType> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(PhoneType phoneType : PhoneType.values())
        {
            lookup.put(phoneType.getPegaPhoneType(), phoneType);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static PhoneType get(String pegaPhoneType) 
    {
        return lookup.get(pegaPhoneType);
    }

}
