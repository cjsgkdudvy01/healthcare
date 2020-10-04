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
            // url �� ��� RequestKey ������ key�� Map�� ��ƾ� ��
            presentResource = isResourcesUrl ? new AntPathRequestMatcher(presentResourceStr) : presentResourceStr;
            List<ConfigAttribute> configList = new LinkedList<ConfigAttribute>();

            // �̹� requestMap �� �ش� Resource �� ���� Role �� �ϳ� �̻� ���εǾ� �־��� ���, 
            // sort_order �� resource(Resource) �� ���� �Ű����Ƿ� ���� Resource �� ���� Role ������ �������� ��ȸ��.
            // �ش� ���� Role List (SecurityConfig) �� �����͸� ��Ȱ���Ͽ� ���Ӱ� ������ ����
            if (preResource != null && presentResourceStr.equals(preResource)) {
                List<ConfigAttribute> preAuthList = resourcesMap.get(presentResource);
                Iterator<ConfigAttribute> preAuthItr = preAuthList.iterator();
                while (preAuthItr.hasNext()) {
                    SecurityConfig tempConfig = (SecurityConfig) preAuthItr.next();
                    configList.add(tempConfig);
                }
            }

            configList.add(new SecurityConfig((String) tempMap.get("authority")));
            
            // ���� ������ Resource �� ���� �Ѱ� �̻��� Role ���� �߰��� ��� 
            // ���� resourceKey �� ���� ���� ���� Role ���� ����Ʈ�� ����� ��.
            resourcesMap.put(presentResource, configList);

            // ���� resource ������ ����
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

        // best regex matching - best ��Ī�� Url �� ���� Role List ��ȸ, 
    	// DB ������ ���Խ� ������ �ִ� ��� ��� (ex. hsqldb custom function, Oracle 10g regexp_like ��)
        List<Map<String, Object>> resultList = sqlSessionTemplate.selectList("sqlRegexMatchedRequestMapping", url);  //this.namedParameterJdbcTemplate.queryForList(getSqlRegexMatchedRequestMapping(), paramMap);

        Iterator<Map<String, Object>> itr = resultList.iterator();
        Map<String, Object> tempMap;
        List<ConfigAttribute> configList = new LinkedList<ConfigAttribute>();
        // ���� url �� ���� Role �����̹Ƿ� ������ configList �� add ��
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