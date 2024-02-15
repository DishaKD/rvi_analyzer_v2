package com.rvi.analyzer.rvianalyzerserver.entiy;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Style")
public class Style {
    @Id
    private Long _id;
    @Column
    private String name;
    @Column
    private String plant;
    @Column
    private List<String> admin;
    @Column
    private String customer;
    @Column
    private String createdBy;
    @Column
    @CreatedDate
    private LocalDateTime createdDateTime;
}
