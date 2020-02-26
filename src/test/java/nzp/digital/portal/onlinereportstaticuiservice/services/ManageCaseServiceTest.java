package nzp.digital.portal.onlinereportstaticuiservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nzp.digital.portal.onlinereportstaticuiservice.config.JacksonOffsetDateTimeMapper;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Address;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Address.UnitTypeEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.AddressInfo;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.ContactDetail;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.ContactDetail.PreferredContactMethodEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Contacts;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.DamagedVehicles;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.EventInfo;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.ItemDetail;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.MissingVehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Name;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.OffenderVehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.OnlineReportCaseRequest;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.OrganisationContact;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Owner;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Owner.TypeEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Person;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Person.GenderEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Person.RelationEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.PersonalContact;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Phone;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.ReportType;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.SearchedItem;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.StolenVehicles;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Suspect;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Suspect.RelationTypeEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Vehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Vehicle.LocationEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhatHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhenItHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhereItHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhereItHappened.HowCloseToAddressEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhereItHappened.LocationTypeEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhereItHappened.VehicleLocationEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhoWasHurt;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhoWasInvolved;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.AlternativeName;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Assault;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Content;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.DamagedVehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.DateEntry;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.EmailAddress;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.EventTypeIndicator;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.FreeFormAddress;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Item;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.LocationDetails;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.LocationType;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Narrative;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Offender;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.OnlineReportPega;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.OrganisationOwn;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Organization;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.OrganizationDo;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Property;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.PropertyVehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Property__1;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.SomebodyElse;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.SomebodyOwns;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.StolenVehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Witness;
import nzp.digital.portal.onlinereportstaticuiservice.util.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ManageCaseServiceTest {
  @Mock RestTemplate restTemplate;

  @Autowired ModelConverter modelConverter;

  private ManageCaseService manageCaseService;

  @BeforeEach
  public void setup() {
    manageCaseService = new ManageCaseService(restTemplate, modelConverter);
  }

  @Test
  public void givenCaseIdWhenGetCaseThenReturnCase() {
    String caseId = "POLICE-SDW-WORK OR-7876";
    Map<String, String> params = new HashMap<>();
    params.put("caseId", caseId);

    OnlineReportCaseRequest expectedCase = createOnlineReport();

    OnlineReportPega pegaReport = createPegaReport();
    when(this.restTemplate.getForObject(
            endsWith("/cases/{caseId}"), eq(OnlineReportPega.class), eq(params)))
        .thenReturn(pegaReport);

    OnlineReportCaseRequest actualCase = this.manageCaseService.getCase(caseId);
    assertEquals(expectedCase, actualCase);
  }

  private OnlineReportCaseRequest createOnlineReport() {
    OnlineReportCaseRequest expectedCase = new OnlineReportCaseRequest();
    expectedCase.setNotAnEmergency(true);

    ReportType reportType = new ReportType();
    reportType.setSomethingLost(false);
    reportType.setSomethingDamaged(true);
    reportType.setSomeoneHurt(true);
    reportType.setSomethingStolen(true);
    reportType.setSomethingElse(false);
    expectedCase.setReportType(reportType);

    EventInfo eventInfo = new EventInfo();

    WhenItHappened whenItHappened = new WhenItHappened();
    whenItHappened.setStartDateTime(
        OffsetDateTime.of(
            LocalDateTime.parse(
                "2020-01-14T00:21:00.000Z",
                DateTimeFormatter.ofPattern(JacksonOffsetDateTimeMapper.DATE_TIME_ISO_FORMAT)),
            ZoneOffset.UTC));
    whenItHappened.setEndDateTime(
        OffsetDateTime.of(
            LocalDateTime.parse(
                "2020-01-14T01:21:00.313Z",
                DateTimeFormatter.ofPattern(JacksonOffsetDateTimeMapper.DATE_TIME_ISO_FORMAT)),
            ZoneOffset.UTC));
    eventInfo.setWhenItHappened(whenItHappened);

    WhereItHappened whereItHappened = new WhereItHappened();
    whereItHappened.setLocationType(LocationTypeEnum.SHOP_BUSINESS);
    whereItHappened.setLocationName("KFC");
    whereItHappened.setHowCloseToAddress(HowCloseToAddressEnum.NEARBY);
    whereItHappened.setAdditionalInformation("In the counter");

    AddressInfo addressInfo = new AddressInfo();
    addressInfo.setCannotFind(true);
    Address address = new Address();
    address.setSuburb("Avalon, Lower Hutt City");
    address.setStreetType("Street");
    address.setStreetName("Thorndon");
    address.setStreetNumber("191");
    address.setUnitNumber("9");
    address.setUnitType(UnitTypeEnum.APARTMENT);
    address.setCountry("New Zealand");
    addressInfo.setManual(address);
    whereItHappened.setAddress(addressInfo);

    whereItHappened.setStolenFromVehicle(true);
    whereItHappened.setLicencePlate("DDD222");
    whereItHappened.setVehicleLocation(VehicleLocationEnum.PUBLIC_CARPARK);

    eventInfo.setWhereItHappened(whereItHappened);

    WhoWasInvolved whoWasInvolved = new WhoWasInvolved();
    whoWasInvolved.setKnowsWho(true);
    Suspect suspect = new Suspect();
    suspect.setDescription(
        "The Other King. Yes, THAT Other King. Resides at the highest peak of Las Vegas.");
    suspect.setRelationType(RelationTypeEnum.OTHER);
    suspect.setRelationOther("Arch nemesis");
    whoWasInvolved.setWho(suspect);
    whoWasInvolved.setKnowsDescription(true);
    whoWasInvolved.setDescription(
        "Very big. Can fly. Hooded in grey. Looks pretty evil - you'll know him when you see him.");
    whoWasInvolved.setKnowsVehicle(true);
    OffenderVehicle offenderVehicle = new OffenderVehicle();
    offenderVehicle.setLicencePlate("ICKM666");
    offenderVehicle.setDescription(
        "It's an InterContinental Kabbalistic Missile. Travels pretty fast. Long and thin.");
    whoWasInvolved.setVehicle(offenderVehicle);
    whoWasInvolved.setKnowsContact(true);
    whoWasInvolved.setContact("Sohu is probably the best to talk to.");
    eventInfo.setWhoWasInvolved(whoWasInvolved);

    List<ItemDetail> stolenItems = new ArrayList<>();
    ItemDetail itemDetail1 = new ItemDetail();
    itemDetail1.setId("G42345687");
    itemDetail1.setValue("1000");
    itemDetail1.setDescription("Black Cat");
    nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item item1 =
        new nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item();
    item1.setCannotFind(false);
    SearchedItem searchedItem1 = new SearchedItem();
    searchedItem1.setCategory("Animal");
    searchedItem1.setNiaItemName("Cat");
    searchedItem1.setItemName("Cat");
    searchedItem1.setDisplayValue("I");
    item1.setSearch(searchedItem1);
    itemDetail1.setWhat(item1);
    Owner owner1 = new Owner();
    owner1.setType(TypeEnum.ME);
    owner1.setIndex(0);
    itemDetail1.setOwner(owner1);
    stolenItems.add(itemDetail1);

    ItemDetail itemDetail2 = new ItemDetail();
    itemDetail2.setId("G23456789");
    itemDetail2.setValue("2000");
    itemDetail2.setDescription("Brown Corgi");
    nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item item2 =
        new nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item();
    item2.setCannotFind(false);
    SearchedItem searchedItem2 = new SearchedItem();
    searchedItem2.setCategory("Animal");
    searchedItem2.setNiaItemName("Dog");
    searchedItem2.setItemName("Dog");
    searchedItem2.setDisplayValue("Somebody");
    item2.setSearch(searchedItem2);
    itemDetail2.setWhat(item2);
    Owner owner2 = new Owner();
    owner2.setType(TypeEnum.OTHER_PERSON);
    owner2.setIndex(0);
    itemDetail2.setOwner(owner2);
    stolenItems.add(itemDetail2);

    ItemDetail itemDetail3 = new ItemDetail();
    itemDetail3.setValue("200");
    itemDetail3.setDescription("Yellow Duck");
    nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item item3 =
        new nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item();
    item3.setCannotFind(true);
    item3.setManual("Duck");
    itemDetail3.setWhat(item3);
    Owner owner3 = new Owner();
    owner3.setType(TypeEnum.ORGANISATION);
    owner3.setIndex(0);
    itemDetail3.setOwner(owner3);
    stolenItems.add(itemDetail3);

    eventInfo.setStolenItems(stolenItems);

    DamagedVehicles damagedVehicles = new DamagedVehicles();
    damagedVehicles.setAnyDamaged(true);
    List<Vehicle> vehicles = new ArrayList<>();
    Vehicle vehicle1 = new Vehicle();
    vehicle1.setType(Vehicle.TypeEnum.OTHER);
    vehicle1.setMake("A flying kayak");
    vehicle1.setHowAndWhere("It was pretty much blown to smithereens, I'm telling you.");
    vehicle1.setLocation(LocationEnum.PUBLIC_CARPARK);
    vehicle1.setLicencePlate("KAYAK4");
    vehicle1.setDescription("It flies. The only other I know of is Sohu's, so it's pretty unique.");
    vehicle1.setWasMoved(true);
    vehicle1.setIgnitionTamperedWith(false);
    Owner ownerOfDamagedVehicle = new Owner();
    ownerOfDamagedVehicle.setType(TypeEnum.OTHER_PERSON);
    ownerOfDamagedVehicle.setIndex(1);
    vehicle1.setOwner(ownerOfDamagedVehicle);
    vehicles.add(vehicle1);
    damagedVehicles.setVehicles(vehicles);
    eventInfo.setDamagedVehicles(damagedVehicles);

    List<ItemDetail> damagedItems = new ArrayList<>();
    ItemDetail damagedItemDetail = new ItemDetail();
    damagedItemDetail.setDescription(
        "Giant 10 towered supercomputer called THARMAS that controls the nuclear capability of the Untied States. The Other King scorched it unto blue flames.");
    nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item damagedItem =
        new nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item();
    damagedItem.setCannotFind(true);
    damagedItem.setManual("Mobile Disco");
    damagedItemDetail.setWhat(damagedItem);
    Owner owner = new Owner();
    owner.setType(TypeEnum.ORGANISATION);
    owner.setIndex(1);
    damagedItemDetail.setOwner(owner);
    damagedItems.add(damagedItemDetail);

    eventInfo.setDamagedItems(damagedItems);

    StolenVehicles stolenVehicles = new StolenVehicles();
    stolenVehicles.setAnyStolen(true);
    List<MissingVehicle> missingVehicles = new ArrayList<>();
    MissingVehicle missingVehicle = new MissingVehicle();
    missingVehicle.setAgreesToTow(true);
    missingVehicle.setDescription("A superfast yacht with seven sails");
    missingVehicle.setLicencePlate("abcdfe");
    missingVehicle.setLocation(MissingVehicle.LocationEnum.DRIVEWAY);
    missingVehicle.setType(MissingVehicle.TypeEnum.CAR);
    missingVehicle.setMake("BMW");
    Owner ownerOfMissingVehicle = new Owner();
    ownerOfMissingVehicle.setType(TypeEnum.OTHER_PERSON);
    ownerOfMissingVehicle.setIndex(2);
    missingVehicle.setOwner(ownerOfMissingVehicle);
    missingVehicles.add(missingVehicle);
    stolenVehicles.setVehicles(missingVehicles);
    eventInfo.setStolenVehicles(stolenVehicles);

    WhoWasHurt whoWasHurt = new WhoWasHurt();
    whoWasHurt.setWho("Caelius West");
    whoWasHurt.setHow("He was sent up in a blaze of blue flame. There was only ash left.");
    eventInfo.setWhoWasHurt(whoWasHurt);

    WhatHappened whatHappened = new WhatHappened();
    whatHappened.setDescription("Tell what happened");
    whatHappened.setWhy("Something about whatever power in the Universe meted out judgment.");
    eventInfo.setWhatHappened(whatHappened);

    expectedCase.setEventInfo(eventInfo);

    Contacts contacts = new Contacts();
    PersonalContact personalContact = new PersonalContact();
    Person person = new Person();
    Name name = new Name();
    name.setFirstName("jack");
    name.setMiddleName("lucky");
    name.setLastName("luo");
    person.setName(name);
    person.setHasPreviousName(true);
    Name previousName = new Name();
    previousName.setFirstName("jack");
    previousName.setMiddleName("amy");
    previousName.setLastName("luo");
    person.setPreviousName(previousName);
    person.setGender(GenderEnum.MALE);
    person.setDriverLicence("DT222333");
    person.setDateOfBirth("01-01-2002");
    personalContact.setPersonalDetails(person);
    ContactDetail contactDetail = new ContactDetail();
    contactDetail.setPreferredContactMethod(PreferredContactMethodEnum.EMAIL);
    contactDetail.setEmails(Arrays.asList("jack@gmail.com"));
    Phone phone = new Phone();
    phone.setCountry("64");
    phone.setType(Phone.TypeEnum.MOBILE);
    phone.setArea("21");
    phone.setNumber("232232");
    contactDetail.setPhoneNumbers(Arrays.asList(phone));
    contactDetail.setAddress(addressInfo);
    personalContact.setContactDetails(contactDetail);

    contacts.setReporter(personalContact);

    List<PersonalContact> otherPeople = new ArrayList<>();
    otherPeople.add(createPersonalContact(0, addressInfo));
    otherPeople.add(createPersonalContact(1, addressInfo));
    otherPeople.add(createPersonalContact(2, addressInfo));
    contacts.setOtherPeople(otherPeople);

    List<OrganisationContact> organisations = new ArrayList<>();
    organisations.add(createOrganisationContact(0));
    organisations.add(createOrganisationContact(1));
    contacts.setOrganisations(organisations);

    expectedCase.setContacts(contacts);
    return expectedCase;
  }

  private OrganisationContact createOrganisationContact(int index) {
    OrganisationContact organisationContact = new OrganisationContact();
    organisationContact.setName("Citadel West" + index);
    ContactDetail orgContactDetail = new ContactDetail();
    List<Phone> phoneNumbers = new ArrayList<>();
    Phone orgPhone = new Phone();
    orgPhone.setCountry("64");
    orgPhone.setArea(String.valueOf(21 + index));
    orgPhone.setNumber("999999" + index);
    orgPhone.setType(Phone.TypeEnum.WORK);
    phoneNumbers.add(orgPhone);
    orgContactDetail.setPhoneNumbers(phoneNumbers);
    AddressInfo orgAddress = new AddressInfo();
    orgAddress.setCannotFind(false);
    orgAddress.setSearch((96 + index) + " LAKE TERRACE, TAUPO, TAUPO DISTRICT 3330");
    orgContactDetail.setAddress(orgAddress);
    organisationContact.setContactDetails(orgContactDetail);
    return organisationContact;
  }

  private PersonalContact createPersonalContact(int index, AddressInfo addressInfo) {
    PersonalContact otherPersonContact = new PersonalContact();
    Person otherPerson = new Person();
    Name otherPersonName = new Name();
    otherPersonName.setFirstName("joyce" + index);
    otherPersonName.setMiddleName("lucky" + index);
    otherPersonName.setLastName("luo" + index);
    otherPerson.setName(otherPersonName);
    otherPerson.setGender(GenderEnum.FEMALE);
    otherPerson.setRelation(RelationEnum.EX_BOY_GIRL_FRIEND);
    otherPerson.setDateOfBirth(String.valueOf(33 + index));
    otherPersonContact.setPersonalDetails(otherPerson);
    ContactDetail otherPersonContactDetail = new ContactDetail();
    otherPersonContactDetail.setEmails(Arrays.asList("joyce" + index + "@gmail.com"));
    Phone otherPersonPhone = new Phone();
    otherPersonPhone.setCountry("64");
    otherPersonPhone.setType(Phone.TypeEnum.MOBILE);
    otherPersonPhone.setArea(String.valueOf(21 + index));
    otherPersonPhone.setNumber("222222" + index);
    otherPersonContactDetail.setPhoneNumbers(Arrays.asList(otherPersonPhone));
    otherPersonContactDetail.setAddress(addressInfo);
    otherPersonContact.setContactDetails(otherPersonContactDetail);
    return otherPersonContact;
  }

  private OnlineReportPega createPegaReport() {
    OnlineReportPega pegaReport = new OnlineReportPega();
    Content reportContent = new Content();

    EventTypeIndicator eventTypeIndicator = new EventTypeIndicator();
    eventTypeIndicator.setBeenDamaged(true);
    eventTypeIndicator.setBeenHurt(true);
    eventTypeIndicator.setBeenStolen(true);
    eventTypeIndicator.setLostProperty(false);
    eventTypeIndicator.setOther(false);
    reportContent.setEventTypeIndicator(eventTypeIndicator);

    DateEntry dateEntry = new DateEntry();
    dateEntry.setFromDateTime("2020-01-14T00:21:00.000Z");
    dateEntry.setToDateTime("2020-01-14T01:21:00.313Z");
    reportContent.setDateEntry(dateEntry);

    LocationType locationType = new LocationType();
    locationType.setLocationTypeIndicator("A shop/business");
    LocationDetails locationDetails = new LocationDetails();
    locationDetails.setLocationName("KFC");
    locationDetails.setNotInLookUp(true);
    locationDetails.setProximity("Nearby");
    locationDetails.setAdditonalLocationInfo("In the counter");

    FreeFormAddress freeFormAddress = new FreeFormAddress();
    freeFormAddress.setCountry("New Zealand");
    freeFormAddress.setIsOverSee("New Zealand");
    freeFormAddress.setStreetDirectionSuffix("North East");
    freeFormAddress.setStreetName("Thorndon");
    freeFormAddress.setStreetNumber("191");
    freeFormAddress.setStreetType("Street");
    freeFormAddress.setTownSuburb("Avalon, Lower Hutt City");
    freeFormAddress.setUnitNumber("9");
    freeFormAddress.setUnitType("Apartment");

    locationDetails.setFreeFormAddress(freeFormAddress);
    locationType.setLocationDetails(locationDetails);
    reportContent.setLocationType(locationType);

    reportContent.setStolenfromavehicleindicator("Yes");
    Property__1 property__1 = new Property__1();
    PropertyVehicle propertyVehicle = new PropertyVehicle();
    propertyVehicle.setRegistration("DDD222");
    propertyVehicle.setVehicleLocation("Parked in a public carpark");
    property__1.setPropertyVehicle(propertyVehicle);
    reportContent.setProperty(property__1);

    Offender offender = new Offender();
    offender.setKnownIndicator("Yes");
    offender.setDescription(
        "The Other King. Yes, THAT Other King. Resides at the highest peak of Las Vegas.");
    offender.setRelationshipToVictim("Other");
    offender.setOtherQuestion("Arch nemesis");
    offender.setDescriptionQuestion("Yes");
    offender.setOffenderDescription(
        "Very big. Can fly. Hooded in grey. Looks pretty evil - you'll know him when you see him.");
    offender.setSeenOffender("Yes");
    offender.setVehicleRegistration("ICKM666");
    offender.setVehicleDescription(
        "It's an InterContinental Kabbalistic Missile. Travels pretty fast. Long and thin.");
    Witness witness = new Witness();
    witness.setWitnessIndicator("Yes");
    witness.setNameContact("Sohu is probably the best to talk to.");
    offender.setWitness(witness);
    reportContent.setOffender(offender);

    List<Item> items = new ArrayList<>();
    Item item1 = new Item();
    item1.setCategory("Animal");
    item1.setDisplayValue("I");
    item1.setItemDescription("Black Cat");
    item1.setItemName("Cat");
    item1.setItemValueDoller("1000");
    item1.setNIAItemName("Cat");
    item1.setNotInLookUp(false);
    item1.setSerialNumber("G42345687");
    item1.setWhoOwnsIt("I do");
    items.add(item1);

    Item item2 = new Item();
    item2.setCategory("Animal");
    item2.setDisplayValue("Somebody");
    item2.setItemDescription("Brown Corgi");
    item2.setItemName("Dog");
    item2.setItemValueDoller("2000");
    item2.setNIAItemName("Dog");
    item2.setNotInLookUp(false);
    item2.setSerialNumber("G23456789");
    item2.setWhoOwnsIt("Somebody else does");
    item2.setSomebodyOwns(createPegaSomebodyOwns(0));
    items.add(item2);

    Item item3 = new Item();
    item3.setDisplayValue("Organisation");
    item3.setItemDescription("Yellow Duck");
    item3.setItemName("Duck");
    item3.setItemValueDoller("200");
    item3.setNotInLookUp(true);
    item3.setWhoOwnsIt("An organisation does");
    item3.setOrganisationOwn(createPegaOrganisationOwn(0));
    items.add(item3);

    reportContent.setItem(items);

    List<DamagedVehicle> damagedVehicles = new ArrayList<>();
    DamagedVehicle damagedVehicle = new DamagedVehicle();
    damagedVehicle.setVehicleType("Other");
    damagedVehicle.setMake("A flying kayak");
    damagedVehicle.setDescription("It was pretty much blown to smithereens, I'm telling you.");
    damagedVehicle.setVehicleLocation("Parked in a public carpark");
    damagedVehicle.setRegistration("KAYAK4");
    damagedVehicle.setVehicleDescription(
        "It flies. The only other I know of is Sohu's, so it's pretty unique.");
    damagedVehicle.setMovedTampered("Yes");
    damagedVehicle.setVehicleTampered("No");
    damagedVehicle.setWhoOwnsIt("joyce1 luo1");
    damagedVehicle.setOwner(createPegaOwner(1));

    damagedVehicles.add(damagedVehicle);

    reportContent.setVehicleDamagedIndicator("Yes");
    reportContent.setDamagedVehicle(damagedVehicles);

    List<Property> properties = new ArrayList<>();
    Property property = new Property();
    property.setNotInLookUp(true);
    property.setDamageDescription(
        "Giant 10 towered supercomputer called THARMAS that controls the nuclear capability of the Untied States. The Other King scorched it unto blue flames.");
    property.setPropertyType("Mobile Disco");
    property.setWhoOwnsIt("Citadel West1");
    property.setOrganization(createPegaOrganization(1));
    properties.add(property);
    reportContent.setProperties(properties);

    List<StolenVehicle> stolenVehicles = new ArrayList<>();
    StolenVehicle stolenVehicle = new StolenVehicle();
    stolenVehicle.setMake("BMW");
    stolenVehicle.setRegistration("abcdfe");
    stolenVehicle.setVehicleDescription("A superfast yacht with seven sails");
    stolenVehicle.setVehicleType("Car");
    stolenVehicle.setVehicleLocation("Parked on a driveway");
    stolenVehicle.setWhoOwnsIt("Somebody else does");
    stolenVehicle.setOwner(createPegaOwner(2));
    stolenVehicle.setSomeonesTowingApproval("Yes");
    stolenVehicles.add(stolenVehicle);
    reportContent.setStolenVehicle(stolenVehicles);
    reportContent.setVehicleStolenIndicator("Yes");

    Assault assault = new Assault();
    assault.setWhoInjured("Caelius West");
    assault.setHowInjured("He was sent up in a blaze of blue flame. There was only ash left.");
    reportContent.setAssault(assault);

    Narrative narrative = new Narrative();
    narrative.setNarrativeText("Tell what happened");
    narrative.setReasonWhy("Something about whatever power in the Universe meted out judgment.");
    reportContent.setNarrative(narrative);

    nzp.digital.portal.onlinereportstaticuiservice.model.pega.Person person =
        new nzp.digital.portal.onlinereportstaticuiservice.model.pega.Person();
    person.setDob("01-01-2002");
    person.setFirstName("jack");
    person.setMiddleName("lucky");
    person.setSurName("luo");
    person.setLicenseNumber("DT222333");
    AlternativeName alternativeName = new AlternativeName();
    alternativeName.setFirstName("jack");
    alternativeName.setMiddleName("amy");
    alternativeName.setLastName("luo");
    person.setKnowByOtherName(true);
    person.setAlternativeName(alternativeName);
    EmailAddress emailAddress = new EmailAddress();
    emailAddress.setEmail("jack@gmail.com");
    person.setEmailAddress(Arrays.asList(emailAddress));
    person.setGender("Male");
    person.setPreferedContact("Email");
    person.setNotInLookUp(true);
    person.setFreeFormAddress(freeFormAddress);

    nzp.digital.portal.onlinereportstaticuiservice.model.pega.Phone phone =
        new nzp.digital.portal.onlinereportstaticuiservice.model.pega.Phone();
    phone.setCountryCode("64");
    phone.setAreaCodeText("21");
    phone.setPhoneNumberText("232232");
    phone.setPhoneType("Mobile");
    person.setPhones(Arrays.asList(phone));

    reportContent.setPerson(person);

    List<SomebodyElse> somebodyElseList = new ArrayList<>();
    somebodyElseList.add(createPegaSomebodyElse(0, freeFormAddress));
    somebodyElseList.add(createPegaSomebodyElse(1, freeFormAddress));
    somebodyElseList.add(createPegaSomebodyElse(2, freeFormAddress));
    reportContent.setSomebodyElse(somebodyElseList);

    List<OrganizationDo> organizations = new ArrayList<>();
    organizations.add(createPegaOrganizationDoes(0));
    organizations.add(createPegaOrganizationDoes(1));
    reportContent.setOrganizationDoes(organizations);

    pegaReport.setContent(reportContent);

    return pegaReport;
  }

  private OrganizationDo createPegaOrganizationDoes(int index) {
    OrganizationDo organizationDo = new OrganizationDo();
    organizationDo.setNotInLookUp(false);
    List<nzp.digital.portal.onlinereportstaticuiservice.model.pega.Phone> orgPhones =
        new ArrayList<>();
    nzp.digital.portal.onlinereportstaticuiservice.model.pega.Phone orgPhone =
        new nzp.digital.portal.onlinereportstaticuiservice.model.pega.Phone();
    orgPhone.setPhoneType("Work");
    orgPhone.setCountryCode("64");
    orgPhone.setAreaCodeText(String.valueOf(21 + index));
    orgPhone.setPhoneNumberText("999999" + index);
    orgPhones.add(orgPhone);
    organizationDo.setPhones(orgPhones);
    Organization organization = new Organization();
    organization.setOrganisationName("Citadel West" + index);
    organizationDo.setOrganization(organization);
    nzp.digital.portal.onlinereportstaticuiservice.model.pega.Address orgAddress =
        new nzp.digital.portal.onlinereportstaticuiservice.model.pega.Address();
    orgAddress.setAddressCSV((96 + index) + " LAKE TERRACE, TAUPO, TAUPO DISTRICT 3330");
    organizationDo.setAddress(orgAddress);
    return organizationDo;
  }

  private SomebodyElse createPegaSomebodyElse(int index, FreeFormAddress freeFormAddress) {
    SomebodyElse somebodyElse = new SomebodyElse();
    somebodyElse.setAgeOrBirth(String.valueOf(33 + index));
    somebodyElse.setFirstName("joyce" + index);
    somebodyElse.setMiddleName("lucky" + index);
    somebodyElse.setSurName("luo" + index);
    somebodyElse.setRelation("Ex- boyfriend/girlfriend");
    EmailAddress somebodyElseEmailAddress = new EmailAddress();
    somebodyElseEmailAddress.setEmail("joyce" + index + "@gmail.com");
    somebodyElse.setEmailAddress(Arrays.asList(somebodyElseEmailAddress));
    somebodyElse.setGender("Female");
    somebodyElse.setNotInLookUp(true);
    somebodyElse.setFreeFormAddress(freeFormAddress);

    nzp.digital.portal.onlinereportstaticuiservice.model.pega.Phone somebodyElsePhone =
        new nzp.digital.portal.onlinereportstaticuiservice.model.pega.Phone();
    somebodyElsePhone.setCountryCode("64");
    somebodyElsePhone.setAreaCodeText(String.valueOf(21 + index));
    somebodyElsePhone.setPhoneNumberText("222222" + index);
    somebodyElsePhone.setPhoneType("Mobile");
    somebodyElse.setPhones(Arrays.asList(somebodyElsePhone));
    return somebodyElse;
  }

  private Organization createPegaOrganization(int index) {
    Organization organization = new Organization();
    organization.setOrganisationName("Citadel West" + index);
    return organization;
  }

  private nzp.digital.portal.onlinereportstaticuiservice.model.pega.Owner createPegaOwner(int index) {
    nzp.digital.portal.onlinereportstaticuiservice.model.pega.Owner owner1 =
        new nzp.digital.portal.onlinereportstaticuiservice.model.pega.Owner();
    owner1.setFname("joyce" + index);
    owner1.setMname("lucky" + index);
    owner1.setSname("luo" + index);
    return owner1;
  }

  private OrganisationOwn createPegaOrganisationOwn(int index) {
    OrganisationOwn organisationOwn = new OrganisationOwn();
    Organization organization = new Organization();
    organization.setOrganisationName("Citadel West" + index);
    organisationOwn.setOrganization(organization);
    return organisationOwn;
  }

  private SomebodyOwns createPegaSomebodyOwns(int index) {
    SomebodyOwns somebodyOwn = new SomebodyOwns();
    somebodyOwn.setFirstName("joyce" + index);
    somebodyOwn.setMiddleName("lucky" + index);
    somebodyOwn.setSurName("luo" + index);
    return somebodyOwn;
  }

  @Test
  public void givenErrorOccurredWhenGetCaseThenThrowException() {
    String caseId = "POLICE-SDW-WORK OR-7876";
    Map<String, String> params = new HashMap<>();
    params.put("caseId", caseId);

    when(this.restTemplate.getForObject(
            endsWith("/cases/{caseId}"), eq(OnlineReportPega.class), eq(params)))
        .thenThrow(new RestClientException("Error occurred"));

    assertThrows(RestClientException.class, () -> manageCaseService.getCase(caseId));
  }
}
