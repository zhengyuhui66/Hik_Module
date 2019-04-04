package com.hik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hik.app.entity.SYS_RIGHT;
import com.hik.app.entity.SYS_USER;

public interface SYS_USER_jpa   extends JpaRepository<SYS_USER, Integer>{

}
