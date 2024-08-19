/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ru.company.understandablepractice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author mish
 */
@Entity
@Getter
@Setter
public class UserCredentials {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}
