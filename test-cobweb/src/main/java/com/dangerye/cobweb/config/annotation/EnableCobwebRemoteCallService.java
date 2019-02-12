package com.dangerye.cobweb.config.annotation;

import com.dangerye.cobweb.config.CobwebRemoteCallServiceConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CobwebRemoteCallServiceConfiguration.class)
public @interface EnableCobwebRemoteCallService {
}
