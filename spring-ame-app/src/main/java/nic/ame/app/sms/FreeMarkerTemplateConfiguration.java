package nic.ame.app.sms;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class FreeMarkerTemplateConfiguration {

    private Configuration configuration;

    private final ResourceLoader resourceLoader;

    @Autowired
    public FreeMarkerTemplateConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.configuration = createConfiguration();
    }

    private Configuration createConfiguration() {
        Configuration config = new Configuration(Configuration.VERSION_2_3_32);
        config.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_32));
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(FreeMarkerTemplateConfiguration.class, "/templates/sms-templates");
        return config;
    }

    public Configuration getInstance() {
        return configuration;
    }

    private File getResourceDir() throws IOException {
        return resourceLoader.getResource("classpath:").getFile();
    }

    // Other methods to use the configuration can be added here
}
