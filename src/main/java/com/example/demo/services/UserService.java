    package com.example.demo.services;

    import com.example.demo.exceptions.UserNotFoundException;
    import com.example.demo.repository.User;
    import com.example.demo.repository.UserRepository;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.time.Period;
    import java.util.List;
    import java.util.Optional;

    @Service
    public class UserService {

        private final UserRepository userRepository;

        public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        public User createUser(User user) {
            Optional<User> optionalUser = userRepository.findById(user.getId());
            if (!optionalUser.isPresent()) {
                throw new UserNotFoundException("No user with id " + user.getId() + " found");
            }

            user.setAge(Period.between(user.getBirthday(), LocalDate.now()).getYears());
            return userRepository.save(user);
        }

        public void deleteUser(Long id) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (!optionalUser.isPresent()) {
                throw new IllegalStateException("A user with this id " + id + " exists");
            }

            userRepository.deleteById(id);
        }

        public User updateUser(Long id, String name, String email) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (!optionalUser.isPresent()) {
                throw new IllegalStateException("No user with id " + id + " found");
            }

            User user = optionalUser.get();

            if (email != null && !email.equals(user.getEmail())) {
                Optional<User> foundByEmail = userRepository.findByEmail(email);
                if (foundByEmail.isPresent()) {
                    throw new IllegalStateException("This email already exists");
                }

                user.setEmail(email);
            }

            if (name != null && !name.equals(user.getName())) {
                user.setName(name);
            }

            return userRepository.save(user);
        }
    }
