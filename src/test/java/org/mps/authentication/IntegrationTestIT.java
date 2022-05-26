package org.mps.authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class IntegrationTestIT {
    UserRegistration userRegistration;
    @Test
    public void UserRegistration_WhenRegisteringWithWrongDate_RegisterThrowsException(){
        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialValidator credentialValidator = Mockito.mock(CredentialValidator.class);
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);

        Mockito.when(date.validate()).thenReturn(false);
        Mockito.when(passwordString.validate()).thenReturn(true);
        Mockito.when(credentialStore.credentialExists(date,passwordString)).thenReturn(false);
        Mockito.when(credentialValidator.validate()).thenReturn(CredentialValidator.ValidationStatus.BIRTHDAY_INVALID);

        Assertions.assertThrows(RuntimeException.class, ()->{
            userRegistration.register(date,passwordString,credentialStore, credentialValidator);
        });
    }

    @Test
    public void UserRegistration_WhenRegisteringWithWrongPasswordString_RegisterThrowsException(){

        userRegistration = new UserRegistration();
        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);
        CredentialValidator credentialValidator = Mockito.mock(CredentialValidator.class);

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(passwordString.validate()).thenReturn(false);
        Mockito.when(credentialStore.credentialExists(date,passwordString)).thenReturn(false);
        Mockito.when(credentialValidator.validate()).thenReturn(CredentialValidator.ValidationStatus.BIRTHDAY_INVALID);


        Assertions.assertThrows(RuntimeException.class, () ->
                userRegistration.register(date, passwordString, credentialStore, credentialValidator));
    }

    @Test
    public void UserRegistration_WhenRegisteringWithNonExistingCredentials_ValidationReturnsVALIDATION_OK(){

    }

    @Test
    public void UserRegistration_WhenRegisteringWithExistingCredentials_ValidationReturnsEXISTING_CREDENTIAL(){


    }


}
