package itu.mbds.transversal.utils.dto.request;

import itu.mbds.transversal.entity.Item;
import itu.mbds.transversal.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeOwnerDTO {
    List<Item> items;
    User newOwner;
}
