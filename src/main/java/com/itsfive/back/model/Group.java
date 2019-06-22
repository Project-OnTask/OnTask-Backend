package com.itsfive.back.model;

import java.util.HashSet;
import java.util.Optional;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.itsfive.back.model.audit.DateAudit;

@Entity
@Table(name = "groups")
public class Group extends DateAudit{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    private String name;
    
    @Size(max = 160)
    private String description;
    
    @ManyToOne
    private User created_by;
    
    public User getCreated_by() {
		return created_by;
	}

	public void setCreated_by(User created_by) {
		this.created_by = created_by;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	private String coverPhoto;
    
//    @ManyToMany(mappedBy = "joinedGroups")
//    Set<User> members;
    
//    @OneToMany(mappedBy = "group")
//    Set<GroupMember> role;
    
	public Group(@NotBlank @Size(max = 30) String name, @Size(max = 160) String description,User created_by) {
		super();
		this.name = name;
		this.description = description;
		this.created_by = created_by;
	}
	
	public Group() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String groupName) {
		this.name = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getCreatedBy() {
		return created_by;
	}

	public void setCreatedBy(User user) {
		this.created_by = user;
	}
}
