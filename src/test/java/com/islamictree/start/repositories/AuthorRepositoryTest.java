package com.islamictree.start.repositories;

import com.islamictree.start.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import reactor.test.StepVerifier;

@DataJpaTest(properties = "spring.main.web-application-type=none")
class AuthorRepositoryTest {
}