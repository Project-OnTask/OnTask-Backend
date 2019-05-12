package com.itsfive.back.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.itsfive.back.model.audit.DateAudit;

@Entity
@Table(name = "task", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "id"
        })
})
public class Task extends DateAudit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String description;
    
    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private int userid;*/
    
    public Task(String name,String description) {
    	this.name = name;
    	this.description = description;
    }
    
    public Long getId() {
    	return id;
    }
}
