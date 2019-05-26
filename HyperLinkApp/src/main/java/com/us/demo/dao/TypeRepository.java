package com.us.demo.dao;

import com.us.demo.entity.api.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type,Integer> {
}
