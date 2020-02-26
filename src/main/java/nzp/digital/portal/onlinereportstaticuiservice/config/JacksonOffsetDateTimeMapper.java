package nzp.digital.portal.onlinereportstaticuiservice.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

@Configuration
public class JacksonOffsetDateTimeMapper {
  public static final String DATE_TIME_ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  @Primary
  @Bean
  public ObjectMapper objectMapper() {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(
        OffsetDateTime.class,
        new JsonSerializer<OffsetDateTime>() {
          @Override
          public void serialize(
              OffsetDateTime offsetDateTime,
              JsonGenerator jsonGenerator,
              SerializerProvider serializerProvider)
              throws IOException {
            jsonGenerator.writeString(
                DateTimeFormatter.ofPattern(DATE_TIME_ISO_FORMAT).format(offsetDateTime));
          }
        });
    objectMapper.registerModule(simpleModule);

    return objectMapper;
  }
}
