#include "ATM.h"

ATM::ATM(){
  memset(input,'\0',sizeof(input));
}

void ATM::showATM_Main() {
  int customerInputOption = -1;
  while(customerInputOption != 0){
    printf("\
        +--------------------------------+\n\
        |       ATM Main Page            |\n\
        |                                |\n\
        |       1. Login Page            |\n\
        |       2. Register Page         |\n\
        |       0. Exit                  |\n\
        +--------------------------------+\n\
        \n\
        \n\
        Option: ");
    scanf("%d", &customerInputOption);
    switch(customerInputOption){
      case 0:
        printf("Thank you for using our ATM!\n");
        break;
      case 1:
        showLogin_Page();
        break;
      case 2:
        showRegister_Page();
        break;
      default:
        printf("\
            +--------------------------------+\n\
            |                                |\n\
            |         Wrong input            |\n\
            |                                |\n\
            +--------------------------------+\n\
            \n");
    }
  }
}

void ATM::showLogin_Page() {
  int customerInputOption = -1;
  printf("\
      +--------------------------------+\n\
      |           Login Page           |\n\
      |                                |\n\
      |  Enter Account and Password    |\n\
      |                                |\n\
      +--------------------------------+\n\
      \n\
      \n");
  printf("                         Account: ");
  scanf("%s",input);
  printf("                         Password: ");
  scanf("%s",input);
  showPersonalAccount();
}

void ATM::showRegister_Page(){
  printf("\
  +--------------------------------+\n\
  |         Register Page          |\n\
  |                                |\n\
  |  Enter Account and Password    |\n\
  |                                |\n\
  +--------------------------------+\n\
                                    \n");
  printf("                        Account: ");
  scanf("%s",input);
  printf("                        Password: ");
  scanf("%s",input);
}

void ATM::showPersonalAccount() {
  int customerInputOption = -1;
  while(customerInputOption != 0){
    printf("\
    +--------------------------------+\n\
    |     Personal Account Page      |\n\
    |                                |\n\
    |  1. Banking Option             |\n\
    |  2. Change Personal Information|\n\
    |  0. Sign out                   |\n\
    |                                |\n\
    +--------------------------------+\n\
                                      \n\
                                      \n\
                             Option: ");
    scanf("%d", &customerInputOption);
    switch(customerInputOption){
      case 0:
        break;
      case 1:
        showOption_Page();
        break;
      case 2:
        ChangePersonalAccount();
        break;
      default:
        printf("\
    +--------------------------------+\n\
    |                                |\n\
    |         Wrong input            |\n\
    |                                |\n\
    +--------------------------------+\n\
                                     \n");
    } 
  }
}

void ATM::showOption_Page(){
  int customerInputOption = -1;
  while(customerInputOption != 0){
    printf("\
    +--------------------------------+\n\
    |         Banking Option         |\n\
    |                                |\n\
    |  1. Desposite                  |\n\
    |  2. Withdraw                   |\n\
    |  3. Transfer                   |\n\
    |  4. Loan                       |\n\
    |  0. Exit                       |\n\
    |                                |\n\
    +--------------------------------+\n\
                                      \n\
                                      \n\
                             Option: ");
    scanf("%d", &customerInputOption);
    switch(customerInputOption){
      case 0:
        break;
      case 1:
        break;
      case 2:
        break;
      case 3:
        break;
      case 4:
        break;
      default:
        printf("\
    +--------------------------------+\n\
    |                                |\n\
    |         Wrong input            |\n\
    |                                |\n\
    +--------------------------------+\n\
                                     \n");
    }
  }
}

void ATM::ChangePersonalAccount(){
  int customerInputOption = -1;
  while(customerInputOption != 0){
    printf("\
    +--------------------------------+\n\
    |    Change Personal Account     |\n\
    |                                |\n\
    |  1. Change Account             |\n\
    |  2. Change Password            |\n\
    |  3. Change Pin Number          |\n\
    |  0. Exit                       |\n\
    |                                |\n\
    +--------------------------------+\n\
                                      \n\
                                      \n\
                             Option: ");
    scanf("%d", &customerInputOption);
    switch(customerInputOption){
      case 0:
        break;
      case 1:
        break;
      case 2:
        break;
      case 3:
        break;
      default:
        printf("\
    +--------------------------------+\n\
    |                                |\n\
    |         Wrong input            |\n\
    |                                |\n\
    +--------------------------------+\n\
                                     \n");
    }
  }
}


