package com.example.teamlion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    private long id =-1;
    private String symbol;
    private String open;
    private String high;
    private String low;
    private String close;
    @Field("adj close")
    private String AdjClose;
    private String volume;
    private String date;
    
    
}
