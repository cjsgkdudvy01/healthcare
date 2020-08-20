package hong.yeongpyo.healthCare.springSecurity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class CustomJdbcDaoImpl extends JdbcDaoImpl{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetails> users = loadUsersByUsername(username);
		Member user = (Member)users.get(0);
		Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
		
		if(users.size() == 0) {
			logger.debug("Query returned no results for user'"+username+"'");
			UsernameNotFoundException ue = new UsernameNotFoundException(messages.getMessage("jdbcDaoImpl.notFound", new Object[] {username}, "Username {0} not found"));
			throw ue;
		}
		if(getEnableAuthorities())
			dbAuthsSet.addAll(loadUserAuthorities(user.getNo()));
		if(getEnableGroups())
			dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
		
		List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet); 
		user.setAuthorities(dbAuths);
		
		if(dbAuths.size() == 0) {
			logger.debug("user '"+username+"' has no authorities and will be treated as 'mot found'");
			
			UsernameNotFoundException ue = new UsernameNotFoundException(messages.getMessage("jdbcDaoImpl.noAuthority", new Object[] {username}, "User {0} has no GrantedAuthority"));
			throw ue;
		}
		return user;
	}
	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
		return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {
			public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException{
				int no = rs.getInt (1);
				String username = rs.getString (2);
				String password = rs.getString (3);
				String name = rs.getString (4);
				String rrn1 = rs.getString (5);
				String rrn2 = rs.getString(6);
				String nick = rs.getString(7);
				int phonenum1 = rs.getInt (8);
				int phonenum2 = rs.getInt (9);
				int phonenum3 = rs.getInt (10);
				
				return new Member(no, username, password, name, rrn1, rrn2, nick, phonenum1, phonenum2, phonenum3, AuthorityUtils.NO_AUTHORITIES);	//sqlsession으로 작업하도록 변결
			}
			
		});
	}
	protected List<GrantedAuthority> loadUserAuthorities(Integer no) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().query(getAuthoritiesByUsernameQuery(), new Integer[] {no}, new RowMapper<GrantedAuthority>() {
            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                String roleName = getRolePrefix() + rs.getString(1);

                return new SimpleGrantedAuthority(roleName);
            }
        });
	}
	@Override
	protected List<GrantedAuthority> loadGroupAuthorities(String username) {
		// TODO Auto-generated method stub
		return super.loadGroupAuthorities(username);
	}
}
