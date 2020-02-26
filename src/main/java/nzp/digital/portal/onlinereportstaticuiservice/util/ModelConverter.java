package nzp.digital.portal.onlinereportstaticuiservice.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.log4j.Log4j2;
import nzp.digital.portal.onlinereportstaticuiservice.config.EnumMappingProperties;
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
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.LocationType;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Narrative;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Offender;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.OrganisationOwn;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Organization;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.OrganizationDo;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.OrganizationItem;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Property;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.PropertyVehicle;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.SomebodyElse;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.SomebodyOwns;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.StolenVehicle;
import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

@Log4j2
@Component
public class ModelConverter {

  private static final int OWNER_INDEX_NOT_FOUND = -1;
  private ModelMapper modelMapper;
  private EnumMappingProperties enumMappingProperties;

  @Autowired
  public ModelConverter(EnumMappingProperties enumMappingProperties) {
    Assert.notNull(enumMappingProperties, "EnumMappingProperties injection must not be null");
    this.modelMapper = new ModelMapper();
    this.enumMappingProperties = enumMappingProperties;
  }

  public OnlineReportCaseRequest convertToApiModel(Content pegaReportContent) {
    OnlineReportCaseRequest apiReportCase =
        modelMapper.map(pegaReportContent, OnlineReportCaseRequest.class);
    apiReportCase.setNotAnEmergency(true);
    apiReportCase.setReportType(convertToReportType(pegaReportContent.getEventTypeIndicator()));

    apiReportCase.setEventInfo(
        convertToEventInfo(apiReportCase.getReportType(), pegaReportContent));

    apiReportCase.setContacts(convertToContacts(pegaReportContent));

    return apiReportCase;
  }

  private Boolean isYes(String indicator) {
    if (Objects.isNull(indicator)) {
      return null;
    }

    return indicator.equalsIgnoreCase("yes");
  }

  private EventInfo convertToEventInfo(ReportType reportType, Content pegaReportContent) {
    EventInfo eventInfo = new EventInfo();
    eventInfo.setWhenItHappened(convertToWhenItHappened(pegaReportContent.getDateEntry()));
    eventInfo.setWhereItHappened(convertToWhereItHappened(pegaReportContent));

    if (reportType.getSomethingLost()) {
      eventInfo.setLostItems(convertToItemDetails(pegaReportContent));
    } else if (reportType.getSomethingStolen()) {
      eventInfo.setStolenItems(convertToItemDetails(pegaReportContent));
    }

    eventInfo.setWhoWasInvolved(convertToWhoWasInvolved(pegaReportContent.getOffender()));

    eventInfo.setDamagedVehicles(convertToDamagedVehicles(pegaReportContent));

    eventInfo.setDamagedItems(convertToDamagedItemDetails(pegaReportContent));

    eventInfo.setStolenVehicles(convertToStolenVehicles(pegaReportContent));

    if (reportType.getSomeoneHurt()) {
      eventInfo.setWhoWasHurt(convertToWhoWasHurt(pegaReportContent.getAssault()));
    }

    eventInfo.setWhatHappened(convertToWhatHappened(pegaReportContent.getNarrative()));

    return eventInfo;
  }

  private WhoWasHurt convertToWhoWasHurt(Assault assault) {
    if (Objects.isNull(assault)) {
      return null;
    }

    WhoWasHurt whoWasHurt = new WhoWasHurt();
    whoWasHurt.setWho(assault.getWhoInjured());
    whoWasHurt.setHow(assault.getHowInjured());
    return whoWasHurt;
  }

  private StolenVehicles convertToStolenVehicles(Content pegaReportContent) {
    if (BooleanUtils.isNotTrue(isYes(pegaReportContent.getVehicleStolenIndicator()))) {
      return null;
    }

    StolenVehicles stolenVehicles = new StolenVehicles();
    stolenVehicles.setAnyStolen(true);
    stolenVehicles.setVehicles(convertToMissingVehicles(pegaReportContent));

    return stolenVehicles;
  }

  private List<MissingVehicle> convertToMissingVehicles(Content pegaContent) {
    List<StolenVehicle> stolenVehicles = pegaContent.getStolenVehicle();
    if (CollectionUtils.isEmpty(stolenVehicles)) {
      return Collections.emptyList();
    }

    return stolenVehicles.stream()
        .map(
            stolenVehicle -> {
              MissingVehicle missingVehicle = new MissingVehicle();
              missingVehicle.setDescription(stolenVehicle.getVehicleDescription());
              missingVehicle.setMake(stolenVehicle.getMake());
              missingVehicle.setLicencePlate(stolenVehicle.getRegistration());
              missingVehicle.setLocation(
                  this.enumMappingProperties.getMappedEnum(
                      MissingVehicle.LocationEnum.class, stolenVehicle.getVehicleLocation()));
              missingVehicle.setType(
                  this.enumMappingProperties.getMappedEnum(
                      MissingVehicle.TypeEnum.class, stolenVehicle.getVehicleType()));
              Owner owner =
                  convertToOwner(
                      stolenVehicle.getWhoOwnsIt(),
                      pegaContent,
                      () ->
                          this.getOtherPersonName(
                              stolenVehicle.getOwner(), stolenVehicle.getSomebodyOwns()),
                      () ->
                          this.getOrganizationName(
                              stolenVehicle.getOrganization(),
                              stolenVehicle.getOrganisationOwn(),
                              stolenVehicle.getOrganizationItem()));
              missingVehicle.setOwner(owner);

              missingVehicle.setAgreesToTow(
                  BooleanUtils.isTrue(isYes(getTowingApproval(stolenVehicle))));

              return missingVehicle;
            })
        .collect(Collectors.toList());
  }

  private String getTowingApproval(StolenVehicle stolenVehicle) {
    return Objects.nonNull(stolenVehicle.getSelfTowingApproval())
        ? stolenVehicle.getSelfTowingApproval()
        : stolenVehicle.getSomeonesTowingApproval();
  }

  private DamagedVehicles convertToDamagedVehicles(Content pegaReportContent) {
    if (BooleanUtils.isNotTrue(isYes(pegaReportContent.getVehicleDamagedIndicator()))) {
      return null;
    }

    DamagedVehicles damagedVehicles = new DamagedVehicles();
    damagedVehicles.setAnyDamaged(true);
    damagedVehicles.setVehicles(convertToVehicles(pegaReportContent));

    return damagedVehicles;
  }

  private List<Vehicle> convertToVehicles(Content pegaContent) {
    List<DamagedVehicle> damagedVehicles = pegaContent.getDamagedVehicle();
    if (CollectionUtils.isEmpty(damagedVehicles)) {
      return Collections.emptyList();
    }

    return damagedVehicles.stream()
        .map(
            damagedVehicle -> {
              Vehicle vehicle = new Vehicle();
              vehicle.setDescription(damagedVehicle.getVehicleDescription());
              vehicle.setMake(damagedVehicle.getMake());
              vehicle.setLicencePlate(damagedVehicle.getRegistration());
              vehicle.setWasMoved(BooleanUtils.isTrue(isYes(damagedVehicle.getMovedTampered())));
              vehicle.setIgnitionTamperedWith(
                  BooleanUtils.isTrue(isYes(damagedVehicle.getVehicleTampered())));
              vehicle.setLocation(
                  this.enumMappingProperties.getMappedEnum(
                      LocationEnum.class, damagedVehicle.getVehicleLocation()));
              vehicle.setType(
                  this.enumMappingProperties.getMappedEnum(
                      Vehicle.TypeEnum.class, damagedVehicle.getVehicleType()));
              Owner owner =
                  convertToOwner(
                      damagedVehicle.getWhoOwnsIt(),
                      pegaContent,
                      () ->
                          this.getOtherPersonName(
                              damagedVehicle.getOwner(), damagedVehicle.getSomebodyOwns()),
                      () ->
                          this.getOrganizationName(
                              damagedVehicle.getOrganization(),
                              damagedVehicle.getOrganisationOwn(),
                              damagedVehicle.getOrganizationItem()));
              vehicle.setOwner(owner);

              vehicle.setHowAndWhere(damagedVehicle.getDescription());

              return vehicle;
            })
        .collect(Collectors.toList());
  }

  private WhoWasInvolved convertToWhoWasInvolved(Offender offender) {
    if (Objects.isNull(offender)) {
      return null;
    }

    WhoWasInvolved whoWasInvolved = new WhoWasInvolved();
    whoWasInvolved.setKnowsWho(BooleanUtils.isTrue(isYes(offender.getKnownIndicator())));
    if (whoWasInvolved.getKnowsWho()) {
      Suspect suspect = new Suspect();
      suspect.setDescription(offender.getDescription());
      suspect.setRelationType(
          this.enumMappingProperties.getMappedEnum(
              RelationTypeEnum.class, offender.getRelationshipToVictim()));
      suspect.setRelationOther(offender.getOtherQuestion());
      whoWasInvolved.setWho(suspect);
    }

    whoWasInvolved.setKnowsDescription(
        BooleanUtils.isTrue(isYes(offender.getDescriptionQuestion())));
    if (whoWasInvolved.getKnowsDescription()) {
      whoWasInvolved.setDescription(offender.getOffenderDescription());
    }

    whoWasInvolved.setKnowsVehicle(BooleanUtils.isTrue(isYes(offender.getSeenOffender())));
    if (whoWasInvolved.getKnowsVehicle()) {
      OffenderVehicle offenderVehicle = new OffenderVehicle();
      offenderVehicle.setLicencePlate(offender.getVehicleRegistration());
      offenderVehicle.setDescription(offender.getVehicleDescription());

      whoWasInvolved.setVehicle(offenderVehicle);
    }

    whoWasInvolved.setKnowsContact(
        Objects.nonNull(offender.getWitness())
            && BooleanUtils.isTrue(isYes(offender.getWitness().getWitnessIndicator())));
    if (whoWasInvolved.getKnowsContact()) {
      whoWasInvolved.setContact(offender.getWitness().getNameContact());
    }

    return whoWasInvolved;
  }

  private Contacts convertToContacts(Content pegaReportContent) {
    Contacts contacts = new Contacts();

    contacts.setReporter(convertToReporter(pegaReportContent.getPerson()));
    contacts.setOtherPeople(convertToOtherPeople(pegaReportContent.getSomebodyElse()));
    contacts.setOrganisations(convertToOrganisations(pegaReportContent.getOrganizationDoes()));

    return contacts;
  }

  private List<OrganisationContact> convertToOrganisations(List<OrganizationDo> pegaOrganizations) {
    if (Objects.isNull(pegaOrganizations)) {
      return null;
    }

    return pegaOrganizations.stream()
        .map(
            pegaOrganization -> {
              OrganisationContact organisationContact = new OrganisationContact();
              organisationContact.setName(pegaOrganization.getOrganization().getOrganisationName());
              organisationContact.setContactDetails(convertToContactDetails(pegaOrganization));
              return organisationContact;
            })
        .collect(Collectors.toList());
  }

  private List<PersonalContact> convertToOtherPeople(List<SomebodyElse> pegaSomebodyElseList) {
    if (Objects.isNull(pegaSomebodyElseList)) {
      return null;
    }

    return pegaSomebodyElseList.stream()
        .map(pegaSomebodyElse -> convertToOtherPerson(pegaSomebodyElse))
        .collect(Collectors.toList());
  }

  private PersonalContact convertToOtherPerson(SomebodyElse pegaSomebodyElse) {
    PersonalContact personalContact = new PersonalContact();
    personalContact.setPersonalDetails(convertToPersonalDetails(pegaSomebodyElse));
    personalContact.setContactDetails(convertToContactDetails(pegaSomebodyElse));
    personalContact.setVictimSupportRequired(isYes(pegaSomebodyElse.getVictimSupport()));

    return personalContact;
  }

  private PersonalContact convertToReporter(
      nzp.digital.portal.onlinereportstaticuiservice.model.pega.Person person) {
    PersonalContact reporter = new PersonalContact();
    reporter.setPersonalDetails(convertToPersonalDetails(person));
    reporter.setContactDetails(convertToContactDetails(person));
    reporter.setVictimSupportRequired(isYes(person.getVictimSupport()));

    return reporter;
  }

  private ContactDetail convertToContactDetails(
      nzp.digital.portal.onlinereportstaticuiservice.model.pega.Person pegaPerson) {
    ContactDetail contactDetail = new ContactDetail();
    contactDetail.setPreferredContactMethod(
        this.enumMappingProperties.getMappedEnum(
            PreferredContactMethodEnum.class, pegaPerson.getPreferedContact()));
    contactDetail.setAddress(
        convertToAddressInfo(
            pegaPerson.getNotInLookUp(),
            pegaPerson.getFreeFormAddress(),
            pegaPerson.getAddress(),
            pegaPerson.getAddressType()));
    contactDetail.setEmails(convertToEmails(pegaPerson.getEmailAddress()));
    contactDetail.setPhoneNumbers(convertToPhones(pegaPerson.getPhones()));
    return contactDetail;
  }

  private ContactDetail convertToContactDetails(
      nzp.digital.portal.onlinereportstaticuiservice.model.pega.SomebodyElse pegaSomebodyElse) {
    ContactDetail contactDetail = new ContactDetail();
    contactDetail.setAddress(
        convertToAddressInfo(
            pegaSomebodyElse.getNotInLookUp(),
            pegaSomebodyElse.getFreeFormAddress(),
            pegaSomebodyElse.getAddress(),
            pegaSomebodyElse.getAddressType()));
    contactDetail.setEmails(convertToEmails(pegaSomebodyElse.getEmailAddress()));
    contactDetail.setPhoneNumbers(convertToPhones(pegaSomebodyElse.getPhones()));
    return contactDetail;
  }

  private ContactDetail convertToContactDetails(
      nzp.digital.portal.onlinereportstaticuiservice.model.pega.OrganizationDo pegaOrganization) {
    ContactDetail contactDetail = new ContactDetail();
    contactDetail.setAddress(
        convertToAddressInfo(
            pegaOrganization.getNotInLookUp(),
            pegaOrganization.getFreeFormAddress(),
            pegaOrganization.getAddress(),
            null));
    contactDetail.setPhoneNumbers(convertToPhones(pegaOrganization.getPhones()));
    return contactDetail;
  }

  private List<Phone> convertToPhones(
      List<nzp.digital.portal.onlinereportstaticuiservice.model.pega.Phone> pegaPhones) {
    if (Objects.isNull(pegaPhones) || CollectionUtils.isEmpty(pegaPhones)) {
      return Collections.emptyList();
    }

    return pegaPhones.stream()
        .map(
            pegaPhone -> {
              Phone phone = new Phone();
              phone.setType(
                  this.enumMappingProperties.getMappedEnum(
                      Phone.TypeEnum.class, pegaPhone.getPhoneType()));
              phone.setCountry(pegaPhone.getCountryCode());
              phone.setArea(pegaPhone.getAreaCodeText());
              phone.setNumber(pegaPhone.getPhoneNumberText());
              return phone;
            })
        .collect(Collectors.toList());
  }

  private List<String> convertToEmails(List<EmailAddress> pegaEmails) {
    if (Objects.isNull(pegaEmails) || CollectionUtils.isEmpty(pegaEmails)) {
      return Collections.emptyList();
    }

    return pegaEmails.stream()
        .map(pegaEmail -> pegaEmail.getEmail())
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private Person convertToPersonalDetails(
      nzp.digital.portal.onlinereportstaticuiservice.model.pega.Person pegaPerson) {
    Person person = new Person();
    person.setName(
        convertToName(
            pegaPerson.getFirstName(), pegaPerson.getMiddleName(), pegaPerson.getSurName()));
    person.setDateOfBirth(pegaPerson.getDob());
    person.setDriverLicence(pegaPerson.getLicenseNumber());
    person.setGender(
        this.enumMappingProperties.getMappedEnum(GenderEnum.class, pegaPerson.getGender()));

    person.setHasPreviousName(pegaPerson.getKnowByOtherName());
    if (person.getHasPreviousName()) {
      AlternativeName previousName = pegaPerson.getAlternativeName();
      person.setPreviousName(
          convertToName(
              previousName.getFirstName(),
              previousName.getMiddleName(),
              previousName.getLastName()));
    }

    return person;
  }

  private Person convertToPersonalDetails(
      nzp.digital.portal.onlinereportstaticuiservice.model.pega.SomebodyElse somebodyElse) {
    Person person = new Person();
    person.setName(
        convertToName(
            somebodyElse.getFirstName(), somebodyElse.getMiddleName(), somebodyElse.getSurName()));
    person.setDateOfBirth(somebodyElse.getAgeOrBirth());
    person.setGender(
        this.enumMappingProperties.getMappedEnum(GenderEnum.class, somebodyElse.getGender()));
    person.setRelation(
        this.enumMappingProperties.getMappedEnum(RelationEnum.class, somebodyElse.getRelation()));
    return person;
  }

  private Name convertToName(String firstName, String middleName, String lastName) {
    Name name = new Name();
    name.setFirstName(firstName);
    name.setMiddleName(middleName);
    name.setLastName(lastName);

    return name;
  }

  private WhatHappened convertToWhatHappened(Narrative narrative) {
    if (Objects.isNull(narrative)) {
      return null;
    }

    WhatHappened whatHappened = new WhatHappened();
    whatHappened.setDescription(narrative.getNarrativeText());
    whatHappened.setWhy(narrative.getReasonWhy());

    return whatHappened;
  }

  private List<ItemDetail> convertToDamagedItemDetails(Content pegaContent) {
    List<Property> pegaDamagedItems = pegaContent.getProperties();
    if (Objects.isNull(pegaDamagedItems)) {
      return null;
    }

    return pegaDamagedItems.stream()
        .map(
            damagedItem -> {
              ItemDetail itemDetail = new ItemDetail();
              itemDetail.setDescription(damagedItem.getDamageDescription());

              Owner owner =
                  convertToOwner(
                      damagedItem.getWhoOwnsIt(),
                      pegaContent,
                      () ->
                          this.getOtherPersonName(
                              damagedItem.getOwner(), damagedItem.getSomebodyOwns()),
                      () ->
                          this.getOrganizationName(
                              damagedItem.getOrganization(),
                              damagedItem.getOrganisationOwn(),
                              damagedItem.getOrganizationItem()));
              itemDetail.setOwner(owner);

              itemDetail.setWhat(convertToItem(damagedItem));

              return itemDetail;
            })
        .collect(Collectors.toList());
  }

  private List<ItemDetail> convertToItemDetails(Content pegaContent) {
    List<Item> pegaItems = pegaContent.getItem();
    if (CollectionUtils.isEmpty(pegaItems)) {
      return Collections.emptyList();
    }

    return pegaItems.stream()
        .map(
            pegaItem -> {
              ItemDetail itemDetail = new ItemDetail();
              itemDetail.setId(pegaItem.getSerialNumber());
              itemDetail.setDescription(pegaItem.getItemDescription());
              itemDetail.setValue(pegaItem.getItemValueDoller());

              Owner owner =
                  convertToOwner(
                      pegaItem.getWhoOwnsIt(),
                      pegaContent,
                      () ->
                          this.getOtherPersonName(pegaItem.getOwner(), pegaItem.getSomebodyOwns()),
                      () ->
                          this.getOrganizationName(
                              pegaItem.getOrganization(),
                              pegaItem.getOrganisationOwn(),
                              pegaItem.getOrganizationItem()));
              itemDetail.setOwner(owner);

              itemDetail.setWhat(convertToItem(pegaItem));

              return itemDetail;
            })
        .collect(Collectors.toList());
  }

  private Owner convertToOwner(
      String whoOwnsIt,
      Content pegaContent,
      Supplier<String> otherPersonName,
      Supplier<String> organizationName) {
    Owner owner = new Owner();
    owner.setType(this.enumMappingProperties.getMappedEnum(TypeEnum.class, whoOwnsIt));

    if (Objects.nonNull(owner.getType())) {
      switch (owner.getType()) {
        case ME:
          owner.setIndex(0);
          break;
        case OTHER_PERSON:
          owner.setIndex(findIndexForOtherPerson(otherPersonName.get(), pegaContent));
          break;
        case ORGANISATION:
          owner.setIndex(findIndexForOrganization(organizationName.get(), pegaContent));
          break;
        default:
          log.error("Failed to find the Owner Index by Owner Type {}", owner.getType());
          break;
      }

      return owner;
    }

    // whoOwnsIt is dynamic, could be a person name or an organisation name
    int index = findIndexForOtherPerson(otherPersonName.get(), pegaContent);
    if (index != OWNER_INDEX_NOT_FOUND && whoOwnsIt.equalsIgnoreCase(otherPersonName.get())) {
      owner.setType(TypeEnum.OTHER_PERSON);
      owner.setIndex(index);
      return owner;
    }

    index = findIndexForOrganization(organizationName.get(), pegaContent);
    if (index != OWNER_INDEX_NOT_FOUND && whoOwnsIt.equalsIgnoreCase(organizationName.get())) {
      owner.setType(TypeEnum.ORGANISATION);
      owner.setIndex(index);
      return owner;
    }

    log.error("Failed to find the Owner Index for {}", whoOwnsIt);
    owner.setIndex(OWNER_INDEX_NOT_FOUND);
    return owner;
  }

  private Integer findIndexForOrganization(String organizationName, Content pegaContent) {
    try {
      List<OrganizationDo> organizationList = pegaContent.getOrganizationDoes();
      return IntStream.range(0, organizationList.size())
          .filter(
              index ->
                  organizationList
                      .get(index)
                      .getOrganization()
                      .getOrganisationName()
                      .equalsIgnoreCase(organizationName))
          .findFirst()
          .orElse(OWNER_INDEX_NOT_FOUND);
    } catch (Exception e) {
      log.error(
          "Error happened when finding the Organization Index by name {}: {}",
          organizationName,
          e.getMessage());
      return OWNER_INDEX_NOT_FOUND;
    }
  }

  private Integer findIndexForOtherPerson(String otherPersonFullName, Content pegaContent) {
    if (Objects.isNull(otherPersonFullName)) {
      return OWNER_INDEX_NOT_FOUND;
    }

    try {
      List<SomebodyElse> somebodyElseList = pegaContent.getSomebodyElse();
      return IntStream.range(0, somebodyElseList.size())
          .filter(
              index -> {
                SomebodyElse somebodyElse = somebodyElseList.get(index);
                String fullName =
                    getPersonName(somebodyElse.getFirstName(), somebodyElse.getSurName());
                return fullName.equalsIgnoreCase(otherPersonFullName);
              })
          .findFirst()
          .orElse(OWNER_INDEX_NOT_FOUND);
    } catch (Exception e) {
      log.error(
          "Error happened when finding the Other Person Index by full name {}: {}",
          otherPersonFullName,
          e.getMessage());
      return OWNER_INDEX_NOT_FOUND;
    }
  }

  private String getOrganizationName(
      Organization organization,
      OrganisationOwn organisationOwn,
      OrganizationItem organizationItem) {
    if (Objects.nonNull(organization) && Objects.nonNull(organization.getOrganisationName())) {
      return organization.getOrganisationName();
    }

    if (Objects.nonNull(organisationOwn)
        && Objects.nonNull(organisationOwn.getOrganization())
        && Objects.nonNull(organisationOwn.getOrganization().getOrganisationName())) {
      return organisationOwn.getOrganization().getOrganisationName();
    }

    if (Objects.nonNull(organizationItem)
        && Objects.nonNull(organizationItem.getOrganisationName())) {
      return organizationItem.getOrganisationName();
    }

    return null;
  }

  private String getOtherPersonName(
      nzp.digital.portal.onlinereportstaticuiservice.model.pega.Owner owner,
      SomebodyOwns somebodyOwns) {
    if (Objects.nonNull(owner) && Objects.nonNull(owner.getFname())) {
      return getPersonName(owner.getFname(), owner.getSname());
    }

    if (Objects.nonNull(somebodyOwns) && Objects.nonNull(somebodyOwns.getFirstName())) {
      return getPersonName(somebodyOwns.getFirstName(), somebodyOwns.getSurName());
    }

    return null;
  }

  private String getPersonName(String firstName, String lastName) {
    Assert.notNull(firstName, "Person First Name must not be null");
    Assert.notNull(lastName, "Person Last Name must not be null");
    return firstName + " " + lastName;
  }

  private nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item convertToItem(
      Property pegaProperty) {
    nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item item =
        new nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item();
    item.setCannotFind(pegaProperty.getNotInLookUp());

    if (item.getCannotFind()) {
      item.setManual(pegaProperty.getPropertyType());
    } else {
      SearchedItem searchedItem = new SearchedItem();
      searchedItem.setCategory(pegaProperty.getCategory());
      searchedItem.setDisplayValue(pegaProperty.getDisplayValue());
      searchedItem.setItemName(pegaProperty.getPropertyType());
      searchedItem.setNiaItemName(pegaProperty.getNIAItemName());

      item.setSearch(searchedItem);
    }
    return item;
  }

  private nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item convertToItem(
      Item pegaItem) {
    nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item item =
        new nzp.digital.portal.onlinereportstaticuiservice.model.gen.Item();
    item.setCannotFind(pegaItem.getNotInLookUp());

    if (item.getCannotFind()) {
      item.setManual(pegaItem.getItemName());
    } else {
      SearchedItem searchedItem = new SearchedItem();
      searchedItem.setCategory(pegaItem.getCategory());
      searchedItem.setDisplayValue(pegaItem.getDisplayValue());
      searchedItem.setItemName(pegaItem.getItemName());
      searchedItem.setNiaItemName(pegaItem.getNIAItemName());

      item.setSearch(searchedItem);
    }
    return item;
  }

  public WhereItHappened convertToWhereItHappened(Content pegaReportContent) {
    LocationType locationType = pegaReportContent.getLocationType();

    WhereItHappened whereItHappened = new WhereItHappened();
    whereItHappened.setLocationType(
        enumMappingProperties.getMappedEnum(
            LocationTypeEnum.class, locationType.getLocationTypeIndicator()));
    whereItHappened.setLocationName(locationType.getLocationDetails().getLocationName());
    whereItHappened.setAdditionalInformation(
        locationType.getLocationDetails().getAdditonalLocationInfo());
    whereItHappened.setHowCloseToAddress(
        enumMappingProperties.getMappedEnum(
            HowCloseToAddressEnum.class, locationType.getLocationDetails().getProximity()));

    whereItHappened.setAddress(
        convertToAddressInfo(
            locationType.getLocationDetails().getNotInLookUp(),
            locationType.getLocationDetails().getFreeFormAddress(),
            locationType.getLocationDetails().getAddress(),
            null));

    whereItHappened.setStolenFromVehicle(
        BooleanUtils.isTrue(isYes(pegaReportContent.getStolenfromavehicleindicator())));
    if (whereItHappened.getStolenFromVehicle()) {
      PropertyVehicle propertyVehicle = pegaReportContent.getProperty().getPropertyVehicle();
      whereItHappened.setLicencePlate(propertyVehicle.getRegistration());
      whereItHappened.setVehicleLocation(
          this.enumMappingProperties.getMappedEnum(
              VehicleLocationEnum.class, propertyVehicle.getVehicleLocation()));
    }

    return whereItHappened;
  }

  private AddressInfo convertToAddressInfo(
      Boolean addressNotInLookUp,
      FreeFormAddress freeFormAddress,
      nzp.digital.portal.onlinereportstaticuiservice.model.pega.Address niaAddress,
      String addressType) {
    Assert.notNull(addressNotInLookUp, "Address Not In Lookup should not be null");

    AddressInfo addressInfo = new AddressInfo();
    addressInfo.setCannotFind(addressNotInLookUp);

    if (Objects.nonNull(addressType)) {
      addressInfo.setType(
          this.enumMappingProperties.getMappedEnum(AddressInfo.TypeEnum.class, addressType));
    }

    if (addressInfo.getCannotFind()) {
      Address address = new Address();

      address.setCountry(freeFormAddress.getCountry());

      address.setUnitType(
          this.enumMappingProperties.getMappedEnum(
              UnitTypeEnum.class, freeFormAddress.getUnitType()));
      address.setUnitNumber(freeFormAddress.getUnitNumber());

      address.setStreetName(freeFormAddress.getStreetName());
      address.setStreetNumber(freeFormAddress.getStreetNumber());
      address.setStreetType(freeFormAddress.getStreetType());

      address.setSuburb(freeFormAddress.getTownSuburb());

      addressInfo.setManual(address);
    } else {
      addressInfo.setSearch(niaAddress.getAddressCSV());
    }

    return addressInfo;
  }

  public WhenItHappened convertToWhenItHappened(DateEntry dateEntry) {
    WhenItHappened whenItHappened = new WhenItHappened();
    if (dateEntry.getFromDateTime() != null) {
      whenItHappened.setStartDateTime(
          OffsetDateTime.of(
              LocalDateTime.parse(
                  dateEntry.getFromDateTime(),
                  DateTimeFormatter.ofPattern(JacksonOffsetDateTimeMapper.DATE_TIME_ISO_FORMAT)),
              ZoneOffset.UTC));
    }
    if (dateEntry.getToDateTime() != null) {
      whenItHappened.setEndDateTime(
          OffsetDateTime.of(
              LocalDateTime.parse(
                  dateEntry.getToDateTime(),
                  DateTimeFormatter.ofPattern(JacksonOffsetDateTimeMapper.DATE_TIME_ISO_FORMAT)),
              ZoneOffset.UTC));
    }

    return whenItHappened;
  }

  public ReportType convertToReportType(EventTypeIndicator eventTypeIndicator) {
    ReportType reportType = new ReportType();
    reportType.setSomethingDamaged(eventTypeIndicator.getBeenDamaged());
    reportType.setSomeoneHurt(eventTypeIndicator.getBeenHurt());
    reportType.setSomethingStolen(eventTypeIndicator.getBeenStolen());
    reportType.setSomethingLost(eventTypeIndicator.getLostProperty());
    reportType.setSomethingElse(eventTypeIndicator.getOther());

    return reportType;
  }
}
