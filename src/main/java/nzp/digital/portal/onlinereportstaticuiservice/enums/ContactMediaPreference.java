package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum ContactMediaPreference {
	EMAIL("Email"),
	PHONE("Phone");
	
	private String pegaContactMediaPreference;
	 
	ContactMediaPreference(String pegaContactMediaPreference) {
        this.pegaContactMediaPreference = pegaContactMediaPreference;
    }
 
    public String getPegaContactMediaPreference() {
        return pegaContactMediaPreference;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, ContactMediaPreference> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(ContactMediaPreference contactMediaPreference : ContactMediaPreference.values())
        {
            lookup.put(contactMediaPreference.getPegaContactMediaPreference(), contactMediaPreference);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static ContactMediaPreference get(String pegaContactMediaPreference) 
    {
        return lookup.get(pegaContactMediaPreference);
    }

}
