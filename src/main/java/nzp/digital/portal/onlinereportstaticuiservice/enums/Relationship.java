package nzp.digital.portal.onlinereportstaticuiservice.enums;

import java.util.HashMap;
import java.util.Map;

public enum Relationship {
	PARENT("Parent"),
	SPOUSE("Husband,Wife"),
	OTHER("Other"),
	OTHER_RELATIVE("Other relative"),
	CHILD("Child"),
	PARTNER_LIVING_TOGETHER("Partner-living togather"),
	PARTNER_NOT_LIVING_TOGETHER("Partner-Not Living Together"),
	SIBLING("Brother,Sister"),
	STEP_PARENT("Step Parent"),
	STEP_CHILD("Step Child"),
	STRANGER("Stranger"),
	FRIEND("Friend"),
	GRANDPARENT("Grandparent"),
	GRANDCHILD("Grandchild"),
	KNOWN_TO_EACH_OTHER("Known to Each Other"),
	FLATMATE_BOARDER("Flatmate/boarder"),
	EX_PARTNER_LIVING_TOGETHER("Ex Married/Partner living together"),
	EX_PARTNER_NOT_LIVING_TOGETHER("Ex Partner Not Living Together"),
	EX_FRIEND("Ex-boyfriend/girlfriend"),
	FAMILY_MEMBER("Family member/whanau"),
	FRIEND_ACQUAINTANCE("Friend/acquaintance"),
	COLLEAGUE("Colleague");
	
	private String pegaRelationship;
	 
	Relationship(String pegaRelationship) {
        this.pegaRelationship = pegaRelationship;
    }
 
    public String getPegaRelationship() {
        return pegaRelationship;
    }
    
  //****** Reverse Lookup Implementation************//
    
    //Lookup table
    private static final Map<String, Relationship> lookup = new HashMap<>();
  
    //Populate the lookup table on loading time
    static
    {
        for(Relationship relationship : Relationship.values())
        {
            lookup.put(relationship.getPegaRelationship(), relationship);
        }
    }
  
    //This method can be used for reverse lookup purpose
    public static Relationship get(String pegaRelationship) 
    {
        return lookup.get(pegaRelationship);
    }
}




















