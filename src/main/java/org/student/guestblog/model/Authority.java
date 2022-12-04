package org.student.guestblog.model;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    /**
     * Default authority for the unauthorized user.
     */
    ANONYMOUS,

    /**
     * Default authority for the usual user.
     */
    USER,

    /**
     * Uses for the granting of the administrative rights.
     */
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
