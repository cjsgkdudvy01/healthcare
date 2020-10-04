package hong.yeongpyo.healthCare.springSecurity.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import hong.yeongpyo.healthCare.springSecurity.dto.MemberDtoNoAuth;

public class CustomJdbcDaoImpl extends JdbcDaoImpl{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private SqlSessionTemplate sqlSessionTemplate;
	
	public CustomJdbcDaoImpl(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetails> users = loadUsersByUsername(username);
		MemberDtoNoAuth user = (MemberDtoNoAuth)users.get(0);
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
		List<UserDetails>loadUsers = sqlSessionTemplate.selectList("loadUsersByUsername", username);
		
		return loadUsers;
	}
	
	protected List<GrantedAuthority> loadUserAuthorities(Integer no) {
		List<String> simpleGrantedAuthorityList = sqlSessionTemplate.selectList("authoritiesByUsername", no); 
		List<GrantedAuthority> simpleGrantedAuthority =  new ArrayList<GrantedAuthority>();  
		for(int i=0 ; i<simpleGrantedAuthorityList.size() ; i++) {
			simpleGrantedAuthority.add(i, new SimpleGrantedAuthority(simpleGrantedAuthorityList.get(i)));
		}		
		return simpleGrantedAuthority;
	}
	
	@Override
	protected List<GrantedAuthority> loadGroupAuthorities(String username) {
		// TODO Auto-generated method stub
		return super.loadGroupAuthorities(username);
	}
}