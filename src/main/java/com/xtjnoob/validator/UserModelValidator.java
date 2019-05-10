package com.xtjnoob.validator;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class UserModelValidator implements InitializingBean {

    private Validator validator;

    // 实现校验方法并返回校验结果
    public ValidatorResult validate(Object bean) {
        final ValidatorResult validatorResult = new ValidatorResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0) {
            // 有错误
            validatorResult.setHasErrs(true);
            constraintViolationSet.forEach(constraintViolation -> {
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                validatorResult.getErrMsgMap().put(propertyName, errMsg);
            });
        }
        return validatorResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // 通过hibernate validator通过工厂的初始化方式使其实例化
        this.validator = Validation.byProvider(HibernateValidator.class).configure().addProperty( "hibernate.validator.fail_fast", "false" )
                .buildValidatorFactory().getValidator();
    }
}
