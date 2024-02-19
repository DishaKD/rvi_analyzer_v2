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
public class Style {

    private String _id;
    private String name;
    private String plant;
    //    private String admin;
    private List<String> admin;
    private String customer;

    // @Field(name = "created-by")
    private String createdBy;
    // @Field(name = "created-date")
    @CreatedDate
    private LocalDateTime createdDateTime;

}
