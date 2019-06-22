package com.itsfive.back.model;

import org.hibernate.annotations.NaturalId;

import com.itsfive.back.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
public class User extends DateAudit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
    @Size(max = 30)
    private String fname;
    
    @Size(max = 30)
    private String lname;
    
    @Size(max = 40)
    private String username;

    @Size(max = 30)
    @Column(unique=true)
    private String mobile;

    @Size(max = 40)
    @Email
    @Column(name="email")
    private String email;

    @Size(max = 100)
    private String password;
    
    @Column(name = "enabled")
    private boolean enabled;
    
    @Column(name = "pro_pic")
    private String proPicURL;
    
    @Column(name = "cover")
    private String coverURL;
   
    public String getProPicURL() {
		return proPicURL;
	}

	public void setProPicURL(String proPicURL) {
		this.proPicURL = proPicURL;
	}

	public String getCoverURL() {
		return coverURL;
	}

	public void setCoverURL(String coverURL) {
		this.coverURL = coverURL;
	}

	@OneToMany(mappedBy = "user")
    Set<GroupMember> role;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();
   
    public User() {
    	
    }
    
    public User(String fname,String mobile) {
    	this.fname = fname;
    	this.mobile = mobile;
    }
    
    public User(Long id,String fname) {
    	this.id = id;
    	this.fname = fname;
    }

    public User(String name, String username, String email, String password) {
    	this.enabled = false;
        this.fname = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFName() {
        return fname;
    }
    
    public void setFName(String fname) {
       this.fname = fname;
    }

    public void setName(String name) {
        this.fname = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getMobile() {
        return password;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
