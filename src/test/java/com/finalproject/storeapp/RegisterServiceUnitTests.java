package com.finalproject.storeapp;

import com.finalproject.storeapp.core.exceptions.ExistingUserException;
import com.finalproject.storeapp.core.exceptions.NotFoundException;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.repository.UserRepository;
import com.finalproject.storeapp.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class RegisterServiceUnitTests {

    private final RegisterService registerService;
    private final UserRepository userRepository;

    @Test
    void registerSuccessTest() {
        AtomicReference<User> user = new AtomicReference<>();
        int random = ThreadLocalRandom.current().nextInt(0, 1000);

        assertThatCode(() -> user.set(registerService.register(
                new User("tempUser" + random + "@temp", "temptemp")
        ))).doesNotThrowAnyException();

        assertThat(user.get().getId()).isGreaterThan(0);
    }

    @Test
    void registerFailureTest() {
        AtomicReference<User> user = new AtomicReference<>();

        assertThatCode(() -> user.set(
                userRepository.findOneByEmail("asdf@asdf").orElseThrow(NotFoundException::new)
        )).doesNotThrowAnyException();

        Throwable throwable = catchThrowable(() -> registerService.register(
                new User("asdf@asdf", "asdfasdf")
        ));

        assertThat(throwable).isInstanceOf(ExistingUserException.class).hasMessageContaining("EXISTING_USER");
    }
}
