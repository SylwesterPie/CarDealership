package pl.zajavka.infrastructure.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "pl.zajavka")
@Import(PersistenceJPAConfiguration.class)
public class ApplicationConfiguration {


}
