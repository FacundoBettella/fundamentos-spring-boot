package com.fundamentos.springboot.fundamentos.repository;

import com.fundamentos.springboot.fundamentos.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /* Con JPQL trabajamos con objetos, no con tablas o columnas */
    @Query("Select u FROM User u WHERE u.userName=?1 ")
    Optional <User> findByName(String userName);

    /* Busca y ordena a partir del nombre de usuario */
    @Query("SELECT u FROM User u WHERE u.userName like ?1% ")
    List <User> findAndSort(String userName, Sort sort);

    /* El parametro por el que va a buscar en la db está en la definicion de la función. */
    List <User> findByUserLastName(String loQueQuieras);

    Optional <User> findByUserNameAndUserLastName(String name, String lastName);
}
