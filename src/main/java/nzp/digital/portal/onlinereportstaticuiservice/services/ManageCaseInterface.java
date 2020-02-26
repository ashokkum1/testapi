package nzp.digital.portal.onlinereportstaticuiservice.services;

import nzp.digital.portal.onlinereportstaticuiservice.model.gen.OnlineReportCaseRequest;

public interface ManageCaseInterface {
  OnlineReportCaseRequest getCase(String caseId);
}
