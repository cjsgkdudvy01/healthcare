package hong.yeongpyo.healthCare.springSecurity.serviceImpl;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import hong.yeongpyo.healthCare.springSecurity.dao.SecuredObjDao;
import hong.yeongpyo.healthCare.springSecurity.service.SecuredObjService;

public class SecuredObjServiceImpl implements SecuredObjService {

	private SecuredObjDao securedObjDao;
	
	public SecuredObjServiceImpl(SecuredObjDao securedObjDao) {
		this.securedObjDao = securedObjDao;
	}
	
	@Override
	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<RequestMatcher, List<ConfigAttribute>> ret = new LinkedHashMap<RequestMatcher, List<ConfigAttribute>>();
		LinkedHashMap<Object, List<ConfigAttribute>> data = securedObjDao.getRolesAndUrl();
		Set<Object> keys = data.keySet();
		for(Object key : keys){
			ret.put((AntPathRequestMatcher)key, data.get(key));
		}
		return ret;
	}

	@Override
	public LinkedHashMap<String, List<ConfigAttribute>> getRolesAndMethod()
			throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, List<ConfigAttribute>> ret = new LinkedHashMap<String, List<ConfigAttribute>>();
		LinkedHashMap<Object, List<ConfigAttribute>> data = securedObjDao.getRolesAndMethod();
		Set<Object> keys = data.keySet();
		for(Object key : keys){
			ret.put((String)key, data.get(key));
		}
		return ret;
	}

	@Override
	public LinkedHashMap<String, List<ConfigAttribute>> getRolesAndPointcut()
			throws Exception {
		// TODO Auto-generated method stub
		LinkedHashMap<String, List<ConfigAttribute>> ret = new LinkedHashMap<String, List<ConfigAttribute>>();
		LinkedHashMap<Object, List<ConfigAttribute>> data = securedObjDao.getRolesAndPointcut();
		Set<Object> keys = data.keySet();
		for(Object key : keys){
			ret.put((String)key, data.get(key));
		}
		return ret;
	}

	@Override
	public List<ConfigAttribute> getMatchedRequestMapping(String url) throws Exception {
		// TODO Auto-generated method stub
		return securedObjDao.getRegexMatchedRequestMapping(url);
	}

	@Override
	public String getHierarchicalRoles() throws Exception {
		// TODO Auto-generated method stub
		return securedObjDao.getHierarchicalRoles();
	}

}