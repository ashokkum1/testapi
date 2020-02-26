package nzp.digital.portal.onlinereportstaticuiservice.model;

import java.util.List;

public class EventInfo {
	 private WhenItHappened whenItHappened;
	 private WhereItHappened whereItHappened;
	 private List<ItemDetail> lostItems;
	 private List<ItemDetail> damagedItems;
	 private DamagedVehicle damagedVehicles;
	 private WhatHappened whatHappened;
	 private WhoAssaulted whoWasHurt;
	 private WhoWasInvolved whoWasInvolved;
	 private AdditionalInformation additionalInformation;	 
	 private StolenVehicle stolenVehicles;
	 private List<ItemDetail> stolenItems;	 
		
		 // Getter Methods 

		 public WhenItHappened getWhenItHappened() {
		  return whenItHappened;
		 }

		 public WhereItHappened getWhereItHappened() {
		  return whereItHappened;
		 }

		
		 public AdditionalInformation getAdditionalInformation() {
		  return additionalInformation;
		 }		 

		 // Setter Methods 

		 public void setWhenItHappened(WhenItHappened whenItHappened) {
		  this.whenItHappened = whenItHappened;
		 }

		 public void setWhereItHappened(WhereItHappened whereItHappened) {
		  this.whereItHappened = whereItHappened;
		 }

		
		 public void setAdditionalInformation(AdditionalInformation additionalInformation) {
		  this.additionalInformation = additionalInformation;
		 }			

		public WhatHappened getWhatHappened() {
			return whatHappened;
		}

		public void setWhatHappened(WhatHappened whatHappened) {
			this.whatHappened = whatHappened;
		}

		
		public WhoWasInvolved getWhoWasInvolved() {
			return whoWasInvolved;
		}

		public void setWhoWasInvolved(WhoWasInvolved whoWasInvolved) {
			this.whoWasInvolved = whoWasInvolved;
		}

		public DamagedVehicle getDamagedVehicles() {
			return damagedVehicles;
		}

		public void setDamagedVehicles(DamagedVehicle damagedVehicles) {
			this.damagedVehicles = damagedVehicles;
		}
		
		public WhoAssaulted getWhoWasHurt() {
			return whoWasHurt;
		}

		public void setWhoWasHurt(WhoAssaulted whoWasHurt) {
			this.whoWasHurt = whoWasHurt;
		}		

		public List<ItemDetail> getLostItems() {
			return lostItems;
		}

		public void setLostItems(List<ItemDetail> lostItems) {
			this.lostItems = lostItems;
		}

		public List<ItemDetail> getStolenItems() {
			return stolenItems;
		}

		public void setStolenItems(List<ItemDetail> stolenItems) {
			this.stolenItems = stolenItems;
		}

		public StolenVehicle getStolenVehicles() {
			return stolenVehicles;
		}

		public void setStolenVehicles(StolenVehicle stolenVehicles) {
			this.stolenVehicles = stolenVehicles;
		}

		public List<ItemDetail> getDamagedItems() {
			return damagedItems;
		}

		public void setDamagedItems(List<ItemDetail> damagedItems) {
			this.damagedItems = damagedItems;
		}		
		
}
