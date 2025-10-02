package com.demo.loans.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


@ConfigurationProperties(prefix = "loans")
@Setter @Getter
public class loansContactInfoDto{

    private String message;
    private Map<String,String> contactDetails;
    private List<String> onCallSupport;

}
