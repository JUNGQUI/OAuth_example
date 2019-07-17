package com.example.jk.oauth.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name=SimpleData.TABLE_NAME)
public class SimpleData {
    protected static final String TABLE_NAME = "simple_data";

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String message;

    @ManyToOne
    private User user;
}
