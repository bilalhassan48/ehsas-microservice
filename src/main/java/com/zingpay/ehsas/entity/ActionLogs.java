package com.zingpay.ehsas.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Bilal Hassan on 21-Nov-21
 * @project ehsas-integration-microservice
 */

@Entity
@Getter
@Setter
@Table(name = "action_logs")
public class ActionLogs {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "uri")
    private String uri;

    @Column(name = "method")
    private String method;

    @Column(name = "status")
    private String status;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "status_description")
    private String statusDescription;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "request_object")
    private String requestObject;

    @Column(name = "response_object")
    private String responseObject;

    @Column(name = "third_party_request_object")
    private String thirdPartyRequestObject;

    @Column(name = "third_party_response_object")
    private String thirdPartyResponseObject;
}
