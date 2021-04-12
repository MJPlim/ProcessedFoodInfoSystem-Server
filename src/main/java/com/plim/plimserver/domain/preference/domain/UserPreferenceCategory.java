package com.plim.plimserver.domain.preference.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user_preference_category")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPreferenceCategory {
	@Id @GeneratedValue
	@Column(name = "user_preference_category_id")
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "preference_category_id")
	private PreferenceCategory preferenceCategory;
	
	@Builder
	public UserPreferenceCategory(Long userId, PreferenceCategory preferenceCategory) {
		this.userId = userId;
		this.preferenceCategory = preferenceCategory;
		this.preferenceCategory.getUserPreferenceCategoriyList().add(this);
	}
	
	
	
}
