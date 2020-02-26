package nzp.digital.portal.onlinereportstaticuiservice.config;

import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

@Log4j2
@Configuration
@PropertySource("classpath:enum-mapping.properties")
public class EnumMappingProperties {
  private Environment env;

  @Autowired
  public EnumMappingProperties(Environment env) {
    Assert.notNull(env, "Environment injection must not be null");
    this.env = env;
  }

  private String getMappedEnumValue(String key) {
    return env.getProperty("pega.enum." + key);
  }

  public <T extends Enum<T>> T getMappedEnum(Class<T> enumType, String pegaEnumValue) {
    T e = null;
    try {
      Assert.notNull(pegaEnumValue, "Pega Enum Value should not be null");
      String mappedEnumValue = this.getMappedEnumValue(pegaEnumValue);
      e = Enum.valueOf(enumType, Objects.isNull(mappedEnumValue) ? pegaEnumValue.toUpperCase() : mappedEnumValue);
    } catch (Exception exception) {
      log.error(
          "Failed to get mapped enum for {} from value {}, error: {}",
          enumType.getName(),
          pegaEnumValue,
          exception.getMessage());
    }
    return e;
  }
}
