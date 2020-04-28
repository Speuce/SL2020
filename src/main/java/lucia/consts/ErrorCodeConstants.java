package main.java.lucia.consts;

/**
 * An enumerated type containing the different error codes
 * that will be shown if the program encounters a fatal error
 * which results in the program's security or stability being
 * severely compromised.
 *
 * @author Brett Downey
 */
public enum ErrorCodeConstants {

    /**
     * A encryption error which may occur if
     * the encryption algorithm fails to initiate.
     * This can happen if an incorrect algorithm is
     * used for encryption. The current chosen RSA and
     * AES encryption is the current (2019) industry standard
     * for secure encryption, unless you know what you are doing,
     * do not attempt to change the algorithms chosen.
     */
    ENCRYPTION_ERROR(205),

    /**
     * A decryption error which may occur if
     * the decryption algorithm fails to initiate.
     * This can happen if an incorrect algorithm is
     * used for decryption. The current chosen RSA and
     * AES encryption is the current (2019) industry standard
     * for secure encryption, unless you know what you are doing,
     * do not attempt to change the algorithms chosen.
     */
    DECRYPTION_ERROR(206),

    /**
     * The error code which occurs if an engine failure
     * happens.
     */
    ENGINE_FAILURE(55);

    /**
     * The error code that would be displayed for the
     * corresponding error type.
     */
    private final int code;

    ErrorCodeConstants(int code) {
        this.code = code;
    }

    /**
     * Retrieves the given code for the error type
     *
     * @return The code error within int form
     */
    public int getCode() {
        return code;
    }
}