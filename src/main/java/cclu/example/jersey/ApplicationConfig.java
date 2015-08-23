
package cclu.example.jersey;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.internal.ValidationBinder;

import javax.inject.Inject;

public class ApplicationConfig extends ResourceConfig {

  @Inject
  public ApplicationConfig(ServiceLocator serviceLocator) {
    packages("cclu.example.jersey.resource");
	register(MultiPartFeature.class);
    //property(ServerProperties.BV_FEATURE_DISABLE, true);
    //register(new ValidationBinder());
    
    // Google Guice integration
    //GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
    //GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
    //guiceBridge.bridgeGuiceInjector(Guice.createInjector(Stage.PRODUCTION, new AuthzModule(), new DbModule()));
  }
}
