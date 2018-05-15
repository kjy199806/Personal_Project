#include <cstring>
#include "Account.h"

Account::Account(){

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

Account& Account::returnAccount(){
  return *this;
}

void Account::changeAccountID(char* newAccountId){

  delete [] accountID; 
  accountID = new char [strlen(newAccountId + 1)];
  strcpy(accountID,newAccountId);

}

void Account::changePassword(char* newPassword){

  delete [] password;
  password = new char [strlen(newPassword + 1)];
  strcpy(password,newPassword);

}

void Account::changePinNum(int newPinNum){
  pinNum = newPinNum;
}
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
