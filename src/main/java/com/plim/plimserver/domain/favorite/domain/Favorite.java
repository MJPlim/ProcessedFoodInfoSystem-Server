package com.plim.plimserver.domain.favorite.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.plim.plimserver.domain.food.domain.Food;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "favorite")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "favorite_id")
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;			//매핑아직안함
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id")
	private Food food;			//이것두

	@Builder
	public Favorite(Long userId, Food food) {
		this.userId = userId;
		this.food = food;
		this.food.getFavoriteList().add(this);
	}
}
