package com.example.QuanLyPhongTro.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;

	private String password;

	private String email;

	private String phoneNumber;

	private String fullName;

	private Integer status;

	@Temporal(TemporalType.DATE)
	private Date createdAt;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_service")
	private ServicePackages servicePackage;

	@JsonProperty("id_service")
	public Integer getIdUser() {
		return servicePackage != null ? servicePackage.getId() : null;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Advertisements> advertisements;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SupportRequests> supportRequests;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Houses> houses;

	// Getters and Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public ServicePackages getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(ServicePackages servicePackage) {
		this.servicePackage = servicePackage;
	}

	public Set<Advertisements> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Set<Advertisements> advertisements) {
		this.advertisements = advertisements;
	}

	public Set<SupportRequests> getSupportRequests() {
		return supportRequests;
	}

	public void setSupportRequests(Set<SupportRequests> supportRequests) {
		this.supportRequests = supportRequests;
	}

	public Set<Houses> getHouses() {
		return houses;
	}

	public void setHouses(Set<Houses> houses) {
		this.houses = houses;
	}
}
