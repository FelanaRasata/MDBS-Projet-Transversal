package itu.mbds.transversal.security;

import itu.mbds.transversal.entity.Role;
import itu.mbds.transversal.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails extends User implements UserDetails {

    private final long id;
    private final String username;
    private final String password;
    private final boolean enabled;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User byUsername) {
        this.id = byUsername.getId();
        this.username = byUsername.getUsername();
        this.password = byUsername.getPassword();
        this.enabled = byUsername.isEnabled();
        List<GrantedAuthority> auths = new ArrayList<>();

        for (Role role : byUsername.getRoles()) {

            auths.add(new SimpleGrantedAuthority(role.getAuthority().toUpperCase()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
