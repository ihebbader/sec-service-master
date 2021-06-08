package org.sid.Security;

public interface SecurityParams {
    String JWT_HEADER_NAME="Authorization";
    String SECRET="baderiheb@gmail.com";
    long EXPIRATION=10*24*36000;
    String HEADER_PREFIX="Bearer ";
}
