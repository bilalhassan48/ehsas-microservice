package com.zingpay.ehsas.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zingpay.ehsas.feign.AuthServiceClient;
import com.zingpay.ehsas.util.Status;
import com.zingpay.ehsas.util.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

public class BaseController {

    //protected static final Logger logger = LoggerFactory.getLogger(CustomException.class);

    @Autowired
    protected ObjectMapper json;

    @Autowired
    protected ApplicationContext context;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private AuthServiceClient authServiceClient;

    public ObjectMapper getJson() {
        return json;
    }

    public void setJson(ObjectMapper json) {
        this.json = json;
    }

    public <T extends Object> T convertToObject(String jsonString, Class<T> c) throws Exception {
        return json.readValue(jsonString, c);
    }

    public <T> T convertToHashMap(String jsonString, TypeReference typeRef) throws Exception {
        return (T) json.readValue(jsonString, typeRef);
    }

    public Object convertToObject(String jsonString) throws Exception {
        return json.readValue(jsonString, Object.class);
    }

    /*public String response(Object obj) throws JsonProcessingException {
        try {

            if(obj instanceof StatusMessage){
                StatusMessage sm = (StatusMessage) obj;
                Status status = new Status(sm);
                return json.writeValueAsString(status);
            }
            return json.writeValueAsString(obj);
        } catch (JsonProcessingException e) {

            throw e;
        }
    }

    public String response(StatusMessage statusMessage, long id) throws JsonProcessingException {
        try {

            Status status = new Status(statusMessage,id);
            return json.writeValueAsString(status);
        } catch (JsonProcessingException e) {
            throw e;
        }
    }

    public String response(StatusMessage statusMessage, long id, int responseCode) throws JsonProcessingException {
        try {


            Status status = new Status(statusMessage,id);
            return json.writeValueAsString(status);
        } catch (JsonProcessingException e) {
            throw e;
        }
    }*/
    /*public String response(Object obj) {
        try {

            if(obj instanceof StatusMessage){
                StatusMessage sm = (StatusMessage) obj;
                Status status = new Status(sm);
                return json.writeValueAsString(status);
            }
            return status;
        } catch (JsonProcessingException e) {

            throw e;
        }
    }*/

    public <T> T response(T obj) {
        if(obj instanceof StatusMessage){
            StatusMessage statusMessage = (StatusMessage)obj;
            //res.setStatus(statusMessage.getStatusCode().value());
        }
        //do changes in object or response if you want
        return obj;
    }

    public Status response(StatusMessage statusMessage, long id) {
        Status status = new Status(statusMessage, id);
        return status;
    }

    public Status response(StatusMessage statusMessage) {
        Status status = new Status(statusMessage);
        return status;
    }

    public Status response(String message) {
        Status status = new Status(message);
        return status;
    }

    public Status response(StatusMessage statusMessage, long id, int responseCode) {
        Status status = new Status(statusMessage, id);
        return status;
    }

    public String getToken(){
        var authorizationRequestParam = Optional.ofNullable(httpServletRequest.getHeader("Authorization"));
        if(authorizationRequestParam.isPresent()){
            return authorizationRequestParam.get();
        }
        return "";
    }

    /*protected PaginationResponse initializePaginationEntity(Predicate predicate, int page, int size, String sortType, String[] sortProperties) {
        return new PaginationResponse(predicate,page,size,sortProperties,sortType);
    }*/

    public String getLoggedInUserEmail() {
        return authServiceClient.fetchEmail(getToken());
    }

    public int getLoggedInUserAccountId() {
        return authServiceClient.fetchAccountId(getToken());
    }
}
