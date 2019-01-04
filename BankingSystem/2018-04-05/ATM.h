#include <cstring>
#include <cstdio>
class ATM{
  char input[20];

  public:
  ATM(){
    memset(input,'\0',sizeof(input));
  }
  void showATM_Main() {
    int customerInputOption = -1;
    while(customerInputOption != 0){
      printf("\
              ATM Main Page          \n\
                                     \n\
              1. Login Page          \n\
              2. Register Page       \n\
                                     \n");
      scanf("%d", &customerInputOption);
      switch(customerInputOption){
        case 1:
          showLogin_Page();
          break;
        case 2:
          showRegister_Page();
          break;
        default:
          printf("Wrong input\n");
      }
    }
  }

  void showLogin_Page() {
    int customerInputOption = -1;
    while(customerInputOption != 0){
      printf("\
              Login Page             \n\
                                     \n\
              Account: ");
      scanf("%s",input);
      printf("Password: ");
      scanf("%s",input);
      showPersonalAccount();
    }
  }

  void showPersonalAccount() {
    int customerInputOption = -1;
    while(customerInputOption != 0){
      printf("\
            Personal Account Page          \n\
                                           \n\
            1. Banking Option              \n\
            2. Change Personal Information \n\
            0. Exit                        \n\
                                           \n");
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
          printf("Wrong input\n");
      } 
    }
  }




  void showRegister_Page() const{}
  void ChangePersonalAccount() const{}
  void showOption_Page() const{}
};
