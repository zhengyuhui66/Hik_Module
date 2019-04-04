package com.hik.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.hik.app.entity.CLICK_COUNT_LOG;
import com.hik.app.entity.Happen;
import com.hik.app.entity.PRICONDITION;
import com.hik.app.entity.SHOW_COUNT_LOG;
import com.hik.framework.utils.JSONUtils;

import net.sf.json.JSONObject;
import oracle.jdbc.driver.OracleTypes;

@Repository
public class VehicleWifi_ningbo_DaoImpl extends BaseHIKDao implements VehicleWifi_ningbo_Dao{

	@Override
	public List<JSONObject> getSpeedAndTime(String gw_id) {
		// TODO Auto-generated method stub
		List params = new ArrayList();
		params.add(gw_id);
		String sql="select r.speed,r.timeout from DEVICE t LEFT JOIN vehicle v ON(t.vehicleid=v.id) LEFT JOIN ROUTE r ON(v.routeid=r.id) WHERE t.apmac=?";
		String[] str = new String[]{"speed","timeout"};
		return getNoPageObject(sql, params, str);
	}
}
