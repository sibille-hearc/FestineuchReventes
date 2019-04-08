package ch.hearc.ig.odi.filter;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;

@NameBinding //faire un lien entre l'annotation et l'invoquation d'une autre classe
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured { }
