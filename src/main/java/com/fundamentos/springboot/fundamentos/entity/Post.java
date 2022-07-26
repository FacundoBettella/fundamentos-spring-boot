package com.fundamentos.springboot.fundamentos.entity;

import javax.persistence.*;

@Entity
@Table(name = "post")
public class Post {

    /* Para que se cree un ID único*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*Nombramos columna y su valor no puede ser null */
    @Column(name = "id_post", nullable = false, unique = true)
    private Long id;

    @Column(name = "description", length = 255)
    private String description;

    /* Creamos relacion con la entidad usuario */
    @ManyToOne
    private User user;

    /* Constructores para hacer registros en la base de datos */
    public Post() {
    }

    public Post(String description, User user) {
        this.description = description;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
