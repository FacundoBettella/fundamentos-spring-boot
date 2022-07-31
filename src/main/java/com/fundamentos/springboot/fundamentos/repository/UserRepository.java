package com.fundamentos.springboot.fundamentos.repository;

import com.fundamentos.springboot.fundamentos.dto.UserDto;
import com.fundamentos.springboot.fundamentos.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    /* Con JPQL trabajamos con objetos, no con tablas o columnas */
    @Query("Select u FROM User u WHERE u.userName=?1 ")
    Optional <User> findByName(String userName);

    /* Busca y ordena a partir del nombre de usuario */
    @Query("SELECT u FROM User u WHERE u.userName like ?1% ")
    List <User> findAndSort(String userName, Sort sort);

    /* El parametro por el que va a buscar en la db está en la definicion de la función. */
    List <User> findByUserLastName(String loQueQuieras);

    Optional <User> findByUserNameAndUserLastName(String name, String lastName);

    Optional <User> findByUserNameLike(String name);

    Optional <User> findByUserNameOrUserLastName(String name, String lastName);

    List <User> findByUserNameLikeOrderByIdUserDesc(String name);

    List <User> findByUserNameContainingOrderByIdUserAsc(String name);

    /* Sentencia JPQL. Usamos el DTO representar (must all) los datos que obtenemos de la db */
    @Query(" SELECT new com.fundamentos.springboot.fundamentos.dto.UserDto(u.idUser, u.userName, u.userLastName)" +
            " FROM User u " +
            " where u.userName=:parametroName" +
            " and u.userLastName=:parametroLastName ")
    Optional <UserDto> getAllByUserNameAndLastName(@Param("parametroName") String name,
                                                   @Param("parametroLastName") String lastName);

    List<User> findAll(); /* PagingAndSortingRepository extends */
}
