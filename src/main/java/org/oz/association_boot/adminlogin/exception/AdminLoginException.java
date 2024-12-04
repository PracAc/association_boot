package org.oz.association_boot.adminlogin.exception;

public enum AdminLoginException {
    BAD_AUTH(900, "ID/PW incorrect"),
    TOKEN_NOT_ENOUGH(901, "AccessToken or RefreshToken is NULL"),
    ACCESSTOKEN_TOO_SHORT(902, "Access Token too short"),
    REQUIRE_SIGN_IN(903, "Require sign in"),
    ACCESSTOKEN_EXPIRED(904, "Access Token just Expired!!!"),
    REFRESHTOKEN_EXPIRED(905, "Refresh Token just Expired!!!");

    private final int status;
    private final String message;

    AdminLoginException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public AdminLoginTaskException getException() {
        return new AdminLoginTaskException(status, message);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}