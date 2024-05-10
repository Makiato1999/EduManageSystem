package com.easybytes.easyschool.validations;

import com.easybytes.easyschool.annotation.FieldsValueMatch;
import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class FieldsValueMatchValidator
        implements ConstraintValidator<FieldsValueMatch, Object> {
    // 这个class实现了Jakarta Bean Validation API的ConstraintValidator接口。
    // 这个验证器是为了支持自定义注解FieldsValueMatch的功能，确保指定的两个字段值是相等的。
    // 这通常用于校验表单中的两个字段，如密码和确认密码。

    private String field; // 这是一个私有成员变量，用于存储需要验证的字段名。
    private String fieldMatch; // 这是第二个私有成员变量，用于存储需要与field值比较的字段名。

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    // 在方法调用之前，如果控制器方法参数使用了@Valid或@Validated注解，Spring将触发验证过程。
    // 这时，Spring Validation机制会查找并应用所有相关的约束验证器，包括ConstraintValidator的实现。
    @Override
    public boolean isValid(Object value // value参数代表了附加了@FieldsValueMatch注解的对象实例
            ,ConstraintValidatorContext context
    ) {
        // 通过将fieldValue和fieldMatchValue声明为Object类型，这个验证器不限于比较字符串类型的字段。
        // 这意味着，无论字段的数据类型是什么（字符串、数字、日期等），只要数据类型支持equals方法，这个验证器都能够使用。
        // 这种方法提供了更高的灵活性，使得FieldsValueMatchValidator能够在更广泛的场景中应用。

        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        // 使用BeanWrapperImpl从要验证的对象中获取field指定的属性值。

        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
        // 同样使用BeanWrapperImpl获取fieldMatch指定的属性值。

        /*
        if (fieldValue != null) {
            if (fieldValue.toString().startsWith("$2a")) {
                return true;
            } else {
                return fieldValue.equals(fieldMatchValue);
            }
        } else {
            return fieldMatchValue == null;
        }

         */
        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }
}
