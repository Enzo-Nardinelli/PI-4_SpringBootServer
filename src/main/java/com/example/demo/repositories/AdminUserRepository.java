/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.example.demo.repositories;

import com.example.demo.models.AdminUser;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 *
 * @author Enzo
 */
import java.util.Optional;

public interface AdminUserRepository extends MongoRepository<AdminUser, String> {
    Optional<AdminUser> findByName(String name);
}
