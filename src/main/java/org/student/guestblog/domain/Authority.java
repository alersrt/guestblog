package org.student.guestblog.domain;

public enum Authority {

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

    public String getAuthority() {
        return this.name();
    }
}
