package com.aston_rest_api.dao.daoimpl;

import com.aston_rest_api.db.ConnectionManagerImpl;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    public static final MySQLContainer container=
            new MySQLContainer("mysql:8.0")
                    .withUsername("test")
                    .withPassword("test")
                    .withDatabaseName("test");

    ConnectionManagerImpl connectionManager=ConnectionManagerImpl.getInstance();


    static void init(){
        container.start();
    }
   @AfterAll
   static void stop(){
        container.stop();
   }
   @BeforeEach
   void setUp(){

   }







    @Test
    void insert() throws SQLException {
        Connection connection=connectionManager.getConnection();




    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void findUserByLoginAndPassword() {
    }

    @Test
    void findUserPurchases() {
    }
}