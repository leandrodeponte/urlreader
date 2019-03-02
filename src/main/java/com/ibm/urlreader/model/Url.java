package com.ibm.urlreader.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "url")
public class Url {

    public Url(String text){
        this.text = text;
    }

    @Getter @Setter
    @Id
    private UUID id;

    @Getter @Setter
    private String text;
}
