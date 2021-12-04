package com.zingpay.ehsas.config;

import com.zingpay.ehsas.entity.ActionLogs;
import com.zingpay.ehsas.repository.ActionLogRepository;
import com.zingpay.ehsas.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Bilal Hassan on 23-Nov-21
 * @project ehsas-integration-microservice
 */

@Slf4j
@Aspect
@Configuration
public class LoggingAspect {

    @Autowired
    private ActionLogRepository actionLogRepository;

    @Autowired(required = false)
    private HttpServletRequest request;

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.zingpay.ehsas.controller..*)" +
            " || within(com.zingpay.ehsas.entity..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getResponse();

            Object result = joinPoint.proceed();

            ActionLogs actionLogs = new ActionLogs();
            actionLogs.setCreatedOn(new Date());
            actionLogs.setResponseObject(Utils.parseObjectToJson(result));
            actionLogs.setMethod(request.getMethod());
            actionLogs.setUri(request.getRequestURI());
            actionLogs.setStatusCode(response.getStatus()+"");
            actionLogs.setStatusDescription("");
            actionLogs.setStatus(HttpStatus.valueOf(response.getStatus()).getReasonPhrase());

            if(joinPoint.getArgs().length>0) {
                actionLogs.setRequestObject(Utils.parseObjectToJson(joinPoint.getArgs()));
            }

            actionLogRepository.save(actionLogs);

            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}
