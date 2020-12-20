package com.arw.hometest.service;


import com.arw.hometest.dao.RoleDao;
import com.arw.hometest.dao.UserAppDao;
import com.arw.hometest.model.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserAppDao userAppDao;

    @Autowired
    private RoleDao roleDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserApp userApp = this.userAppDao.findUserAccount(userName);

        if (userApp == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database!");
        }
        List<String> roleNames = this.roleDAO.getRoleNames(userApp.getUserId());
        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null) {
            for (String role : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = new User(
                userApp.getUserName(),
                userApp.getEncryptedPassword(),
                grantList);

        return userDetails;
    }
}
