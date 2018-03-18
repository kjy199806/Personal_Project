#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "stack.h"

//int MAXSIZE = 8;       
//int stack[8];     
//int top = -1;            



int skipSpace(char* json,int * i){
  int j;
  for(j = 0; j<strlen(json) && json[*i+j] == ' '; j++){ 
  }
  *i += j;
}

int isValidJson(char* json){
  struct stack_t s1;
  init(&s1, 10);
  for (int i =0; i<strlen(json) && json[i] != '\0'; i++){
    skipSpace(json,&i);

    // process \{
    if ( json[i] == '{' ){
      push(&s1, 1);
      i++;
    }
    skipSpace(json,&i);


    //------------------------------------------
    // Could be String, \{, or \}
    //------------------------------------------

    //------------------------------------------
    // Beg of String
    //------------------------------------------
    if ( json[i] == '"' ){

      // Process String
      i++; 
      //while( json[i] != '"'  && json[i] != '\0' )\{
      while( json[i] != '"'  && json[i] != '\0' ){
        i++;
      }
      if ( json[i] == '"' ) { i++; }

      skipSpace(json,&i);

      // process :
      if ( json[i] == ':' ){
        i++;
      }
      else {
        return 0; // false
      }
      skipSpace(json,&i);


      //---------------------------------------
      // Could be Object or Number
      //---------------------------------------

      //---------------------------------------
      // Beg of Object
      //---------------------------------------
      if ( json[i] == '{' ) {                         // process object
        i--;
      }
      //---------------------------------------
      // Beg of Number
      //---------------------------------------
      else {
        if ( '0' <= json[i] && json[i] <= '9' ){      // process number
          while ( '0' <= json[i] && json[i] <= '9' ){
            i++;
          }
        }
        else  {
          return 0;
        }

        if ( json[i] == '}' ) {                       // process \}
          if (isempty(&s1)){
            return 0;
          }
          else {
            pop(&s1);
          }
        } 
        else {
          return 0;
        }
      }
      skipSpace(json,&i);
    }
    //------------------------------------------
    // \}
    //------------------------------------------
    else if ( json[i] == '}' ) {                       // process \}

      while( json[i] == '}' ){
        if (isempty(&s1)){
          return 0;
        }
        else {
          pop(&s1);
        }
        i++;
        skipSpace(json,&i);
      }
    } 
    //------------------------------------------
    // \{
    //------------------------------------------
    else if ( json[i] == '{' ) {
      i--;
    }
    //------------------------------------------
    // Not String, \{, or \} 
    //------------------------------------------
    else {
      return 0;
    }
    skipSpace(json,&i);

    
  } // end of for


  if ( isempty(&s1) ){
    return 1;
  }
  else {
    return 0;

  } 
  //delete(&s1); // TODO: Where to put?
}

int main() {
  struct stack_t s2;
  init(&s2, 100);
  // Valid test
  printf("Valid test\n");
  char j1[] = "{}";                                               printf("%-50s: - %s\n", j1, isValidJson(j1) == 1? "pass" : "fail");
  char j2[] = "{\"abc\":123}" ;                                   printf("%-50s: - %s\n", j2, isValidJson(j2) == 1? "pass" : "fail");
  char j3[] = "{\"abc\":{\"hello\":123}}" ;                       printf("%-50s: - %s\n", j3, isValidJson(j3) == 1? "pass" : "fail");
  char j4[] = "{\"abc\":{{}}}" ;                                  printf("%-50s: - %s\n", j4, isValidJson(j4) == 1? "pass" : "fail");
  char j5[] = "{\"abc\":{{\"abc\":{\"a\":1}}}}" ;                 printf("%-50s: - %s\n", j5, isValidJson(j5) == 1? "pass" : "fail");
  char j6[] = "{\"abc\":{{\"abc\"    :{\"a\":1}}}}" ;             printf("%-50s: - %s\n", j6, isValidJson(j6) == 1? "pass" : "fail");
  char j7[] = "{\"abc\":{{\"abc\"    :{\"a\":1}}}}" ;             printf("%-50s: - %s\n", j7, isValidJson(j7) == 1? "pass" : "fail");
  char j8[] = "{   \"abc\":{{\"abc\"    :{\"a\":1}}}}" ;          printf("%-50s: - %s\n", j8, isValidJson(j8) == 1? "pass" : "fail");
  char j9[] = "{\"abc\"     :{{\"abc\"    :{     \"a\":1}}}}" ;   printf("%-50s: - %s\n", j9, isValidJson(j9) == 1? "pass" : "fail");

  // TODO: Not working. Fix it
  // TODO: align the test output nicely?
  // char j6[] = "{\"abc\":{{\"abc\"  :{\"a\":1}}}}" ; printf("%s: - %s\n", j6, isValidJson(j6) == 1? "pass" : "fail");

  // Invalid test
  printf("\n");
  printf("Invalid test\n");
  char j11[] = "{ ";                               printf("%s: - %s\n", j11, isValidJson(j11) == 0? "pass" : "fail");
  char j12[] = "{\"abc:123}" ;                     printf("%s: - %s\n", j12, isValidJson(j12) == 0? "pass" : "fail");
  char j13[] = "{\"abc\":{\"hello\":123}" ;        printf("%s: - %s\n", j13, isValidJson(j13) == 0? "pass" : "fail");
  char j14[] = "{\"abc\":{}}}" ;                   printf("%s: - %s\n", j14, isValidJson(j14) == 0? "pass" : "fail");
  char j15[] = "{\"abc\":{{\"abc\":\"a\":1}}}}" ;  printf("%s: - %s\n", j15, isValidJson(j15) == 0? "pass" : "fail");
  return 0;
}

