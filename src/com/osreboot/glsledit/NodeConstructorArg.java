package com.osreboot.glsledit;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(NodeConstructorArgs.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NodeConstructorArg {
	public String value();
}
