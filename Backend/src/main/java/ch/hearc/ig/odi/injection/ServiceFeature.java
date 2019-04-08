/*
 * 2018. Cours outils de développement intégré. ulysse.rosselet@he-arc.ch
 */

package ch.hearc.ig.odi.injection;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class ServiceFeature implements Feature {

  @Override
  public boolean configure(final FeatureContext context) {
    context.register(new ServiceBinder());
    return true;
  }
}