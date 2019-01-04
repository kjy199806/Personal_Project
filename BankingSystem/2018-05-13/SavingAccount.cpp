#include "Account.h"
#include "SavingAccount.h"

//empty account
SavingAccount::SavingAccount(): Account(){
  balance = 0;
}

//creating new saving account 
SavingAccount::SavingAccount(char*  newAccountId,
                             char*  newPassword,
                             int    newPinNum,
                             Types  newType,
                             double newBalance): Account(newAccountId,newPassword,
                                                          newPinNum,newType){

  balance = newBalance;

}

// check the current balance and if it is not below 0 it will do the the + / -
void SavingAccount::setBalance(double money){
  if(isValidBalance(money)){
    balance += money;
  }
}

bool SavingAccount::isValidBalance(double money){
  return (balance + money) >= 0; 
}

// wipe the account information
void SavingAccount::clearAcc(){
  Account::clearAcc();
  balannce = 0;
}

//copy the saving account information to this account
SavingAccount& SavingAccount::operator=(const SavingAccount& other){

  //checking if these two accounts are not same accounts
  if(this != &other){
    transactionFee = other.transactionFee;  
    pinNum         = other.pinNum;    
    type           = other.type;      
    balance        = other.balance;

    delete [] accountID;
    delete [] password;  

    accountID = new char [strlen(other.AccountID + 1)];
    strcpy(accountID,other.AccountID);

    password = new char [strlen(other.password + 1)];
    strcpy(password,other.password);

  }
  return *this;
}
