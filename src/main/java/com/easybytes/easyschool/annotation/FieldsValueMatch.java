package com.easybytes.easyschool.annotation;

import com.easybytes.easyschool.validations.FieldsValueMatchValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldsValueMatchValidator.class)
// 这是一个必须的注解，它告诉Spring框架这个注解是一个验证约束，而且指出FieldsValueMatchValidator类将作为这个注解的验证器。
// 这意味着当你在你的类上使用了@FieldsValueMatch注解时，Spring验证框架将会调用FieldsValueMatchValidator类来执行实际的验证逻辑。
@Target({ ElementType.TYPE })
// 定义了注解FieldsValueMatch可以应用在哪些Java元素上。ElementType.TYPE意味着它可以应用在类、接口（包括注解类型）或枚举的声明上。
@Retention(RetentionPolicy.RUNTIME)
// 指定了FieldsValueMatch注解保留到哪个阶段。RetentionPolicy.RUNTIME意味着注解不仅被保留在编译后的类文件中，
// 而且在运行时通过反射可见，这使得运行时的框架能够读取这个注解。
public @interface FieldsValueMatch {

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String message() default "Fields values don't match!";

    String field();

    String fieldMatch();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsValueMatch[] value();
    }
}