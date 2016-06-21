package com.theironyard;

import javax.persistence.*;

/**
 * Created by will on 6/20/16.
 */
@Entity
@Table(name = "microblog")
public class Message {
    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String text;

    public Message(Integer id, String text) {
        this.id = id;
        this.text = text;
    }
}
