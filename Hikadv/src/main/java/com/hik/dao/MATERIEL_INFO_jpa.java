package com.hik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.SYS_RIGHT;

public interface MATERIEL_INFO_jpa   extends JpaRepository<MATERIEL_INFO, String>{

}
