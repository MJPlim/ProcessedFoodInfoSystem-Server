package com.plim.plimserver.domain.advertisement.domain;

import com.plim.plimserver.domain.food.domain.Food;
import lombok.*;

import javax.persistence.*;

@Table(name = "ad_food")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AdvertisementFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "impression_count")
    private Long impressionCount;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "ad_state")
    private String adState;

    @Builder
    public AdvertisementFood(Long id, Food food, Long viewCount, Long impressionCount, int priority, String adState) {
        this.id = id;
        this.food = food;
        this.viewCount = viewCount;
        this.impressionCount = impressionCount;
        this.priority = priority;
        this.adState = adState;
    }

    @PrePersist
    public void prePersist() {
        this.viewCount = this.viewCount == null ? 0 : this.viewCount;
        this.impressionCount = this.impressionCount == null ? 0 : this.impressionCount;
        this.priority = this.priority == null ? 0 : this.priority;
    }
}
