package com.hik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hik.app.entity.SYS_ROLE;
public interface SYS_ROLE_jpa   extends JpaRepository<SYS_ROLE, String>{

}
