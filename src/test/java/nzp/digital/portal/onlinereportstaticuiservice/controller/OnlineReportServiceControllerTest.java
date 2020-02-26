package nzp.digital.portal.onlinereportstaticuiservice.controller;

import nzp.digital.portal.onlinereportstaticuiservice.model.gen.OnlineReportCaseRequest;
import nzp.digital.portal.onlinereportstaticuiservice.services.ManageCaseService;
import nzp.digital.portal.onlinereportstaticuiservice.util.ModelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OnlineReportServiceController.class)
public class OnlineReportServiceControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean ManageCaseService manageCaseService;

  @Test
  public void givenCaseIdWhenGetCaseThenReturnCase() throws Exception {
    OnlineReportCaseRequest expectedCase = new OnlineReportCaseRequest();
    when(this.manageCaseService.getCase("POLICE-SDW-WORK OR-7876"))
        .thenReturn(expectedCase);

    this.mockMvc
        .perform(get("/api/onlinereport/POLICE-SDW-WORK OR-7876"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo(ModelMapper.javaToJson(expectedCase))));
  }

  @Test
  public void givenNonExistedCaseIdWhenGetCaseThenReturn404() throws Exception {

    when(this.manageCaseService.getCase("POLICE-SDW-WORK OR-7876"))
        .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

    this.mockMvc
        .perform(get("/api/onlinereport/POLICE-SDW-WORK OR-7876"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().string(emptyString()));
  }

  @Test
  public void givenServerErrorWhenGetCaseThenReturn500() throws Exception {

    when(this.manageCaseService.getCase("POLICE-SDW-WORK OR-7876"))
        .thenThrow(new RestClientException("Rest Client Exception"));

    this.mockMvc
        .perform(get("/api/onlinereport/POLICE-SDW-WORK OR-7876"))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(content().string(emptyString()));
  }

  @Test
  public void givenPegaServiceUnavailableWhenGetCaseThenReturn503() throws Exception {

    when(this.manageCaseService.getCase("POLICE-SDW-WORK OR-7876"))
        .thenThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE));

    this.mockMvc
        .perform(get("/api/onlinereport/POLICE-SDW-WORK OR-7876"))
        .andDo(print())
        .andExpect(status().isServiceUnavailable())
        .andExpect(content().string(emptyString()));
  }
}
