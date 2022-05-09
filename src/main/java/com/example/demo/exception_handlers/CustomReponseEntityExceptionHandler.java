package com.example.demo.exception_handlers;

import java.util.Date;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.example.demo.entity.ExceptionMessage;

@RestController
@ControllerAdvice
public class CustomReponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({
//		HttpRequestMethodNotSupportedException.class,
//		HttpMediaTypeNotSupportedException.class,
//		HttpMediaTypeNotAcceptableException.class,
//		MissingPathVariableException.class,
//		MissingServletRequestParameterException.class,
//		ServletRequestBindingException.class,
//		ConversionNotSupportedException.class,
//		TypeMismatchException.class,
//		HttpMessageNotReadableException.class,
//		HttpMessageNotWritableException.class,
//		MethodArgumentNotValidException.class,
//		MissingServletRequestPartException.class,
//		BindException.class,
//		NoHandlerFoundException.class,
//		AsyncRequestTimeoutException.class
			ArithmeticException.class })
	@Nullable
	public ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request) throws Exception {
		HttpHeaders headers = new HttpHeaders();

		System.out.println("In Custom Exp Handler >>>");

		if (ex instanceof HttpRequestMethodNotSupportedException) {
			HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
			return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex, headers, status,
					request);
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
			return handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException) ex, headers, status, request);
		} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
			return handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException) ex, headers, status, request);
		} else if (ex instanceof MissingPathVariableException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleMissingPathVariable((MissingPathVariableException) ex, headers, status, request);
		} else if (ex instanceof MissingServletRequestParameterException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, headers, status,
					request);
		} else if (ex instanceof ServletRequestBindingException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleServletRequestBindingException((ServletRequestBindingException) ex, headers, status, request);
		} else if (ex instanceof ConversionNotSupportedException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleConversionNotSupported((ConversionNotSupportedException) ex, headers, status, request);
		} else if (ex instanceof TypeMismatchException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleTypeMismatch((TypeMismatchException) ex, headers, status, request);
		} else if (ex instanceof HttpMessageNotReadableException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleHttpMessageNotReadable((HttpMessageNotReadableException) ex, headers, status, request);
		} else if (ex instanceof HttpMessageNotWritableException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleHttpMessageNotWritable((HttpMessageNotWritableException) ex, headers, status, request);
		} else if (ex instanceof MethodArgumentNotValidException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMethodArgumentNotValid((MethodArgumentNotValidException) ex, headers, status, request);
		} else if (ex instanceof MissingServletRequestPartException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleMissingServletRequestPart((MissingServletRequestPartException) ex, headers, status, request);
		} else if (ex instanceof BindException) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			return handleBindException((BindException) ex, headers, status, request);
		} else if (ex instanceof NoHandlerFoundException) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			return handleNoHandlerFoundException((NoHandlerFoundException) ex, headers, status, request);
		} else if (ex instanceof AsyncRequestTimeoutException) {
			HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
			return handleAsyncRequestTimeoutException((AsyncRequestTimeoutException) ex, headers, status, request);
		} else if (ex instanceof ArithmeticException) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			return handleExceptionInternal((ArithmeticException) ex, null, headers, status, request);
		}

		else {
			// Unknown exception, typically a wrapper with a common MVC exception as cause
			// (since @ExceptionHandler type declarations also match first-level causes):
			// We only deal with top-level MVC exceptions here, so let's rethrow the given
			// exception for further processing through the HandlerExceptionResolver chain.
			throw ex;
		}
	}

	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

//		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
//			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
//		}
		ExceptionMessage exceptonMessage = new ExceptionMessage("Arithmatic Exception Occured.Please check input values enterd by user", new Date());
		body = exceptonMessage;
		headers.set("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<>(body, headers, status);
	}

}
