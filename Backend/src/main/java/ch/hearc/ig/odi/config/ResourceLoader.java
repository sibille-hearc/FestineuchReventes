/*
 * 2018. Cours outils de développement intégré. ulysse.rosselet@he-arc.ch
 */

package ch.hearc.ig.odi.config;

import ch.hearc.ig.odi.filter.AuthenticationFilter;
import ch.hearc.ig.odi.filter.CORSFilter;
import ch.hearc.ig.odi.injection.ServiceBinder;
import ch.hearc.ig.odi.injection.ServiceFeature;
import ch.hearc.ig.odi.restresources.AuthenticationEndpoint;
import ch.hearc.ig.odi.restresources.OffreResources;
import ch.hearc.ig.odi.restresources.ProfilResources;
import org.glassfish.jersey.server.ResourceConfig;

public class ResourceLoader extends ResourceConfig {

  public ResourceLoader() {
    register(ServiceFeature.class);
    register(ProfilResources.class);
    register(CORSFilter.class);
    register(AuthenticationEndpoint.class);
    register(AuthenticationFilter.class);
    register(OffreResources.class);
    registerInstances(new ServiceBinder());

  }

}

