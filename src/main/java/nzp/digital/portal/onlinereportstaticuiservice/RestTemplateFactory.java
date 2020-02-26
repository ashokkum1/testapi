package nzp.digital.portal.onlinereportstaticuiservice;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {

  @Value("${pega.dev.userid}")
  private String pegaUserId;

  @Value("${pega.dev.password}")
  private String pegaUserPassword;

  private RestTemplate restTemplate;

  public RestTemplate getObject() {
    return restTemplate;
  }

  public Class<RestTemplate> getObjectType() {
    return RestTemplate.class;
  }

  public boolean isSingleton() {
    return true;
  }

  public void afterPropertiesSet() {
    restTemplate = new RestTemplate();
    restTemplate
        .getInterceptors()
        .add(new BasicAuthenticationInterceptor(pegaUserId, pegaUserPassword));
  }
}
