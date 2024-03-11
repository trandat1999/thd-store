package com.thd.store.util;

public class SystemMessage {
    //message properties
    public static final String SUCCESS = "store.message.success";
    public static final String BAD_REQUEST = "store.message.badRequest";
    public static final String USER_IS_DISABLE = "store.message.userIsDisabled";
    public static final String USER_IS_EXPIRED = "store.message.userIsExpired";
    public static final String USER_CREDENTIALS_IS_EXPIRED = "store.message.userCredentialsIsExpired";
    public static final String USER_IS_LOCKED = "store.message.userIsLocked";
    public static final String USER_BAD_CREDENTIALS = "store.message.usernamePasswordIsInvalid";
    public static final String JWT_IS_EXPIRED = "store.message.jwtIsExpired";
    public static final String JWT_IS_INVALID = "store.message.jwtIsInvalid";
    public static final String ACCESS_DENIED = "store.message.accessDenied";
    public static final String UNAUTHORIZED = "store.message.unauthorized";
    public static final String FORBIDDEN = "store.message.forbidden";
    public static final String VALUE_EXIST = "store.message.valueExist";
    public static final String REGISTER_SUCCESS = "store.message.registerSuccess";
    public static final String SEND_MAIL_ERROR = "store.message.sendMailError";
    public static final String TOKEN_INVALID = "store.message.tokenInvalid";
    public static final String TOKEN_EXPIRED = "store.message.tokenExpired";
    public static final String VERIFY_SUCCESS = "store.message.verifySuccess";
    public static final String NOT_FOUND = "store.message.notFound";
    public static final String ACTIVATED = "store.message.activated";
    public static final String GENERATE_TOKEN_SUCCESS = "store.message.generateTokenSuccess";
    public static final String TWO_FIELD_NOT_MATCH = "store.message.twoFieldNotMatch";
    public static final String CONTENT_SUCCESS_FORGOT_PASS= "store.message.successForgotPass";
    public static final String ALREADY_SEND_FORGOT_PASS= "store.message.alreadySendForgotPass";
    public static final String LINK_EXPIRED= "store.message.linkAlreadyExpired";
    public static final String ACTIVE_NEW_PASS_SUCCESS= "store.message.activeNewPassSuccess";
    public static final String FILE_NAME_INVALID = "store.message.fileNameInvalid";
    public static final String WRITE_FILE_ERROR = "store.message.writeFileError";

    // validation
    public static final String VALIDATION_NOTNULL = "{store.validation.NotNull}";
    public static final String VALIDATION_NOTNULL_SV = "store.validation.NotNull";
    public static final String VALIDATION_NOT_BLANK = "{store.validation.NotBlank}";
    public static final String VALIDATION_EMAIL = "{store.validation.email}";
    public static final String VALIDATION_MIN_LENGTH = "{store.validation.MinLength}";
    public static final String VALIDATION_USERNAME_PATTERN = "{store.validation.usernamePattern}";
    public static final String VALIDATION_FIELD_MATCH = "{store.validation.fieldMatch}";

    // message application
    public static final String CONTENT_MAIL_REGISTER = "Thanks for registering your account. Link activity will be expired after 5 minutes";
    public static final String SUBJECT_MAIL_REGISTER = "Verify your email address";
    public static final String SUBJECT_MAIL_FORGOT = "Reset your password";
    public static final String CONTENT_MAIL_FORGOT = "Please active your new password. Link activity will be expired after 5 minutes";
}
