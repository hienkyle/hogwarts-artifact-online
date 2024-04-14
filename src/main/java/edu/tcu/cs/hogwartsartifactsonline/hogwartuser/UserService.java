package edu.tcu.cs.hogwartsartifactsonline.hogwartuser;

import edu.tcu.cs.hogwartsartifactsonline.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

//    private final PasswordEncoder passwordEncoder;

//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<HogwartsUser> findAll(){
        return this.userRepository.findAll();
    }

    public HogwartsUser findById(Integer userId){
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    }

    public HogwartsUser save(HogwartsUser newUser){
        // TODO: we need to encode plain text password
//        newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        newUser.setPassword(newUser.getPassword());
        return this.userRepository.save(newUser);
    }

    /*
     * Do not use this to change user password
     *
     * @param userId
     * @param update
     * @return
     */
    public HogwartsUser update(Integer userId, HogwartsUser update){
        HogwartsUser oldUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));

        oldUser.setUsername(update.getUsername());
        oldUser.setEnabled(update.isEnabled());
        oldUser.setRoles(update.getRoles());

        return this.userRepository.save(oldUser);
    }

    public void delete(Integer userId){
        this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
        this.userRepository.deleteById(userId);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//        return this.userRepository.findByUsername(username)
//                .map(user -> new MyUserPrincipal(user))
//                .orElseThrow(() -> new UsernameNotFoundException("username " + username + " is not found"));
//    }
}
