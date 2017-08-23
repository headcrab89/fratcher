package com.webengineering.fratcher.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Sets the current user to anonymous.
     */
    public void setAnonymous() {
        setCurrentUser(-1L, "<anonymous>");
    }


    /**
     * Check if the current user is not authenticated.
     *
     * @return true if the user is not authenticated.
     */
    public boolean isAnonymous() {
        return getCurrentUser().getId() == -1L;
    }

    /**
     * Retrieve the currently active user or null, if no user is logged in.
     *
     * @return the current user.
     */
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Set a user for the current request.
     *
     * @param id    user id
     * @param userName user userName
     */
    public void setCurrentUser(Long id, String userName) {
        LOG.debug("Setting user context. id={}, user={}", id, userName);
        User user = new User();
        user.setId(id);
        user.setUserName(userName);
        UsernamePasswordAuthenticationToken secAuth = new UsernamePasswordAuthenticationToken(user, null);
        SecurityContextHolder.getContext().setAuthentication(secAuth);
    }

    /**
     * Retrieve a user with the given userName and password.
     *
     * @param userName    userName
     * @param password password
     * @return the user or null if none could be found
     */
    public User getUser(String userName, String password) {
        LOG.debug("Retrieving user from database. user={}", userName);
        return userRepository.findByUserNameAndPassword(userName, password);
    }

    /**
     * Retrieve a user with the given userName and password.
     *
     * @param userName    userName
     * @return the user or null if none could be found
     */
    public User getUserByUserName(String userName) {
        LOG.debug("Retrieving user from database. user={}", userName);
        return userRepository.findByUserName(userName);
    }

    public void saveUser (User user) {
        userRepository.save(user);
    }
}
