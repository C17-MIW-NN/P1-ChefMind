package nl.miwnn.ch17.codalabs.chefmind.service.mappers;

import nl.miwnn.ch17.codalabs.chefmind.dto.NewChefMindUserDTO;
import nl.miwnn.ch17.codalabs.chefmind.model.ChefMindUser;

/**
 * @author Nelleke Jansen
 * Converts NewChefMindUserDTO Objects into ChefMindUsers
 */
public class ChefMindUserMapper {

    public static ChefMindUser fromDTO(NewChefMindUserDTO newChefMindUserDTO) {
        ChefMindUser chefMindUser = new ChefMindUser();

        chefMindUser.setUsername(newChefMindUserDTO.getUsername());
        chefMindUser.setPassword(newChefMindUserDTO.getPassword());

        return chefMindUser;
    }
}
