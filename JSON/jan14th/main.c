#include <stdio.h>
#include <string.h>
#include "JSON.h"
int main(int argc, char* argv[]){
  FILE* a;
  for(int i = 2; i<argc; i++){
    a = fopen(*(argv+i), "r");
    int returnCha;
    int isFinish= 0;
    char returnArray[10000] = {0};
    for(int i = 0; !isFinish; i++){
      isFinish = 0;
      returnCha = fgetc(a);
      if(returnCha == EOF){
        if(argv[1][0] == 'y' || *argv[1] == 'Y'){
          validTestData(returnArray);
        }
        else{
          invalidTestData(returnArray);
        }
        isFinish = 1;
        memset(returnArray,0,sizeof(returnArray));
      }
      else{
        returnArray[i] = returnCha;
      }
    }
  }
}
