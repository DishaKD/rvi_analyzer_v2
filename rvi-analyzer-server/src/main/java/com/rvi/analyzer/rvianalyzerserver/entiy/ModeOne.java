package com.rvi.analyzer.rvianalyzerserver.entiy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
 // import org.springframework.data.mongodb.core.mapping.Document;
 // import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

 // @Document
@Builder
@Getter
@Setter
public class ModeOne {

    private String _id;
    // @Field(name = "created-by")
    private String createdBy;
    // @Field(name = "default-configurations")
    private DefaultConfiguration defaultConfigurations;
    // @Field(name = "session-configurations")
    private SessionConfigurationModeOne sessionConfigurationModeOne;
    private List<SessionResult> results;
    private String status;
    // @Field(name = "created-date")
    @CreatedDate
    private LocalDateTime createdDateTime;
    // @Field(name = "last-updated-date")
    @LastModifiedDate
    private LocalDateTime lastUpdatedDateTime;
}
