package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
	MALE("Male"),
	FEMALE("Female"),
	GENDER_DIVERSE("Gender diverse");
	
	private String pegaGender;
	 
	Gender(String pegaGender) {
        this.pegaGender = pegaGender;
    }
 
    public String getPegaGender() {
        return pegaGender;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, Gender> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(Gender gender : Gender.values())
        {
            lookup.put(gender.getPegaGender(), gender);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static Gender get(String pegaGender) 
    {
        return lookup.get(pegaGender);
    }
}

