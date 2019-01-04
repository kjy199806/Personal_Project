#ifndef ACCOUNT_H
#define ACCOUNT_H

enum class Types{
  Adult,
  Senior,
  Student
};

class Account{

  // Account members           
  double transactionFee;  
  char*  accountID;       
  char*  password;  
  int    pinNum;    
  Types  type;      
                    
                                                                    
  public:
  Account();
  Account(char*  newAccountId,
          char*  newPassword,
          int    newPinNum,
          Types  newType);
  ~Account();
  Types getType();
  Account& returnAccount();
  void changeAccountID(char* newAccountId);
  void changePassword(char* newPassword);
  void changePinNum(int newPinNum);
  void setTransactionFee();
  bool isThisAcc(char* acc, char* pwd, int pNum);
  void clearAcc();
};

#endif
