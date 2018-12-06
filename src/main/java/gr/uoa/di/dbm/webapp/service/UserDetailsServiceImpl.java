package gr.uoa.di.dbm.webapp.service;

import gr.uoa.di.dbm.webapp.dao.AppUserDAO;
import gr.uoa.di.dbm.webapp.dao.GenericDAO;
import gr.uoa.di.dbm.webapp.dao.IGenericDAO;
import gr.uoa.di.dbm.webapp.dao.ServiceRequestDAO;
import gr.uoa.di.dbm.webapp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserDAO appUserDAO;

    private final IGenericDAO<AppRole> appRoleDAO;

    private final ServiceRequestDAO serviceRequestDAO;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserDetailsServiceImpl(AppUserDAO appUserDAO, GenericDAO<AppRole> appRoleDAO, ServiceRequestDAO serviceRequestDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserDAO = appUserDAO;
        this.appRoleDAO = appRoleDAO;
        this.serviceRequestDAO = serviceRequestDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        appRoleDAO.setEntityClass(AppRole.class);
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

    public AppUser registerUser(Map<String,String> credentials){
        try {
            UserRole userRole = new UserRole();

            AppUser appUser = new AppUser();
            appUser.setUserName(credentials.get("username"));
            appUser.setEncrytedPassword(bCryptPasswordEncoder.encode(credentials.get("password")));
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

    public String testProc1(){
        List result = serviceRequestDAO.callProcedure1();
        return result.toString();
    }

}
