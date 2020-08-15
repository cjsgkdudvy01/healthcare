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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class CustomJdbcDaoImpl extends JdbcDaoImpl{
	private static final Logger logger = LoggerFactory.getLogger(CustomJdbcDaoImpl.class);
	SqlSessionTemplate sqlSessionTemplate; 
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
			dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
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
	protected List<UserDetails> loadUserByname(String username) {
		return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[] {username}, new RowMapper<UserDetails>() {
			public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException{
				
				//return new Member(id, password, name, phonenum1, phonenum2, phonenum3, gender, authorities)	//sqlsession으로 작업하도록 변결
			}
			
		});
		
	}
}
