package itu.mbds.transversal.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategory extends BaseEntity {

    @NotNull(message = "Ajoutez le nom de la catégorie")
    @NotBlank(message = "Ajoutez le nom de la catégorie")
    @NotEmpty(message = "Ajoutez le nom de la catégorie")
    String title;

}
