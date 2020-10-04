package hong.yeongpyo.healthCare.springSecurity.dao;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class SecuredObjDao {
	private SqlSessionTemplate sqlSessionTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    public SecuredObjDao(SqlSessionTemplate sqlSessionTemplate) {
    	this.sqlSessionTemplate = sqlSessionTemplate;
    }
 
    public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndResources(String resourceType) throws Exception {

        LinkedHashMap<Object, List<ConfigAttribute>> resourcesMap = new LinkedHashMap<Object, List<ConfigAttribute>>();
        List<Map<String, Object>> resultList;
    
        boolean isResourcesUrl = true;
        if ("method".equals(resourceType)) {
        	resultList = sqlSessionTemplate.selectList("sqlRolesAndMethod");
            isResourcesUrl = false;
        } else if ("pointcut".equals(resourceType)) {
        	resultList = sqlSessionTemplate.selectList("sqlRolesAndPointcut");
            isResourcesUrl = false;
        } else {
        	resultList = sqlSessionTemplate.selectList("sqlRolesAndUrl");
        }

        Iterator<Map<String, Object>> itr = resultList.iterator();
        Map<String, Object> tempMap;
        String preResource = null;
        String presentResourceStr;
        Object presentResource;
        
        while (itr.hasNext()) {
            tempMap = itr.next();
            presentResourceStr = (String) tempMap.get(resourceType);
            // url 인 경우 RequestKey 형식의 key를 Map에 담아야 함
            presentResource = isResourcesUrl ? new AntPathRequestMatcher(presentResourceStr) : presentResourceStr;
            List<ConfigAttribute> configList = new LinkedList<ConfigAttribute>();

            // 이미 requestMap 에 해당 Resource 에 대한 Role 이 하나 이상 맵핑되어 있었던 경우, 
            // sort_order 는 resource(Resource) 에 대해 매겨지므로 같은 Resource 에 대한 Role 맵핑은 연속으로 조회됨.
            // 해당 맵핑 Role List (SecurityConfig) 의 데이터를 재활용하여 새롭게 데이터 구축
            if (preResource != null && presentResourceStr.equals(preResource)) {
                List<ConfigAttribute> preAuthList = resourcesMap.get(presentResource);
                Iterator<ConfigAttribute> preAuthItr = preAuthList.iterator();
                while (preAuthItr.hasNext()) {
                    SecurityConfig tempConfig = (SecurityConfig) preAuthItr.next();
                    configList.add(tempConfig);
                }
            }

            configList.add(new SecurityConfig((String) tempMap.get("authority")));
            
            // 만약 동일한 Resource 에 대해 한개 이상의 Role 맵핑 추가인 경우 
            // 이전 resourceKey 에 현재 새로 계산된 Role 맵핑 리스트로 덮어쓰게 됨.
            resourcesMap.put(presentResource, configList);

            // 이전 resource 비교위해 저장
            preResource = presentResourceStr;
        }
                
        return resourcesMap;
    }

    public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndUrl() throws Exception {
        return getRolesAndResources("url");
    }

    public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndMethod() throws Exception {
        return getRolesAndResources("method");
    }

    public LinkedHashMap<Object, List<ConfigAttribute>> getRolesAndPointcut() throws Exception {
        return getRolesAndResources("pointcut");
    }

    public List<ConfigAttribute> getRegexMatchedRequestMapping(String url) throws Exception {

        // best regex matching - best 매칭된 Url 에 따른 Role List 조회, 
    	// DB 차원의 정규식 지원이 있는 경우 사용 (ex. hsqldb custom function, Oracle 10g regexp_like 등)
        List<Map<String, Object>> resultList = sqlSessionTemplate.selectList("sqlRegexMatchedRequestMapping", url);  //this.namedParameterJdbcTemplate.queryForList(getSqlRegexMatchedRequestMapping(), paramMap);

        Iterator<Map<String, Object>> itr = resultList.iterator();
        Map<String, Object> tempMap;
        List<ConfigAttribute> configList = new LinkedList<ConfigAttribute>();
        // 같은 url 에 대한 Role 맵핑이므로 무조건 configList 에 add 함
        while (itr.hasNext()) {
            tempMap = itr.next();
            configList.add(new SecurityConfig((String)tempMap.get("authority")));
        }

        if (configList.size() > 0) {
        	logger.debug("Request Uri : {}, matched Uri : {}, mapping Roles : {}", url, resultList.get(0).get("uri"), configList);
            
        }
        return configList;
    }

    public String getHierarchicalRoles() throws Exception {

    	List<Map<String, Object>> resultList = sqlSessionTemplate.selectList("sqlHierarchicalRoles"); //this.namedParameterJdbcTemplate.queryForList(getSqlHierarchicalRoles(), new HashMap<String, String>());

        Iterator<Map<String, Object>> itr = resultList.iterator();
        StringBuffer concatedRoles = new StringBuffer();
        Map<String, Object> tempMap;
        while (itr.hasNext()) {
            tempMap = itr.next();
            concatedRoles.append(tempMap.get("child"));
            concatedRoles.append(" > ");
            concatedRoles.append(tempMap.get("parent"));
            concatedRoles.append("\n");
        }

        return concatedRoles.toString();
    }
}