package com.plim.plimserver.api.repository;

import com.plim.plimserver.api.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {

}
