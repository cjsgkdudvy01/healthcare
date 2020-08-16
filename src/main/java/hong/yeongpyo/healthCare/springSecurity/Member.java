package hong.yeongpyo.healthCare.springSecurity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class Member implements UserDetails{
	private int no;
	private String id;
	private String password;
	private String name;
	private String rrn1; 
	private String rrn2;
	private String nick;
	private int phonenum1;
	private int phonenum2;
	private int phonenum3;
	private Set<GrantedAuthority> authorities;
	public Member(int no, String id, String password, String name, String rrn1, String rrn2, String nick, int phonenum1, int phonenum2, int phonenum3, 
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.no = no;
		this.id = id;
		this.password = password;
		this.name = name;
		this.rrn1 = rrn1;
		this.rrn2 = rrn2;
		this.nick = nick;
		this.phonenum1 = phonenum1;
		this.phonenum2 = phonenum2;
		this.phonenum3 = phonenum3;
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}
	public int getNo() {
		return no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRrn1() {
		return rrn1;
	}

	public void setRrn1(String rrn1) {
		this.rrn1 = rrn1;
	}

	public String getRrn2() {
		return rrn2;
	}

	public void setRrn2(String nick) {
		this.nick = nick;
	}
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	public int getPhonenum1() {
		return phonenum1;
	}

	public void setPhonenum1(int phonenum1) {
		this.phonenum1 = phonenum1;
	}

	public int getPhonenum2() {
		return phonenum2;
	}

	public void setPhonenum2(int phonenum2) {
		this.phonenum2 = phonenum2;
	}
	public int getPhonenum3() {
		return phonenum3;
	}
	public void setPhonenum3(int phonenum3) {
		this.phonenum3 = phonenum3;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return getId();
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	} 
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
            new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        return sortedAuthorities;
	}
	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;	
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
}
