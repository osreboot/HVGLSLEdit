package com.osreboot.glsledit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface NodeConstructorArgs {
	NodeConstructorArg[] value();
}
