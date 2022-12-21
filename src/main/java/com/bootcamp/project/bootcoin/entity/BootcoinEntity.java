package com.bootcamp.project.bootcoin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Bootcoin")
public class BootcoinEntity {
    @Id
    private String id;
    private String documentNumber;
    private String documentType;
    private String name;
    private String lastname;
    private String mobileNumber;
    private String email;
    private String accountNumber;
    private boolean hasYanki;
    private double balance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date modifyDate;
}
