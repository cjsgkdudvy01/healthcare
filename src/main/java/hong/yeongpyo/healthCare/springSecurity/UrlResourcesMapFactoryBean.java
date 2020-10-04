package hong.yeongpyo.healthCare.springSecurity;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import hong.yeongpyo.healthCare.springSecurity.service.SecuredObjService;

public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

	private SecuredObjService securedObjService;
	
	private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap;
	
	public void setSecuredObjService(SecuredObjService securedObjService) {
		this.securedObjService = securedObjService;
	}

	public void init() throws Exception {
		requestMap = securedObjService.getRolesAndUrl();
	}
	
	@Override
	public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
		// TODO Auto-generated method stub
		if(requestMap == null){
			requestMap = securedObjService.getRolesAndUrl();
		}
		return requestMap;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return LinkedHashMap.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}
}
