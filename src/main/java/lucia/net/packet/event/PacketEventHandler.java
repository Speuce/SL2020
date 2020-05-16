package main.java.lucia.net.packet.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tag used for any methods which contain ONE parameter, of a specific packet type.
 * @author Matthew Kwiatkowski
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PacketEventHandler {

    ListenerPriority priority() default ListenerPriority.NORMAL;

}
