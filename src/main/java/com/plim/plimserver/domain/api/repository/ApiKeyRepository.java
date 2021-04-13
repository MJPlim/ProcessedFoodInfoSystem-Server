package com.plim.plimserver.domain.api.repository;

import com.plim.plimserver.domain.api.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    List<ApiKey> findAllByKeyName(String keyName);
}
