package com.us.demo.dao;

import com.us.demo.entity.AccessKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessKeyRepository extends JpaRepository<AccessKey,Integer> {
}
