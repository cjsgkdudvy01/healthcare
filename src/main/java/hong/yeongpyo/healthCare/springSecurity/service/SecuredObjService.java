package hong.yeongpyo.healthCare.springSecurity.service;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

public interface SecuredObjService {

	/**
     * �ѿ� ���� URL�� ���� ������ ���´�.
     * @return
     * @throws Exception
     */
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() throws Exception;

    /**
     * �ѿ� ���� �޼ҵ��� ���� ������ ���´�.
     * @return
     * @throws Exception
     */
    public LinkedHashMap<String, List<ConfigAttribute>> getRolesAndMethod() throws Exception;

    /**
     * �ѿ� ���� AOP pointcut ���� ������ ���´�.
     * @return
     * @throws Exception
     */
    public LinkedHashMap<String, List<ConfigAttribute>> getRolesAndPointcut() throws Exception;

    /**
     * Best ��Ī ������ ���´�.
     * @param url
     * @return
     * @throws Exception
     */
    public List<ConfigAttribute> getMatchedRequestMapping(String url) throws Exception;

    /**
     * ���� ������ ������ ���´�.
     * @return
     * @throws Exception
     */
    public String getHierarchicalRoles() throws Exception;
}

