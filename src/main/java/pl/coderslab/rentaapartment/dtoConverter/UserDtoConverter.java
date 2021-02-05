package pl.coderslab.rentaapartment.dtoConverter;

import org.springframework.stereotype.Component;
import pl.coderslab.rentaapartment.dto.UserDto;
import pl.coderslab.rentaapartment.model.Role;
import pl.coderslab.rentaapartment.model.User;

@Component
public class UserDtoConverter {


    public UserDto entityToDto(User user){
        UserDto userDto = new UserDto();
        if(user.getRole().equals(Role.ROLE_FIRM)){
            userDto.setFirmName(user.getFirmName());
            userDto.setRegon(user.getRegon());
            userDto.setNip(user.getNip());
        }else {
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
        }
        userDto.setCreated(user.getCreated());
        userDto.setId(user.getId());
        userDto.setRole(user.getRole());
        userDto.setUserName(user.getUserName());

        return userDto;
    }

    public User dtoToEntity(UserDto userDto){
        User user = new User();
        if (userDto.getRole().equals(Role.ROLE_FIRM)){
            user.setFirmName(userDto.getFirmName());
            user.setNip(userDto.getNip());
            user.setRegon(userDto.getRegon());
        } else {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
        }
        user.setCreated(userDto.getCreated());
        user.setId(userDto.getId());
        user.setRole(userDto.getRole());
        user.setUserName(userDto.getUserName());
        return user;
    }
}
