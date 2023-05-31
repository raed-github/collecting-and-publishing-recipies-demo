package com.rsaad.recipe.audit;


import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.AuditorAware;

/**
 * Use this file incase of spring security.
 * @author Raed Saad
 *
 */

class SpringSecurityAuditorAware{
//class SpringSecurityAuditorAware implements AuditorAware<SecurityProperties.User> {

//    public SecurityProperties.User getCurrentAuditor() {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return null;
//        }
//
//        return ((MyUserDetails) authentication.getPrincipal()).getUser();
//    }
}
