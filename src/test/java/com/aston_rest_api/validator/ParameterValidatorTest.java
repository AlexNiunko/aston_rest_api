package com.aston_rest_api.validator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParameterValidatorTest {
    private static ParameterValidator validator;
    @BeforeAll
    static  void init(){
        validator=ParameterValidator.getInstance();
    }
    @AfterAll
   static void destroy(){
        validator=null;
    }

    @Test
    void validateCorrectDate() {
        String date="2012-05-12";
        Assertions.assertTrue(validator.validateDate(date));
    }
    @Test
    void validateIncorrectDate() {
        String date="20122-05-12";
        Assertions.assertFalse(validator.validateDate(date));
    }

    @Test
    void validateCorrectNumber() {
        String number="123";
        Assertions.assertTrue(validator.validateNumber(number));
    }
    @Test
    void validateIncorrectNumber() {
        String number="1f2r3";
        Assertions.assertFalse(validator.validateNumber(number));
    }

    @Test
    void validateCorrectNameOrSurname() {
        String name="Mikhail";
        Assertions.assertTrue(validator.validateNameOrSurname(name));
    }
    @Test
    void validateIncorrectNameOrSurname() {
        String name="Mike345ha&il";
        Assertions.assertFalse(validator.validateNameOrSurname(name));
    }
    @Test
    void validateCorrectPassword() {
        String password="asrg346";
        Assertions.assertTrue(validator.validatePassword(password));
    }
    @Test
    void validateIncorrectPassword() {
        String password="";
        Assertions.assertFalse(validator.validatePassword(password));
    }

    @Test
    void validateCorrectEmail() {
        String email="alexniunko89@gmail.com";
        Assertions.assertTrue(validator.validateEmail(email));
    }
    @Test
    void validateIncorrectEmail() {
        String email="alexniunko89gmail.com";
        Assertions.assertFalse(validator.validateEmail(email));
    }
}