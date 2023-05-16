package ru.rossilman.lab3.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.rossilman.weblab3.entity.Result;
import ru.rossilman.weblab3.storage.ResultStorage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Rossilman on 19/04/2023
 */
@Timeout(2)
class ResultStorageTest {

    static ResultStorage storage;

    @BeforeAll
    static void initDatabase() {
        storage = new ResultStorage();
    }

    @Test
    void checkIfResultAddsToTheDatabase() {
        Random random = ThreadLocalRandom.current();
        Result result = new Result(
                random.nextInt(),
                random.nextInt(),
                random.nextInt(),
                false,
                random.nextLong()
        );
        storage.addResult(result);
        List<Result> results = storage.getAllResults();
        assertEquals(result, results.get(results.size() - 1));
    }
}
