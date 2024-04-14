package edu.tcu.cs.hogwartsartifactsonline.hogwartuser;

import edu.tcu.cs.hogwartsartifactsonline.hogwartuser.converter.UserDtoToUserConverter;
import edu.tcu.cs.hogwartsartifactsonline.hogwartuser.converter.UserToUserDtoConverter;
import edu.tcu.cs.hogwartsartifactsonline.hogwartuser.dto.UserDto;
import edu.tcu.cs.hogwartsartifactsonline.system.Result;
import edu.tcu.cs.hogwartsartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {
    private final UserService userService;

    private final UserDtoToUserConverter userDtoToUserConverter;

    private final UserToUserDtoConverter userToUserDtoConverter;

    public UserController(UserService userService, UserDtoToUserConverter userDtoToUserConverter, UserToUserDtoConverter userToUserDtoConverter) {
        this.userService = userService;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @GetMapping
    public Result findAllUsers(){
        List<HogwartsUser> foundUsers = this.userService.findAll();

        List<UserDto> userDtos = foundUsers.stream()
                .map(foundUser -> this.userToUserDtoConverter.convert(foundUser))
                .collect(Collectors.toList());

        return new Result(true, StatusCode.SUCCESS, "Find All Success", userDtos);
    }

    @GetMapping("/{userId}")
    public Result findUserById(@PathVariable Integer userId){
        HogwartsUser foundUser = this.userService.findById(userId);
        UserDto userDto = this.userToUserDtoConverter.convert(foundUser);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", userDto);
    }

    /*
     * We are not using UserDto but User since we require password
     *
     * @param newUser
     * @return
     */
    @PostMapping
    public Result addUser(@Valid @RequestBody HogwartsUser newUser){
        HogwartsUser savedUser = this.userService.save(newUser);
        UserDto savedUserDto = this.userToUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedUserDto);
    }

    // We do not use this to change password
    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto){
        HogwartsUser update = this.userDtoToUserConverter.convert(userDto);
        HogwartsUser updatedUser = this.userService.update(userId, update);
        UserDto updatedUserDto = this.userToUserDtoConverter.convert(updatedUser);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable Integer userId){
        this.userService.delete(userId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }
}
