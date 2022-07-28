package com.security.security20220721.annotations;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface  AnonymousAccess {
}
