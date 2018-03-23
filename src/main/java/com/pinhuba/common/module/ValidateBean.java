package com.pinhuba.common.module;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidateBean {
	private Boolean hasError;
	private Boolean isShowAllError;
	private String error;
	private ResultBean resultBean;

	/**
	 * @param object
	 * @param isShowAllMessage 是否显示所有提示
	 */
	public ValidateBean(Object object, Boolean isShowAllError) {
		super();
		this.isShowAllError = isShowAllError;
		validateHandler(object);
	}

	public ValidateBean(Object object) {
		super();
		this.isShowAllError = false;
		validateHandler(object);
	}

	private void validateHandler(Object object) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
		String error = "";
		if (constraintViolations.size() > 0) {
			setHasError(true);
			if(isShowAllError){
				for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
					error += constraintViolation.getMessage() + "<br/>";
				}
			}else{
				error = constraintViolations.iterator().next().getMessage();
			}
			setError(error);
			setResultBean(new ResultBean(false, error));
		}else{
			setHasError(false);
		}
	}

	public Boolean hasError() {
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}

	public Boolean getIsShowAllError() {
		return isShowAllError;
	}

	public void isShowAllError(Boolean isShowAllError) {
		this.isShowAllError = isShowAllError;
	}

	public ResultBean getResultBean() {
		return resultBean;
	}

	public void setResultBean(ResultBean resultBean) {
		this.resultBean = resultBean;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
