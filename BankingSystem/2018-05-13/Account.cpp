#include <cstring>
#include "Account.h"

Account::Account(){

  //empty state
  transactionFee = 0;
  accountID      = nullptr;
  password       = nullptr;
  pinNum         = 0;

}

Account::Account(char*  newAccountId,
                 char*  newPassword,
                 int    newPinNum,
                 Types  newType){

  type           = newType;
  accountID      = nullptr;
  password       = nullptr;

  //using existed functions to reduce duplication
  changeAccountID(newAccountId);
  changePassword(newPassword);
  changePinNum(newPinNum); 
  setTransactionFee();

}

Account::~Account(){

  delete [] accountID;
  delete [] password;

}

Types Account::getType(){
  return type;
}

// When client try to login from the ATM, 
// it will login the account through this function
Account& Account::returnAccount(){
  return *this;
}

// Changing account id when the client want to change it
void Account::changeAccountID(char* newAccountId){

  delete [] accountID; 
  accountID = new char [strlen(newAccountId + 1)];
  strcpy(accountID,newAccountId);

}

// Changing client password  when the client want to change it
void Account::changePassword(char* newPassword){

  delete [] password;
  password = new char [strlen(newPassword + 1)];
  strcpy(password,newPassword);

}

// Changing client pin number when the client want to change it
void Account::changePinNum(int newPinNum){
  pinNum = newPinNum;
}

// All accounts have different transaction fee depend on the banking type
//      +----------------------------+
//      |   Transaction  Fee         |
//      |----------------------------|
//      |      adult   = 1.5         |
//      |      senior  = 1.0         |
//      |      student = 0.5         |
//      +----------------------------+

void Account::setTransactionFee(){
  if(type == Types::Adult){
    transactionFee = 1.5;
  }
  else if(type == Types::Senior){
    transactionFee = 1;
  }
  else{

    //type == Types::Student
    transactionFee = 0.5;
  }
}

bool Account::isEmpty()const{

  return (transactionFee == 0       &&
          accountID      == nullptr &&
          password       == nullptr &&
          pinNum         == 0);

}

// When the client is trying to login from the ATM, it will check if it is this account
bool Account::isThisAcc(char* acc, char* pwd, int pNum){
  return (strcmp(accountID,acc) == 0 && strcmp(password,pwd) == 0 && pinNum == pNum);
}

//wipe account information when the client is going to remove banking account
void Account::clearAcc(){
  delete [] accountID;
  delete [] password;
  transactionFee = 0;
  pinNum         = 0;
  accountID = nullptr;
  password  = nullptr;
}

