package org.iksoft.Service;

import org.iksoft.Entity.User;
import org.iksoft.Exception.NotFoundException;
import org.iksoft.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author IK
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                new NotFoundException("Not found user with username "+username)
        );
    }

    public boolean isUserRegistered(String username){
        return userRepository.existsUserByUsername(username);
    }

    public User getUserById(Integer id){
        return userRepository.findById(id).orElseThrow(
                new NotFoundException("Not found user with id "+id)
        );
    }

    public User addUser(String username, String email, String password, String role){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(getHashedPassword(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    public void changeEmail(Integer id, String newEmail){
        User tmp = getUserById(id);
        tmp.setEmail(newEmail);
        userRepository.save(tmp);
    }

    public void changePassword(Integer id, String newPassword){
        User tmp = getUserById(id);
        tmp.setPassword(getHashedPassword(newPassword));
        userRepository.save(tmp);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }

    //password of user must be saved as hash-value
    private BigInteger getHashedPassword(String pass) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("md5");

            byte[] hashByte = md.digest(
                    pass.getBytes(StandardCharsets.UTF_8)
            );

            return new BigInteger(1, hashByte);

        } catch (NoSuchAlgorithmException exception) {
            return new BigInteger("0");
        }
    }
}
