package gr.uoa.di.dbm.webapp.service;

import gr.uoa.di.dbm.webapp.dao.AppUserDAO;
import gr.uoa.di.dbm.webapp.dao.IGenericDAO;
import gr.uoa.di.dbm.webapp.entity.AppRole;
import gr.uoa.di.dbm.webapp.entity.AppUser;
import gr.uoa.di.dbm.webapp.entity.Role;
import gr.uoa.di.dbm.webapp.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserDAO appUserDAO;

    private final IGenericDAO<AppRole> appRoleDAO;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserDetailsServiceImpl(AppUserDAO appUserDAO, @Qualifier("appRoleGenericDAO") IGenericDAO<AppRole> appRoleDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserDAO = appUserDAO;
        this.appRoleDAO = appRoleDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        AppUser appUser = appUserDAO.getUserProfile(userName);

        if (appUser == null) {
            System.out.println("User not found! " + userName);
            return null;
            //throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]  .........OLD
        //List<String> roleNames = appRoleDAO.getRoleNames(appUser.getUserId());
        //List<GrantedAuthority> grantList = new ArrayList<>();
        //if (roleNames != null)
        //    roleNames.stream().map(SimpleGrantedAuthority::new).forEach(grantList::add);
            /*for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }*/
        List<GrantedAuthority> grantList = new ArrayList<>();
        appUser.getUserRoles()
                .stream()
                .map(UserRole::getAppRole)
                .map(AppRole::getRoleName)
                .collect(Collectors.toList())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .forEach(grantList::add);

        return new User(appUser.getUserName(), appUser.getEncrytedPassword(), grantList);
    }

    public boolean checkIfUserExists(String username){
        return appUserDAO.findByUsername(username) != null;
    }

    public AppUser registerUser(AppUser appUser){
    //public AppUser registerUser(Map<String,String> credentials){
        try {
            UserRole userRole = new UserRole();

            appUser.setEncrytedPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
            appUser.setEnabled(1);
            appUser.addUserRole(userRole);

            AppRole appRole = appRoleDAO.findById(Role.ROLE_USER.getValue());
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
