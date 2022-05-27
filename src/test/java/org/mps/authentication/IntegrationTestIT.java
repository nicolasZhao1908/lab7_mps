package org.mps.authentication;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class IntegrationTestIT {

    private UserRegistration userRegistration;
    private CredentialValidator credentialValidator;
    private Date date;
    private PasswordString passwordString;
    private CredentialStoreSet credentialStoreSet;

    @BeforeEach
    public void init(){
        userRegistration = new UserRegistration();
    }
    @AfterEach
    public void finish(){userRegistration = null;}

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

        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);
        CredentialValidator credentialValidator = Mockito.mock(CredentialValidator.class);

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(passwordString.validate()).thenReturn(false);
        Mockito.when(credentialStore.credentialExists(date,passwordString)).thenReturn(false);
        Mockito.when(credentialValidator.validate()).thenReturn(CredentialValidator.ValidationStatus.PASSWORD_INVALID);


        Assertions.assertThrows(RuntimeException.class, () ->
                userRegistration.register(date, passwordString, credentialStore, credentialValidator));
    }

    @Test
    public void UserRegistration_WhenRegisteringWithNonExistingCredentials_RegisterDoesNotThrowException(){

        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);
        CredentialValidator credentialValidator = Mockito.mock(CredentialValidator.class);

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(passwordString.validate()).thenReturn(true);
        Mockito.when(credentialStore.credentialExists(date,passwordString)).thenReturn(false);
        Mockito.when(credentialValidator.validate()).thenReturn(CredentialValidator.ValidationStatus.VALIDATION_OK);


        Assertions.assertDoesNotThrow(() -> userRegistration.register(date, passwordString, credentialStore, credentialValidator));
    }



    @Test
    public void UserRegistration_WhenRegisteringWithExistingCredentials_RegisterThrowsException(){

        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);
        CredentialValidator credentialValidator = Mockito.mock(CredentialValidator.class);

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(passwordString.validate()).thenReturn(true);
        Mockito.when(credentialStore.credentialExists(date,passwordString)).thenReturn(true);
        Mockito.when(credentialValidator.validate()).thenReturn(CredentialValidator.ValidationStatus.EXISTING_CREDENTIAL);

        Assertions.assertThrows(RuntimeException.class, () ->
                userRegistration.register(date, passwordString, credentialStore, credentialValidator));

    }


    @Test
    public void CredentialValidator_WhenRegisteringWithWrongDate_ValidateReturnsBIRTHDAY_INVALID(){

        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);

        credentialValidator = new CredentialValidator(date,passwordString,credentialStore);

        Mockito.when(date.validate()).thenReturn(false);
        Mockito.when(passwordString.validate()).thenReturn(true);
        Mockito.when(credentialStore.credentialExists(date,passwordString)).thenReturn(false);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.BIRTHDAY_INVALID, credentialValidator.validate());
    }

    @Test
    public void CredentialValidator_WhenRegisteringWithWrongPasswordString_ValidationReturnsPASSWORD_INVALID(){

        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);

        credentialValidator = new CredentialValidator(date,passwordString,credentialStore);

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(passwordString.validate()).thenReturn(false);
        Mockito.when(credentialStore.credentialExists(date,passwordString)).thenReturn(false);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.PASSWORD_INVALID, credentialValidator.validate());
    }

    @Test
    public void CredentialValidator_WhenRegisteringWithNonExistingCredentials_ValidationReturnsVALIDATION_OK(){

        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);

        credentialValidator = new CredentialValidator(date,passwordString,credentialStore);

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(passwordString.validate()).thenReturn(true);
        Mockito.when(credentialStore.credentialExists(date,passwordString)).thenReturn(false);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.VALIDATION_OK, credentialValidator.validate());
            }

    @Test
    public void CredentialValidator_WhenRegisteringWithExistingCredentials_ValidationReturnsEXISTING_CREDENTIAL(){

        Date date = Mockito.mock(Date.class);
        PasswordString passwordString = Mockito.mock(PasswordString.class);
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);

        credentialValidator = new CredentialValidator(date,passwordString,credentialStore);

        Mockito.when(date.validate()).thenReturn(true);
        Mockito.when(passwordString.validate()).thenReturn(true);
        Mockito.when(credentialStore.credentialExists(date,passwordString)).thenReturn(true);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.EXISTING_CREDENTIAL, credentialValidator.validate());
    }


    @Test
    public void WhenRegisteringWithWrongDate_ValidateReturnsBIRTHDAY_INVALID(){

        date = new Date(1,1,1);
        passwordString = new PasswordString("Tabien1?");
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);
        credentialValidator = new CredentialValidator(date,passwordString,credentialStore);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.BIRTHDAY_INVALID, credentialValidator.validate());
    }

    @Test
    public void WhenRegisteringWithWrongPasswordString_ValidateReturnsPASSWORD_INVALID(){

        date = new Date(1,1,2001);
        passwordString = new PasswordString("Tamal");
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);
        credentialValidator = new CredentialValidator(date,passwordString,credentialStore);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.PASSWORD_INVALID, credentialValidator.validate());
    }

    @Test
    public void WhenRegisteringWithNonExistingCredentials_ValidationReturnsVALIDATION_OK() {

        date = new Date(1, 1, 2001);
        passwordString = new PasswordString("Tabien1?");
        CredentialStore credentialStore = Mockito.mock(CredentialStore.class);
        credentialValidator = new CredentialValidator(date, passwordString, credentialStore);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.VALIDATION_OK, credentialValidator.validate());

    }
    @Test
    public void WhenRegisteringWithExistingCredentials_ValidationReturnsEXISTING_CREDENTIAL(){

        date = new Date(1,1,2001);
        passwordString = new PasswordString("Tabien1?");
        credentialStoreSet = Mockito.mock(CredentialStoreSet.class);

        when(credentialStoreSet.credentialExists(date, passwordString)).thenReturn(true);

        credentialValidator = new CredentialValidator(date,passwordString,credentialStoreSet);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.EXISTING_CREDENTIAL, credentialValidator.validate());
    }

    @Test
    public void CredentialStoreSet_WhenRegisteringWithWrongDate_ValidateReturnsBIRTHDAY_INVALID(){

        date = new Date(1,1,1);
        passwordString = new PasswordString("Tabien1?");
        credentialStoreSet = new CredentialStoreSet();
        credentialValidator = new CredentialValidator(date,passwordString,credentialStoreSet);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.BIRTHDAY_INVALID, credentialValidator.validate());
    }

    @Test
    public void CredentialStoreSet_WhenRegisteringWithWrongPasswordString_ValidationReturnsPASSWORD_INVALID(){

        date = new Date(1,1,2001);
        passwordString = new PasswordString("Tamal");
        credentialStoreSet = new CredentialStoreSet();
        credentialValidator = new CredentialValidator(date,passwordString,credentialStoreSet);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.PASSWORD_INVALID, credentialValidator.validate());
    }

    @Test
    public void CredentialStoreSet_WhenRegisteringWithNonExistingCredentials_ValidationReturnsVALIDATION_OK(){

        date = new Date(1,1,2001);
        passwordString = new PasswordString("Tabien1?");
        credentialStoreSet = new CredentialStoreSet();
        credentialValidator = new CredentialValidator(date,passwordString,credentialStoreSet);

        Assertions.assertEquals(CredentialValidator.ValidationStatus.VALIDATION_OK, credentialValidator.validate());
    }


    @Test
    public void CredentialStoreSet_WhenRegisteringWithExistingCredentials_ValidationReturnsEXISTING_CREDENTIAL(){

        date = new Date(1,1,2001);
        passwordString = new PasswordString("Tabien1?");
        credentialStoreSet = new CredentialStoreSet();
        credentialStoreSet.register(date, passwordString);
        credentialValidator = new CredentialValidator(date,passwordString,credentialStoreSet);
        Assertions.assertEquals(CredentialValidator.ValidationStatus.EXISTING_CREDENTIAL, credentialValidator.validate());
    }

    @Test
    public void CredentialStoreSet_WhenRegisteringWithCorrectCredentials_RegisterCredentials(){

        date = new Date(1,1,2001);
        passwordString = new PasswordString("Tabien1?");
        credentialStoreSet = new CredentialStoreSet();
        credentialStoreSet.register(date,passwordString);

        Assertions.assertTrue(credentialStoreSet.credentialExists(date,passwordString));
    }

    @Test
    public void CredentialStoreSet_WhenRegisteringWithExistingCredentials_RegisterThrowsCredentialExistsException(){

        date = new Date(1,1,2001);
        passwordString = new PasswordString("Tabien1?");
        credentialStoreSet = new CredentialStoreSet();
        credentialStoreSet.register(date,passwordString);
        credentialValidator = new CredentialValidator(date,passwordString,credentialStoreSet);

        Assertions.assertThrows(CredentialExistsException.class, ()->{
            credentialStoreSet.register(date, passwordString);
        });
    }
}
