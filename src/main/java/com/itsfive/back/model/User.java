
package com.itsfive.back.model;

import com.itsfive.back.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
@UniqueConstraint(columnNames = { "email" }) })
public class User extends DateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 30)
	private String fname;

	@Size(max = 30)
	private String lname;

	@Lob
	private String bio;

	@Size(max = 40)
	private String username;

	@Size(max = 30)
	@Column(unique = true)
	private String mobile;

	// This email field is used for login
	@Size(max = 40)
	@Column(name = "email", unique = true)
	private String email;

	// This contains the actual email for both web and mobile users
	@Size(max = 40)
	@Email
	@Column(name = "email_addr", unique = true)
	private String email_addr;

	@Size(max = 32)
	@Column(name = "email_hash", unique = true)
	private String emailHash;

	@Size(max = 100)
	private String password;

	@Column(name = "enabled_mail")
	private boolean enabledMail;

	@Column(name = "confirm_mail_token")
	private String confirmMailToken;

	@Column(name = "enabled_phone")
	private boolean enabledPhone;

	@Column(name = "pro_pic")
	private String proPicURL;

	@Column(name = "web_link")
	private String websiteLink;

	@Column(name = "twitter_link")
	private String twitterLink;

	@Column(name = "so_link")
	private String stackOverflowLink;

	@Column(name = "github_link")
	private String githubLink;

	@Column(name = "linkedin_link")
	private String linkedInLink;
	
	@Column(name = "outlook_code")
	private String outlookCode;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserWork> userWorks;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserEducation> userEducations;

	@OneToMany(mappedBy = "user")
	Set<GroupMember> role;

	@Column(name = "is_app_user")
	private boolean isAppUser;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Roles> roles = new HashSet<>();

	public User() {

	}

	public User(String fname, String mobile) {
		this.enabledPhone = false;
		this.fname = fname;
		this.mobile = mobile;
		this.username = mobile;
		this.isAppUser = true;
	}

	public User(Long id, String fname) {
		this.id = id;
		this.fname = fname;
	}

	public User(String name, String username, String email, String password) {
		this.enabledMail = false;
		this.fname = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isAppUser = false;
	}
	
	public String getOutlookCode() {
		return outlookCode;
	}

	public void setOutlookCode(String outlookCode) {
		this.outlookCode = outlookCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public String getEmail_addr() {
		return email_addr;
	}

	public void setEmail_addr(String email_addr) {
		this.email_addr = email_addr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isEnabledPhone() {
		return enabledPhone;
	}

	public void setEnabledPhone(boolean enabledPhone) {
		this.enabledPhone = enabledPhone;
	}

	public boolean isMailEnabled() {
		return enabledMail;
	}

	public String getConfirmMailToken() {
		return confirmMailToken;
	}

	public void setConfirmMailToken(String confirmMailToken) {
		this.confirmMailToken = confirmMailToken;
	}

	public void setMailEnabled(boolean enabled) {
		this.enabledMail = enabled;
	}

	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}

	public String getTwitterLink() {
		return twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public String getStackOverflowLink() {
		return stackOverflowLink;
	}

	public void setStackOverflowLink(String stackOverflowLink) {
		this.stackOverflowLink = stackOverflowLink;
	}

	public String getGithubLink() {
		return githubLink;
	}

	public void setGithubLink(String githubLink) {
		this.githubLink = githubLink;
	}

	public String getLinkedInLink() {
		return linkedInLink;
	}

	public void setLinkedInLink(String linkedInLink) {
		this.linkedInLink = linkedInLink;
	}

	public String getEmailHash() {
		return emailHash;
	}

	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}
	
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProPicURL() {
		return proPicURL;
	}

	public void setProPicURL(String proPicURL) {
		this.proPicURL = proPicURL;
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
	
	public boolean isAppUser() {
		return isAppUser;
	}

	public void setAppUser(boolean isAppUser) {
		this.isAppUser = isAppUser;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

}