/* BadAccountException.java */

/**
 *  Implements an exception that should be thrown for nonexistent accounts.
 **/
public class BadTransactionException extends Exception {

  public int withdrawNumber;  // The invalid withdraw number.

  /**
   *  Creates an exception object for nonexistent account "badAcctNumber".
   **/
  public BadTransactionException(int badWithdrawNumber) {
    super("Invalid withdraw number: " + badWithdrawNumber);

    withdrawNumber = badWithdrawNumber;
  }
}
