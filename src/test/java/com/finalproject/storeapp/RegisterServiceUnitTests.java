package com.finalproject.storeapp;

import com.finalproject.storeapp.model.User;
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

    @Test
    void registerSuccessTest() {
        AtomicReference<User> user = new AtomicReference<>();
        int random = ThreadLocalRandom.current().nextInt(0, 1000);

        assertThatCode(() -> user.set(this.registerService.register(
                new User("tempUser" + random + "@temp", "temptemp")
        ))).doesNotThrowAnyException();

        assertThat(user.get().getId()).isGreaterThan(0);
    }

    @Test
    void registerFailureTest() {
        Throwable throwable = catchThrowable(() -> this.registerService.register(
                new User("asdf@asdf", "asdfasdf")
        ));

        assertThat(throwable).isInstanceOf(Exception.class).hasMessageContaining("EXISTING_USER");
    }
}
