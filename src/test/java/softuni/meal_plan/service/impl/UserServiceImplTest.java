package softuni.meal_plan.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import softuni.meal_plan.model.entity.Role;
import softuni.meal_plan.model.entity.User;
import softuni.meal_plan.model.service.UserServiceModel;
import softuni.meal_plan.repository.RoleRepository;
import softuni.meal_plan.repository.UserRepository;
import softuni.meal_plan.service.RoleService;
import softuni.meal_plan.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class UserServiceImplTest {

    public static final String TEST_USER_JUST_NAME = "testuser";
    public static final String TEST_USER_ID = "testUserId";
    public static final String TEST_USER_EMAIL = "testuser@test";
    public static final String TEST_USER_ADDRESS = "Address";
    public static final String TEST_USER_NAME = "TestUser";
    public static final String TEST_USER_PASS = "testpass";
    private UserRepository userRepositoryMock;
    private RoleRepository roleRepositoryMock;
    private User testUser;

    @BeforeEach
    void init() {
        this.testUser = new User();
        this.testUser.setId(TEST_USER_ID);
        this.testUser.setName(TEST_USER_JUST_NAME);
        this.testUser.setAddress(TEST_USER_ADDRESS);
        this.testUser.setEmail(TEST_USER_EMAIL);
        this.testUser.setUsername(TEST_USER_NAME);
        this.testUser.setPassword(TEST_USER_PASS);

        Role roleUser = new Role();
        roleUser.setAuthority("ROLE_USER");

        Set<Role> authoritiesRoleSet = new HashSet<>();
        authoritiesRoleSet.add(roleUser);

        this.testUser.setAuthorities(authoritiesRoleSet);
        this.userRepositoryMock = Mockito.mock(UserRepository.class);
        this.roleRepositoryMock = Mockito.mock(RoleRepository.class);
    }

    @Test
    void registerUserFirstUserEver() {
        Mockito.when(roleRepositoryMock.count()).thenReturn(0L); // simulate no roles existing in db yet
        Mockito.when(userRepositoryMock.count()).thenReturn(0L); // simulate no users existing in db yet
        Mockito.when(userRepositoryMock.saveAndFlush(Mockito.any(User.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        RoleService roleService = new RoleServiceImpl(roleRepositoryMock, new ModelMapper());
        UserService userService = new UserServiceImpl(userRepositoryMock, roleService, new ModelMapper(), new BCryptPasswordEncoder());

        // act
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername(TEST_USER_NAME);
        userServiceModel.setEmail(TEST_USER_EMAIL);
        userServiceModel.setPassword(TEST_USER_PASS);
        userServiceModel.setId(TEST_USER_ID);
        userServiceModel.setAddress(TEST_USER_ADDRESS);
        userServiceModel.setName(TEST_USER_JUST_NAME);

        UserServiceModel userServiceModelResult = userService.registerUser(userServiceModel);

        // assert
        Mockito.verify(roleRepositoryMock, Mockito.times(3)).saveAndFlush(Mockito.any(Role.class));
        Mockito.verify(userRepositoryMock, Mockito.times(1)).saveAndFlush(Mockito.any(User.class));
        Assert.assertEquals("Mismatch... ", userServiceModel.getUsername(), userServiceModelResult.getUsername());
        Assert.assertNotEquals("Password not encrypted...", userServiceModel.getPassword(), userServiceModelResult.getPassword());
        Assert.assertNotNull("Null... ", userServiceModelResult.getAuthorities());
    }

    @Test
    void registerUser() {
        Mockito.when(roleRepositoryMock.findByAndAuthority("ROLE_USER")).thenReturn(new Role("ROLE_USER"));
        Mockito.when(roleRepositoryMock.count()).thenReturn(3L); // simulate 3 roles existing
        Mockito.when(userRepositoryMock.count()).thenReturn(1L); // simulate 1 user existing
        Mockito.when(userRepositoryMock.saveAndFlush(Mockito.any(User.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
        RoleService roleService = new RoleServiceImpl(roleRepositoryMock, new ModelMapper());
        UserService userService = new UserServiceImpl(userRepositoryMock, roleService, new ModelMapper(), new BCryptPasswordEncoder());

        // act
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername(TEST_USER_NAME);
        userServiceModel.setEmail(TEST_USER_EMAIL);
        userServiceModel.setPassword(TEST_USER_PASS);
        userServiceModel.setId(TEST_USER_ID);
        userServiceModel.setAddress(TEST_USER_ADDRESS);
        userServiceModel.setName(TEST_USER_JUST_NAME);

        UserServiceModel userServiceModelResult = userService.registerUser(userServiceModel);

        // assert
        Mockito.verify(roleRepositoryMock, Mockito.times(0)).saveAndFlush(Mockito.any(Role.class));
        Mockito.verify(userRepositoryMock, Mockito.times(1)).saveAndFlush(Mockito.any(User.class));
        Assert.assertEquals("Mismatch... ", userServiceModel.getUsername(), userServiceModelResult.getUsername());
        Assert.assertNotEquals("Password not encrypted...", userServiceModel.getPassword(), userServiceModelResult.getPassword());
        Assert.assertNotNull("Null... ", userServiceModelResult.getAuthorities());
    }

    @Test
    void loadUserByUsername() {
        Mockito.when(this.userRepositoryMock.findByUsername(TEST_USER_NAME))
                .thenReturn(Optional.ofNullable(this.testUser));
        RoleService roleService = new RoleServiceImpl(roleRepositoryMock, new ModelMapper());
        UserService userService = new UserServiceImpl(userRepositoryMock, roleService, new ModelMapper(), new BCryptPasswordEncoder());

        // Act
        UserDetails actual = userService.loadUserByUsername(TEST_USER_NAME);

        // Assert
        Assert.assertEquals("Mismatch... ", TEST_USER_NAME, actual.getUsername());
    }

    @Test
    void findUserByUserName() {
    }

    @Test
    void findUserById() {
        Mockito.when(this.userRepositoryMock.findById(TEST_USER_ID))
                .thenReturn(Optional.ofNullable(this.testUser));
        RoleService roleService = new RoleServiceImpl(roleRepositoryMock, new ModelMapper());
        UserService userService = new UserServiceImpl(userRepositoryMock, roleService, new ModelMapper(), new BCryptPasswordEncoder());

        // Act
        UserServiceModel actual = userService.findUserById(TEST_USER_ID);

        // Assert
        Assert.assertEquals("Mismatch... ", TEST_USER_ID, actual.getId());
    }

    @Test
    void editUserProfile() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void deleteUser() {
        Mockito.when(this.userRepositoryMock.findById(TEST_USER_ID))
                .thenReturn(Optional.ofNullable(this.testUser));
        RoleService roleService = new RoleServiceImpl(roleRepositoryMock, new ModelMapper());
        UserService userService = new UserServiceImpl(userRepositoryMock, roleService, new ModelMapper(), new BCryptPasswordEncoder());

        // Act
        userService.deleteUser(TEST_USER_ID);

        // Assert
        Mockito.verify(userRepositoryMock, Mockito.times(1)).delete(testUser);
    }

    @Test
    void makeAdmin() {
    }

    @Test
    void makeUser() {
    }

    @Test
    void register() {
    }

    @Test
    void findByUsername() {
        Mockito.when(this.userRepositoryMock.findByUsername(TEST_USER_NAME))
                .thenReturn(Optional.ofNullable(this.testUser));
        RoleService roleService = new RoleServiceImpl(roleRepositoryMock, new ModelMapper());
        UserService userService = new UserServiceImpl(userRepositoryMock, roleService, new ModelMapper(), new BCryptPasswordEncoder());

        // Act
        UserServiceModel actual = userService.findByUsername(TEST_USER_NAME);

        // Assert
        Assert.assertEquals("Mismatch... ", TEST_USER_NAME, actual.getUsername());
    }

    @Test
    void findUserByEmail() {
        Mockito.when(this.userRepositoryMock.findUserByEmail(TEST_USER_EMAIL))
                .thenReturn(Optional.ofNullable(this.testUser));
        RoleService roleService = new RoleServiceImpl(roleRepositoryMock, new ModelMapper());
        UserService userService = new UserServiceImpl(userRepositoryMock, roleService, new ModelMapper(), new BCryptPasswordEncoder());

        // Act
        UserServiceModel actual = userService.findUserByEmail(TEST_USER_EMAIL);

        // Assert
        Assert.assertEquals("Mismatch... ", TEST_USER_EMAIL, actual.getEmail());
    }
}