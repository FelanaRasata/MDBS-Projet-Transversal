package itu.mbds.transversal.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    @NotNull(message = "Ajoutez votre nom d'utilisateur")
    @NotBlank(message = "Ajoutez votre nom d'utilisateur")
    @NotEmpty(message = "Ajoutez votre nom d'utilisateur")
    private String username;

    @NotNull(message = "Ajoutez votre mot de passe")
    @NotBlank(message = "Ajoutez votre mot de passe")
    @NotEmpty(message = "Ajoutez votre mot de passe")
    private String password;

    @NotNull(message = "Ajoutez votre nom")
    @NotBlank(message = "Ajoutez votre nom")
    @NotEmpty(message = "Ajoutez votre nom")
    private String name;

    @NotNull(message = "Ajoutez un contact")
    @NotBlank(message = "Ajoutez un contact")
    @NotEmpty(message = "Ajoutez un contact")
    private String contact;

    @ColumnDefault("false")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @ColumnDefault(value = "now()")
    Date dateCreated;

}