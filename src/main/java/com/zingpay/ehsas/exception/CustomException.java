package com.zingpay.ehsas.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zingpay.ehsas.util.Status;
import feign.RetryableException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

@RestControllerAdvice
public class CustomException {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Status> noSuchElementFound(final Exception e) {
        return error(e, HttpStatus.NOT_FOUND, e.getMessage());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Status> httpMessageNotReadableException(final Exception e) {
        return error(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<Status> handleFeignRetryableException(final RetryableException e) {
        return error(e, HttpStatus.FAILED_DEPENDENCY, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Status> constraintViolation(final Exception e) {
        return error(e, HttpStatus.EXPECTATION_FAILED, e.getMessage());
    }

    private ResponseEntity<Status> error(final Exception exception, HttpStatus httpStatus, final String logRef) {
        String message = "";
        if (exception instanceof AccessDeniedException) {
            message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        } else if (exception instanceof NoSuchElementException) {
            message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        } else if (exception instanceof DataIntegrityViolationException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            Throwable t1 = exception.getCause();
            if (t1 instanceof ConstraintViolationException) {
                ConstraintViolationException cve = (ConstraintViolationException) t1;
                Throwable t2 = cve.getCause();
                if (t2 instanceof SQLException) {
                    SQLException sic = (SQLException) t2;
                    message = createMessageForConstraintViolation(sic);
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            }
        } else if (exception instanceof RetryableException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            try {
                Status status = new ObjectMapper().readValue(exception.getMessage(), Status.class);
                return new ResponseEntity<Status>(status, HttpStatus.OK);
            } catch (JsonProcessingException e) {
                System.out.println("error= unable to parse response from Feign Exception");
            }
        }
        if (message.isEmpty()) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            if(exception.getMessage() == null || exception.getMessage().equals("") || exception.getMessage().isEmpty()) {
                message = "Something went wrong";
            } else {
                message = exception.getMessage();
            }
        }
        Status status = new Status(httpStatus.value(), message, 1L);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    private String createMessageForConstraintViolation(SQLException sic) {
        String message = "Database constraint error";
        String errorMessage = sic.getMessage();
        String actualConstraintViolated = "";
        if (errorMessage.contains("duplicate")) {
            actualConstraintViolated = errorMessage.split("=")[0].split("Key ")[1].split("\\(")[1].split(",")[0];

            /*if (errorMessage.contains("app_group_name_organization_id_uindex")) {
                message = "Group already exists";
            } else if (errorMessage.contains("organization_name_uindex")) {
                message = "Organization already exists";
                //} else if (errorMessage.contains("app_user_organization_id_email_uindex")) {
            } else if (errorMessage.contains("organization_official_email_uindex")) {
                message = "Email already exists";
            } else if (errorMessage.contains("organization_filter_organization_id_name_uindex")) {
                message = "Name already exists";
            } else if (errorMessage.contains("collector_service_pk")){
                message = "Service name already exists";
            } else {
                message = actualConstraintViolated + " already exists";
            }*/


        } /*else {
            String[] allMsgs = errorMessage.split("REFERENCES");
            actualConstraintViolated = allMsgs[1].split("`")[1];
            //message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
            message = "Could not save data due to data constraints voilation with " + actualConstraintViolated;
        }*/
        return message;
    }
}
