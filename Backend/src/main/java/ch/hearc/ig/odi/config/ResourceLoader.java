/*
 * 2018. Cours outils de développement intégré. ulysse.rosselet@he-arc.ch
 */

package ch.hearc.ig.odi.config;

import ch.hearc.ig.odi.filter.AuthenticationFilter;
import ch.hearc.ig.odi.filter.CORSFilter;
import ch.hearc.ig.odi.injection.ServiceBinder;
import ch.hearc.ig.odi.injection.ServiceFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ResourceLoader extends ResourceConfig {

  public ResourceLoader() {
    register(ServiceFeature.class);
    register(CORSFilter.class);
    register(AuthenticationFilter.class);
    registerInstances(new ServiceBinder());

  }

}

