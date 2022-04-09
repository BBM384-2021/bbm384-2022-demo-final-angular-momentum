

package com.LinkedHU_CENG.springsecuritymongo.repositories;

import com.LinkedHU_CENG.springsecuritymongo.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, String> {
    
    Role findByRole(String role);
    
}
