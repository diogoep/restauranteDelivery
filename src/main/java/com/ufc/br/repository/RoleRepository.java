package com.ufc.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ufc.br.model.Role;

public  interface RoleRepository extends JpaRepository<Role, Long>{
}
