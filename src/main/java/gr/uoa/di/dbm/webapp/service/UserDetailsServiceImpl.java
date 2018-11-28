package gr.uoa.di.dbm.webapp.service;

import gr.uoa.di.dbm.webapp.dao.AppRoleDAO;
import gr.uoa.di.dbm.webapp.dao.AppUserDAO;
import gr.uoa.di.dbm.webapp.entity.AppRole;
import gr.uoa.di.dbm.webapp.entity.AppUser;
import gr.uoa.di.dbm.webapp.entity.Role;
import gr.uoa.di.dbm.webapp.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@ComponentScan({"gr.uoa.di.dbm.webapp.dao"})
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AppRoleDAO appRoleDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        AppUser appUser = this.appUserDAO.findUserAccount(userName);

        if (appUser == null) {
            System.out.println("User not found! " + userName);
            return null;
            //throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = appRoleDAO.getRoleNames(appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roleNames != null)
            roleNames.stream().map(SimpleGrantedAuthority::new).forEach(grantList::add);
            /*for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }*/

        UserDetails userDetails = new User(appUser.getUserName(), //
                appUser.getEncrytedPassword(), grantList);

        return userDetails;
    }

    public AppUser registerUser(Map<String,String> credentials){
        try {
            UserRole userRole = new UserRole();

            AppUser appUser = new AppUser();
            appUser.setUserName(credentials.get("username"));
            appUser.setEncrytedPassword(bCryptPasswordEncoder.encode(credentials.get("password")));
            appUser.setEnabled(1);
            appUser.addUserRole(userRole);

            AppRole appRole = appRoleDAO.getRoleByName(Role.ROLE_USER.name());
            appRole.addUserRole(userRole);

            appUserDAO.insert(appUser);
            appRoleDAO.insertOrUpdate(appRole);

            return appUser;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
