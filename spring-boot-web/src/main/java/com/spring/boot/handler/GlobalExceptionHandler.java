package com.spring.boot.handler;

import com.spring.boot.dto.BaseResp;
import com.spring.boot.enums.ResponseCodeEnum;
import com.spring.boot.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

/**
 * @Author Jason
 * @Date 2023-04-25
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public BaseResp<String> businessException(BusinessException e) {
		log.error("business exception", e);
		return BaseResp.fail(e.getResponseCode(), e.getMessage());
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
	@ResponseBody
	public BaseResp<String> parameterException(Exception e) {
		log.error("parameter exception", e);
		return BaseResp.fail(ResponseCodeEnum.BAD_REQUEST, getBadParemeterMsg(e));
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	@ResponseBody
	public BaseResp<String> messageNotReadableException(HttpMessageNotReadableException e) {
		log.error("message not readable exception", e);
		return BaseResp.fail(ResponseCodeEnum.BAD_REQUEST, e.getMessage());
	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public BaseResp<String> methodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("method not supported exception", e);
		return BaseResp.fail(ResponseCodeEnum.HTTP_BAD_METHOD, e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public BaseResp<String> exception(Exception e) {
		log.error("global exception", e);
		return BaseResp.fail(ResponseCodeEnum.INTERNAL_SERVER_ERROR);
	}

	private String getBadParemeterMsg(Exception ex) {
		StringJoiner addOnMessage = new StringJoiner(" | ");
		if (ex instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException)ex).getConstraintViolations();
			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
				String field = constraintViolation.getPropertyPath().toString();
				String message = constraintViolation.getMessage();
				String errorMsg = String.format("%s:%s", field, message);
				addOnMessage.add(errorMsg);
			}
		} else {
			BindingResult bindingResult = null;
			if (ex instanceof MethodArgumentNotValidException) {
				bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
			} else if (ex instanceof BindException) {
				bindingResult = ((BindException) ex).getBindingResult();
			} else {
			}

			List<ObjectError> errors = bindingResult.getAllErrors();
			if (errors != null) {
				for (ObjectError error : errors) {
					if (error instanceof FieldError) {
						FieldError fieldError = (FieldError) error;
						String field = fieldError.getField();
						String message = fieldError.getDefaultMessage();
						String errorMsg = String.format("%s:%s", field, message);
						addOnMessage.add(errorMsg);
					}
				}
			}

		}
		return addOnMessage.toString();
	}

}

