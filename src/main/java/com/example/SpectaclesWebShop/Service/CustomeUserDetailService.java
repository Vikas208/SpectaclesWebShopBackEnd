package com.example.SpectaclesWebShop.Service;

import com.example.SpectaclesWebShop.Bean.Login;
import com.example.SpectaclesWebShop.Dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomeUserDetailService implements UserDetailsService {

    @Autowired
    LoginDao loginDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            try{

                    Login login = loginDao.findByMailId(username);
                    if(login == null || login.getMailId() == null){
                        throw new UsernameNotFoundException("User Not Found");
                    }
                    else if(login.getMailId()!=null){
                        return new User(login.getMailId(),login.getPassword(),new ArrayList<>());
                    }
            }catch (Exception e){
                System.out.println(e.toString());
            }
        throw new UsernameNotFoundException("User Not Found");
    }
}
