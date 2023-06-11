package ar.edu.uno.sehda.services;

import ar.edu.uno.sehda.entities.DBUser;
import ar.edu.uno.sehda.entities.Rol;
import ar.edu.uno.sehda.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@Slf4j
public class UserService implements UserDetailsService {

    //implements UserDetailsService

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<DBUser> listUser(){
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(DBUser user){
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(DBUser user){
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public DBUser searchUser(DBUser user){
        return userRepository.findByUsername(user.getUsername());
    }

    public void ValidarUser(String email, String password) {
    }

    @Transactional(readOnly = true)
   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       DBUser user = userRepository.findByUsername(username);

       if (user == null) {
           throw new UsernameNotFoundException(username);
       }
       List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
       log.info("Dentro de UserService: ");
       for (Rol rol: user.getRoles()){
           log.info("Imprimiendo los roles: " + rol.getRolName());
           System.out.println("Roles : " + rol.getRolName());
           roles.add(new SimpleGrantedAuthority( rol.getRolName() ) );
       }

       return new User(user.getUsername(),user.getPassword(), roles);
    }


  /*  @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user ==null){
            throw new UsernameNotFoundException(username);
        }

        //var rol = new SimpleGrantedAuthority(user.getRol());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRol())));
    }
   */
}
