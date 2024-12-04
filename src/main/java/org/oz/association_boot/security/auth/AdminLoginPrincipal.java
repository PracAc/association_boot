package org.oz.association_boot.security.auth;

import java.security.Principal;

public class AdminLoginPrincipal implements Principal {

    private final String adminId;

    public AdminLoginPrincipal(final String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String getName() {
        return adminId;
    }

}

