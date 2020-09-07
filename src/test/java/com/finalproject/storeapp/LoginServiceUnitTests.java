package com.finalproject.storeapp;

import com.finalproject.storeapp.core.exceptions.NotFoundException;
import com.finalproject.storeapp.model.User;
import com.finalproject.storeapp.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class LoginServiceUnitTests {

    private final LoginService loginService;

    @Test
    void authenticateSuccessTest() {
        AtomicReference<User> user = new AtomicReference<>();

        assertThatCode(() ->
                user.set(loginService.authenticate(
                        new User("asdf@asdf", "asdfasdf")
                ))
        ).doesNotThrowAnyException();

        assertThat(user.get().getId()).isEqualTo(2);
    }

    @Test
    void authenticateFailureTest() {
        Throwable throwable = catchThrowable(() -> loginService.authenticate(
                new User("qwer@qwer", "qwerqwer")
        ));

        assertThat(throwable).isInstanceOf(NotFoundException.class).hasMessageContaining("NOT_FOUND");
    }
}
