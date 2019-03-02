package com.ibm.urlreader.model;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Url {

    public Url(String text){
        this.text = text;
    }

    @Getter @Setter
    @Id
    @GeneratedValue
    private Long id;

    @Getter @Setter
    @Column(columnDefinition="varchar(1000)")
    private String text;
}
