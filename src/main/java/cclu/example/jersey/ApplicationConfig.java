
package cclu.example.jersey;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.internal.ValidationBinder;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

import cclu.example.jersey.resource.CorsFilter;

import javax.inject.Inject;

public class ApplicationConfig extends ResourceConfig {

  @Inject
  public ApplicationConfig(ServiceLocator serviceLocator) {
    packages("cclu.example.jersey.resource");
	register(MultiPartFeature.class);
    //property(ServerProperties.BV_FEATURE_DISABLE, true);
    //register(new ValidationBinder());
    
    // Google Guice integration
    GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
    GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
    Injector inject = Guice.createInjector(Stage.PRODUCTION, new AppModule(), new DbModule());
    guiceBridge.bridgeGuiceInjector(inject);
    
    Configuration conf = inject.getInstance(Configuration.class);
    if (conf.getBoolean("cors.enable", false)) register(CorsFilter.class);
  }
}
