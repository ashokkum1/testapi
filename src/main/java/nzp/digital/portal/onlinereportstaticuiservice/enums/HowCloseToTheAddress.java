package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum HowCloseToTheAddress {
	AT("At"),
	NEARBY("Nearby"),
	OUTSIDE("Outside");
	
	private String pegaHowCloseToTheAddress;
	 
	HowCloseToTheAddress(String pegaHowCloseToTheAddress) {
        this.pegaHowCloseToTheAddress = pegaHowCloseToTheAddress;
    }
 
    public String getPegaHowCloseToTheAddress() {
        return pegaHowCloseToTheAddress;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, HowCloseToTheAddress> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(HowCloseToTheAddress howCloseToTheAddress : HowCloseToTheAddress.values())
        {
            lookup.put(howCloseToTheAddress.getPegaHowCloseToTheAddress(), howCloseToTheAddress);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static HowCloseToTheAddress get(String pegaHowCloseToTheAddress) 
    {
        return lookup.get(pegaHowCloseToTheAddress);
    }
}


