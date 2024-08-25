/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ru.company.understandablepractice.security;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author mish
 */
public class NoSuchUserException extends AuthenticationException {

    public NoSuchUserException(String msg) {
        super(msg);
    }

}
