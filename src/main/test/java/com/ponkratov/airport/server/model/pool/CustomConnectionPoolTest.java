package com.ponkratov.airport.server.model.pool;

import com.ponkratov.airport.server.exception.PoolException;
import com.ponkratov.airport.server.view.ServerController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomConnectionPoolTest {
    ServerController controller = new ServerController();
    @Test
    void getConnection() throws PoolException {
        Assertions.assertNotNull(CustomConnectionPool.getInstance().getConnection());
    }
}