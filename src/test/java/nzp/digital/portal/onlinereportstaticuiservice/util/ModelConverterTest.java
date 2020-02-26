package nzp.digital.portal.onlinereportstaticuiservice.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nzp.digital.portal.onlinereportstaticuiservice.config.EnumMappingProperties;
import nzp.digital.portal.onlinereportstaticuiservice.config.JacksonOffsetDateTimeMapper;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.Address.UnitTypeEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.ReportType;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhenItHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhereItHappened;
import nzp.digital.portal.onlinereportstaticuiservice.model.gen.WhereItHappened.LocationTypeEnum;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Address;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.Content;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.DateEntry;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.EventTypeIndicator;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.FreeFormAddress;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.LocationDetails;
import nzp.digital.portal.onlinereportstaticuiservice.model.pega.LocationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.threeten.bp.format.DateTimeFormatter;

@SpringBootTest
public class ModelConverterTest {

  private ModelConverter modelConverter;

  @Autowired
  public ModelConverterTest(EnumMappingProperties enumMappingProperties) {
    this.modelConverter = new ModelConverter(enumMappingProperties);
  }

  @Test
  public void givenEventTypesWhenConvertToReportTypesShouldBeCorrect() {
    EventTypeIndicator eventTypeIndicator = new EventTypeIndicator();
    eventTypeIndicator.setBeenDamaged(true);
    eventTypeIndicator.setBeenHurt(true);
    eventTypeIndicator.setBeenStolen(true);
    eventTypeIndicator.setLostProperty(false);
    eventTypeIndicator.setOther(false);

    ReportType reportType = this.modelConverter.convertToReportType(eventTypeIndicator);

    assertTrue(reportType.getSomethingDamaged());
    assertTrue(reportType.getSomeoneHurt());
    assertTrue(reportType.getSomethingStolen());
    assertFalse(reportType.getSomethingLost());
    assertFalse(reportType.getSomethingElse());
  }

  @Test
  public void givenStartDateTimeWhenConvertToWhenHappenedShouldBeCorrect() {
    DateEntry dateEntry = new DateEntry();
    dateEntry.setFromDateTime("2020-01-14T00:21:00.000Z");

    WhenItHappened whenItHappened = this.modelConverter.convertToWhenItHappened(dateEntry);

    assertEquals(
        "2020-01-14T00:21:00.000Z",
        whenItHappened
            .getStartDateTime()
            .format(DateTimeFormatter.ofPattern(JacksonOffsetDateTimeMapper.DATE_TIME_ISO_FORMAT)));
    assertNull(whenItHappened.getEndDateTime());
  }

  @Test
  public void givenStartAndEndDateTimeWhenConvertToWhenHappenedShouldBeCorrect() {
    DateEntry dateEntry = new DateEntry();
    dateEntry.setFromDateTime("2020-01-14T00:21:00.000Z");
    dateEntry.setToDateTime("2020-01-14T01:21:00.000Z");

    WhenItHappened whenItHappened = this.modelConverter.convertToWhenItHappened(dateEntry);

    assertEquals(
        "2020-01-14T00:21:00.000Z",
        whenItHappened
            .getStartDateTime()
            .format(DateTimeFormatter.ofPattern(JacksonOffsetDateTimeMapper.DATE_TIME_ISO_FORMAT)));
    assertEquals(
        "2020-01-14T01:21:00.000Z",
        whenItHappened
            .getEndDateTime()
            .format(DateTimeFormatter.ofPattern(JacksonOffsetDateTimeMapper.DATE_TIME_ISO_FORMAT)));
  }

  @Test
  public void givenLocationInLookUpWhenConvertToWhereHappenedShouldBeCorrect() {
    Content pegaContent = new Content();

    LocationType pegaLocationType = new LocationType();
    pegaLocationType.setLocationTypeIndicator("A shop/business");
    LocationDetails locationDetails = new LocationDetails();
    locationDetails.setLocationName("KFC");
    locationDetails.setNotInLookUp(false);

    Address pegaAddress = new Address();
    pegaAddress.setAddressCSV("96 LAKE TERRACE, TAUPO, TAUPO DISTRICT 3330");

    locationDetails.setAddress(pegaAddress);
    pegaLocationType.setLocationDetails(locationDetails);

    pegaContent.setLocationType(pegaLocationType);

    WhereItHappened whereItHappened =
        this.modelConverter.convertToWhereItHappened(pegaContent);

    assertEquals(LocationTypeEnum.SHOP_BUSINESS, whereItHappened.getLocationType());
    assertEquals("KFC", whereItHappened.getLocationName());
    assertFalse(whereItHappened.getAddress().getCannotFind());
    assertEquals(
        "96 LAKE TERRACE, TAUPO, TAUPO DISTRICT 3330", whereItHappened.getAddress().getSearch());
  }

  @Test
  public void givenLocationNotInLookUpWhenConvertToWhereHappenedShouldBeCorrect() {
    Content pegaContent = new Content();
    LocationType pegaLocationType = new LocationType();
    pegaLocationType.setLocationTypeIndicator("A shop/business");
    LocationDetails locationDetails = new LocationDetails();
    locationDetails.setLocationName("KFC");
    locationDetails.setNotInLookUp(true);

    FreeFormAddress pegaFreeFormAddress = new FreeFormAddress();
    pegaFreeFormAddress.setCountry("New Zealand");
    pegaFreeFormAddress.setIsOverSee("New Zealand");
    pegaFreeFormAddress.setStreetDirectionSuffix("West");
    pegaFreeFormAddress.setStreetName("High Street");
    pegaFreeFormAddress.setStreetNumber("999");
    pegaFreeFormAddress.setStreetType("Street");
    pegaFreeFormAddress.setTownSuburb("Avalon, Lower Hutt City");
    pegaFreeFormAddress.setUnitNumber("222");
    pegaFreeFormAddress.setUnitType("Unit");

    locationDetails.setFreeFormAddress(pegaFreeFormAddress);
    pegaLocationType.setLocationDetails(locationDetails);

    pegaContent.setLocationType(pegaLocationType);

    WhereItHappened whereItHappened =
        this.modelConverter.convertToWhereItHappened(pegaContent);

    assertEquals(LocationTypeEnum.SHOP_BUSINESS, whereItHappened.getLocationType());
    assertEquals("KFC", whereItHappened.getLocationName());
    assertTrue(whereItHappened.getAddress().getCannotFind());

    nzp.digital.portal.onlinereportstaticuiservice.model.gen.Address manualAddress =
        whereItHappened.getAddress().getManual();
    assertEquals("New Zealand", manualAddress.getCountry());
    assertEquals("High Street", manualAddress.getStreetName());
    assertEquals("999", manualAddress.getStreetNumber());
    assertEquals("Street", manualAddress.getStreetType());
    assertEquals("Avalon, Lower Hutt City", manualAddress.getSuburb());
    assertEquals("222", manualAddress.getUnitNumber());
    assertEquals(UnitTypeEnum.UNIT, manualAddress.getUnitType());
  }
}
