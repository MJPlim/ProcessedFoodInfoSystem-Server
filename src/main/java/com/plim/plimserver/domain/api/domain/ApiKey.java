package com.plim.plimserver.domain.api.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "API_Key")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String keyValue;
}
