#ifndef ATM_H
#define ATM_H

#include <cstring>
#include <cstdio>
class ATM{
  char input[20];

  public:
  ATM();
  void showATM_Main();
  void showLogin_Page();
  void showRegister_Page();
  void showPersonalAccount();
  void showOption_Page();
  void ChangePersonalAccount();
};

#endif
