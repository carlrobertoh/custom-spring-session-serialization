package ee.carlrobert.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.security.jackson2.SecurityJackson2Modules;

@Configuration
public class SpringSessionConfig {

  private static final Logger LOG = LoggerFactory.getLogger(SpringSessionConfig.class);

  @Bean
  public ConversionService springSessionConversionService() {
    var objectMapper = new ObjectMapper()
        .registerModules(SecurityJackson2Modules.getModules(getClass().getClassLoader()));
    var conversionService = new GenericConversionService();
    var defaultDeserializer = new DeserializingConverter();

    conversionService.addConverter(Object.class, byte[].class, source -> {
      try {
        return objectMapper.writeValueAsBytes(source);
      } catch (IOException e) {
        throw new RuntimeException("Unable to serialize Spring Session.", e);
      }
    });
    conversionService.addConverter(byte[].class, Object.class, source -> {
      try {
        return objectMapper.readValue(source, Object.class);
      } catch (IOException e) {
        LOG.warn("Falling back to default session deserialization.");
        return defaultDeserializer.convert(source);
      }
    });

    return conversionService;
  }
}
