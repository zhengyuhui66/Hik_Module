package com.hik.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hik.app.entity.AUDIT_USERINFO;
import com.hik.app.entity.MATERIEL_INFO;
import com.hik.app.entity.SYS_RIGHT;

public interface AUDIT_USERINFO_jpa   extends JpaRepository<AUDIT_USERINFO, String>{

}
