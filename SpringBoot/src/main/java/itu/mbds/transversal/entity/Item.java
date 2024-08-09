package itu.mbds.transversal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {

    @NotNull(message = "Ajoutez le nom de l'objet")
    @NotBlank(message = "Ajoutez le nom de l'objet")
    @NotEmpty(message = "Ajoutez le nom de l'objet")
    String name;

    @NotNull(message = "Ajoutez une description")
    @NotBlank(message = "Ajoutez une description")
    @NotEmpty(message = "Ajoutez une description")
    String description;

    @CreatedDate
    Date dateCreated;

    @ColumnDefault("false")
    Boolean validated;

    @NotNull(message = "Ajoutez une image")
    @NotBlank(message = "Ajoutez une image")
    @NotEmpty(message = "Ajoutez une image")
    String picture;

    @ManyToOne
    ItemCategory itemCategory;
}
