package itu.mbds.transversal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    Date dateCreated;

}
