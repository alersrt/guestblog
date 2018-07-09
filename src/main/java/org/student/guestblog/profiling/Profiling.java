package org.student.guestblog.profiling;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Class level annotation which determines that profiling needed for annotated class. It calls
 * profiler for all methods which are included into this class.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Profiling {

}
