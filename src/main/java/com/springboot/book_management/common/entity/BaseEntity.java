package com.springboot.book_management.common.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
@MappedSuperclass
public class BaseEntity {
    
    @Column (name = "created_at")
    private Date createdAt;

    @Column (name = "updated_at")
    private Date updatedAt;
}
