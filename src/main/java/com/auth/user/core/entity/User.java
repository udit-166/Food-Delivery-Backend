package com.auth.user.core.entity;

import java.util.List;
import java.util.UUID;

import com.auth.user.common.constant.AppConstants;
import com.auth.user.core.model.Location;
import com.auth.user.core.model.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = AppConstants.FDAUSER)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private UUID id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(unique = true, nullable = true)
	private String email;
	
	@Column(unique = true, nullable = true)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Embedded
	private Location location;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> address;
	
	private String profileImageUrl;
	
	private boolean isVerified;
	
	private boolean isActive;
	
	private String fcmToken;
	
	private String device_id;
	
	@Column(unique = true, nullable = true)
	private String googleId;
}
