package com.alberg.jiaqi.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.alberg.jiaqi.exceptions.UserAlreadyBindedException;

@Repository
public class GuotaiPlatformDaoImpl implements GuotaiPlatformDao {

	private static final String USERBIND_INSERT = "insert into userbind(openId,userId)values(?,?)";

	private static final String IS_USERBIND_QUERY = "select count(*) from userbind where openId=? or userId = ?";

	private static final String GET_USER_ID_BY_OPENID_QUERY = "select userId from userbind where openId=?";
	
	private JdbcTemplate jdbcTemplate;

	
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/* (non-Javadoc)
	 * @see com.guotai.dao.GuotaiPlatformDao#insertUserBind(java.lang.String, java.lang.String)
	 */
	@Override
	public void insertUserBind(String openId, String userId) throws UserAlreadyBindedException {
		if (this.isUserBind(openId, userId)) {
			throw new UserAlreadyBindedException();
		}
		jdbcTemplate.update(USERBIND_INSERT, openId, userId);
	}

	/* (non-Javadoc)
	 * @see com.guotai.dao.GuotaiPlatformDao#isUserBind(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isUserBind(String openId, String userId) {
		int rowCount = jdbcTemplate.queryForObject(IS_USERBIND_QUERY,
				new Object[] { openId, userId }, Integer.class);
		if (rowCount == 0) {
			return false;
		}
		return true;
	}

	@Override
	public String getUserIdByOpenId(String openId) {
		String userId = null;
		try {
			userId = jdbcTemplate.queryForObject(GET_USER_ID_BY_OPENID_QUERY,
				new Object[] { openId }, String.class);		
		} catch (EmptyResultDataAccessException edae) {
			return null;
		}
		return userId;
	}
}
