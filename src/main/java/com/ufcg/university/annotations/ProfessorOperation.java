package com.ufcg.university.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProfessorOperation {
    String description() default "description for professor operations";
}
