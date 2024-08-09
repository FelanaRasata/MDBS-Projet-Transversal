package itu.mbds.transversal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserItem extends BaseEntity {

    @ManyToOne
    User user;

    @ManyToOne
    Item item;

    @Column(nullable = false, updatable = false)
    @ColumnDefault(value = "now()")
    Date dateCreated;

}
