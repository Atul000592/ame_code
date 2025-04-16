package nic.ame.app.test.controller;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.io.*;

@Entity
@Table(name = "objects")
public class MyObjectEntity implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] objectData;

    // Constructors, getters, and setters

    public MyObjectEntity() {}

    public MyObjectEntity(byte[] objectData) {
        this.objectData = objectData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getObjectData() {
        return objectData;
    }

    public void setObjectData(byte[] objectData) {
        this.objectData = objectData;
    }
}

