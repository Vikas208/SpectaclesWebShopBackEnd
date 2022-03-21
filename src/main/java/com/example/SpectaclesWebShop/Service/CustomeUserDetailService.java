package com.example.SpectaclesWebShop.Service;

import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.Dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomeUserDetailService implements UserDetailsService {

    @Autowired
    LoginDao loginDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Login login = loginDao.findByMailId(username);
            if (login == null || login.getMailId() == null) {
                throw new UsernameNotFoundException("User Not Found");
            } else if (login.getMailId() != null) {
                return new User(login.getMailId(), login.getPassword(), getAuthorities(login.getHasRole()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UsernameNotFoundException("User Not Found");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String authority) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(authority));
        return authorities;
    }


}
