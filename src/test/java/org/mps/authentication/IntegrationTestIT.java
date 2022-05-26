package org.mps.authentication;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class IntegrationTestIT {
    UserRegistration userRegistration;
    @Test
    public void UserRegistration_WhenRegisteringWithWrongDate_ValidationReturnsBIRTHDAY_INVALID(){
        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialValidator credentialValidator = Mockito.mock(CredentialValidator.class);
        //CredentialStore credentialStore = Mockito.mock(CredentialStore.class);

        Mockito.when(date.validate()).thenReturn(false);

    }

    @Test
    public void UserRegistration_WhenRegisteringWithWrongPasswordString_ValidationReturnsPASSWORD_INVALID(){

    }

    @Test
    public void UserRegistration_WhenRegisteringWithNonExistingCredentials_ValidationReturnsVALIDATION_OK(){

    }

    @Test
    public void UserRegistration_WhenRegisteringWithExistingCredentials_ValidationReturnsEXISTING_CREDENTIAL(){

    }


}
