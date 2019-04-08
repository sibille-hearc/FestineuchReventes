/*
 * 2018. Cours outils de développement intégré. ulysse.rosselet@he-arc.ch
 */

package ch.hearc.ig.odi.injection;

import ch.hearc.ig.odi.persistance.Conn;
import ch.hearc.ig.odi.util.KeyGenerator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ServiceBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(new Conn()).to(Conn.class);
    bind(new KeyGenerator()).to(KeyGenerator.class);
  }
}


