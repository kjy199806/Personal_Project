#ifndef ACCOUNT_H
#define ACCOUNT_H

class Account{

  // Account members           
  double transactionFee;  //--------------------------
  char*  accountID:       //  Type       transactionFee 
  char*  password;        //  1 = Adult    =  $1                              
  int    pinNum;          //  2 = Senior   =  $0.5                             
  int    type;            //  3 = Student  =  Free   
                          //--------------------------
                                                                    
  public:
  Account();
  Account(char*  accountId,
          char*  password,
          int    pinNum,
          int    type);
  ~Account();
  int getType();
  Account& returnAccount();
  void changeAccountID();
  void changePassword();
  void changePinNum();
}

#endif
