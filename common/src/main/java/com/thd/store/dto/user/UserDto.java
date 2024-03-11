package com.thd.store.dto.user;

import com.thd.store.entity.Role;
import com.thd.store.entity.User;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DatNuclear 30/01/2024
 * @project store-movie
 */
@Data
public class UserDto {
    private String username;
    private String gender;
    private String email;
    private List<String> roles = new ArrayList<>();
    private String displayName;

    public UserDto() {
    }
    public UserDto(User entity) {
        if(entity == null){
            return;
        }
        this.displayName = entity.getPerson().getDisplayName();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        if(!CollectionUtils.isEmpty(entity.getRoles())){
            for(Role role :entity.getRoles()){
                this.roles.add(role.getName());
            }
        }
    }
}
