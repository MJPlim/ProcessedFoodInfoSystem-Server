package com.plim.plimserver.domain.preference.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "preference_category")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreferenceCategory {
	
	@Id @GeneratedValue
	@Column(name = "preference_category_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "preference_category_name", nullable = false)
	private PreferenceCategoryType preferenceCategoryType;
	
	@OneToMany(mappedBy = "preferenceCategory", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserPreferenceCategory> userPreferenceCategoriyList = new ArrayList<>();

	@Builder
	public PreferenceCategory(PreferenceCategoryType preferenceCategoryType) {
		this.preferenceCategoryType = preferenceCategoryType;
	}
	
	
	
}
