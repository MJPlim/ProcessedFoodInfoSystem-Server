package com.plim.plimserver.domain.api.repository;

import com.plim.plimserver.domain.api.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {

}
