package itu.mbds.transversal.entity;

import itu.mbds.transversal.utils.enumeration.AdsStatusValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class PostStatus extends BaseEntity {

    String adsId;

    @Enumerated(EnumType.STRING)
    AdsStatusValue status;

    Date dateCreated;

}
