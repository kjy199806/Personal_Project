#include <stdio.h>
#include <string.h>
#include "header.h" // <------- contains prototypes
#define readObject a
#define len strlen
#define myDebug(...) printf("line : %d function : %s", __LINE__, __FUNCTION__);printf(__VA_ARGS__)

// debug
#define debug 0
#if debug == 1
  #define myprintf printf
#else
  #define myprintf 
#endif

int main(void){

  //validTestData();
  printf("\n");
  printf("-----------------------------------\n");
  printf("       testing valid JSON          \n");
  printf("-----------------------------------\n");
  int size = 0;
  int iV= 1;
  char valid [] = "{\"hello\":123}"  ;           validTestData(valid);
  char valid2[] = "{\"hello\":10}" ;             validTestData(valid2);
  char valid3[] = "{\"a\":{\"b\":123}}";         validTestData(valid3);
  char valid4[] = "{\"a\" :  123}";              validTestData(valid4);
  char valid5[] = "{}";                          validTestData(valid5);
  char valid6[] = "{\"a\":124,\"h\":122}";       validTestData(valid6);

  //invalidTestData();
  printf("\n\n");
  printf("-----------------------------------\n");
  printf("      testing invalid JSON         \n");
  printf("-----------------------------------\n");

  char invalid [] = "{\"he\"llo\":392}";          invalidTestData(invalid);
  char invalid2[] = "{\"hello\":3j3r}" ;          invalidTestData(invalid2);
  char invalid3[] = "{\"hello\":33" ;             invalidTestData(invalid3);
  char invalid4[] = "{\"hello\":##}" ;            invalidTestData(invalid4);
  char invalid5[] = "\
                       {\"a\":\
                       {\"b\":124},\
                       {\"c\":125},\
                     }";                               invalidTestData(invalid5);
  char invalid6[] = "{\"a\":{\"b\":124},{\"c\":125},"; invalidTestData(invalid6);
  char invalid7[] = "{\"a\":{\"b\":124},,{\"c\":125},";invalidTestData(invalid7);
  

}
  
/*---------------------------------------------------------

              **readObject function**

    process : It will check the object format
        
    isValidJSON = return 1 (True )
                  return 0 (False)

-----------------------------------------------------------*/

void readObject(char* array, int size, int* isValidJSON, int* currentIndex){
  int isObject = 0;
  *isValidJSON = 1;
  size = strlen(array);
  for(; *currentIndex < size; (*currentIndex)++){
    skipSpace(array, currentIndex);

  /*--------------------------------------------------
        1. Reading object  
  ----------------------------------------------------*/

    if(array[*currentIndex] == '{'){ (*currentIndex)++;
      skipSpace(array, currentIndex);

      /*----------------------------------------------------------------
        Repeating the same process until their is no [,] after the value
      -----------------------------------------------------------------*/

                   //---------------------------------------------------------
                   //           **Process of an (do while) **
                   //
      do{          // <--- (do while) is used when there is [,]
                   //---------------------------------------------------------

          if(array[*currentIndex] == ','){  //---------------------------
                                            //        **Process**
            (*currentIndex)++;              // after looping once index+1
            skipSpace(array, currentIndex); // to check the next index
          }                                 //---------------------------

          if(array[*currentIndex] == '"'){ (*currentIndex)++; 

            /*-------------------------------------------------------
              2. Reading String
              --------------------------------------------------------*/

            readString(array, currentIndex, size, isValidJSON);
            (*currentIndex)++;

          }

          else if(array[*currentIndex] == '}'){

            /*--------------------------------------------------------
              End of Object                   (Valid Object)
              --------------------------------------------------------*/

            return;

          } 

          else{
            /*---------------------------------------------------------
              Invalid Reason : does not have ["] or [}] after the [{]

              JSON Format : { (string) : (value) }
              ^                  ^
              | ["]   (or)  [}]  |
              |                  |
              ---------------------------------------------------------*/
            *isValidJSON = 0;
            return;
          }skipSpace(array, currentIndex);

        /*---------------------------------------------------------
          3. Reading Value
          ----------------------------------------------------------*/

          if(array[*currentIndex] == ':'){ (*currentIndex)++; 
            // debugging
            myprintf("Passing through [:] current Index : %d \n" , *currentIndex);
            skipSpace(array, currentIndex);

            readValue(array, currentIndex, &isObject, isValidJSON, size);

          }
          else{

          /*-------------------------------------------------------------
            Invalid Reason : require [:] after the String

            JSON Format :   { (string) : (value) }
                                       ^
                                       | (required)
                                       |
            --------------------------------------------------------------*/
            //debug: telling the current Index and the error line number
            myprintf("current Index :  %c\n", array[*currentIndex]);
            myprintf("%d error\n", __LINE__);
            *isValidJSON = 0;
            return;

          } skipSpace(array, currentIndex);
          
          //debugging if the while loop work or not
          //if(array[*currentIndex] == ','){
          //  myprintf("found [,]!!!\n");
          //}

      /*------------------------------ 
        continue looping if there is [,] 
        after the value
        --------------------------------*/

      }while(array[*currentIndex] == ',');  // end of do while loop

    } // end of if conditional about  '{' 

    if(array[*currentIndex] == '}'){

    /*----------------------------
      Found [}]  (Valid JSON)
      -----------------------------*/
      return;
    }
    else{
      // debug: telling the current Index and the error line number
      myprintf("current Index :  %d [%c]\n",*currentIndex, array[*currentIndex]);
      myprintf("%d error\n", __LINE__);
      *isValidJSON = 0;
      return;

    }
  } // end of for looping

} // end of  readObject function 

/*-----------------------------------------------------

              **skipSpace function**

  Process : continue to increase the current index 
            until the character is found

-------------------------------------------------------*/
void skipSpace(char* array, int* currentIndex){
  for( ; *currentIndex<strlen(array); (*currentIndex)++){
    if(array[*currentIndex] != ' '){
      return;
    }
  }
}

/*-------------------------------------------------------

              **readString function** 
  
              Any UNICODE character except
              ["]or [\] or control character 

---------------------------------------------------------*/
void readString(char* array, int* currentIndex,int size, int *isValidJSON){
  for( ; *currentIndex<strlen(array); (*currentIndex)++){
    skipSpace(array, currentIndex);
    if(array[*currentIndex] != '"'){
        
    }
    else if(*currentIndex == size && array[*currentIndex] != '}'){
      // debug: printf if it inValid
      myprintf("line : %d error!!!\n",__LINE__);
      *isValidJSON = 0;
      return;
    }
    else{ // found ["]  
      return;
    }
  }
}

/*---------------------------------------------------------

              **readValue function**
  
              Value can be (string)
                           (number)
                           (object)
                           (array)

-----------------------------------------------------------*/
void readValue(char* array, int* currentIndex, int* isObject,int *isValidJSON, int size){
  /*---------------------------------------------
                **For string**

  ---------------------------------------------*/

  if(array[*currentIndex] == '"'){
    readString(array, currentIndex,size, isValidJSON);
  }

  /*----------------------------------------------
                **For number**

  -----------------------------------------------*/

  else if(array[*currentIndex] >= '0' && array[*currentIndex] <= '9'){ 
    readNumber(array, currentIndex);
  }

  /*-----------------------------------------------  
                **For object**

  -------------------------------------------------*/

  else if(array[*currentIndex] == '{'){
    readObject(array, size, isValidJSON, currentIndex);
    skipSpace(array, currentIndex);
    if(array[*currentIndex] == '}'){
      (*currentIndex)++;
    }
    //printf("current index : [%c]\n" , array[*currentIndex]);
    return;
  }

  /*------------------------------------------------
                **For array**

  --------------------------------------------------*/

  else if(array[*currentIndex] == '['){ 
    readArray(array, currentIndex, isObject, isValidJSON, size);
    return;
  }

  /*------------------------------------------
    If it is not (string)
                 (number)
                 (object)
                 (array )
  -------------------------------------------*/

  else{
    *isValidJSON = 0;
    return;
  }
    
}

/*------------------------------------------------
            ** readArray function **

    An array begins with '[' and ends with ']'. 
    Values are separated by [,]

-------------------------------------------------*/

void readArray(char* array,int* currentIndex,int* isObject,int* isValidJSON, int size){

  do{
    readValue(array, currentIndex, isObject, isValidJSON, size);

    /*------------------------------------------
              If the value is (object)
    -------------------------------------------*/
    if(isObject){
      return;
    } skipSpace(array, currentIndex); *currentIndex++;

  }while(array[*currentIndex] == ','); skipSpace(array, currentIndex);

  if(array[*currentIndex] == ']'){
    return;
  }
  else{
    *isValidJSON = 0;
    return;
  }

}

/*--------------------------------------------------
              ** readNumber function**

----------------------------------------------------*/

void readNumber(char* array, int* currentIndex){
  for( ; *currentIndex < strlen(array); (*currentIndex)++){
    if(array[*currentIndex] >= '0' && array[*currentIndex] <= '9'){ 
    }
    else{
      return;
    }
  }
}

/*------------------------------------------------------------
                    ** valid function **
--------------------------------------------------------------
  ar = array              |   #define readObject a                  
  iV = isValid            |   #define len strlen                   
  I = currentIndex        |   printf "pass" if it is working well
--------------------------------------------------------------*/

void validTestData(char* ar){
  int iV =1;
  int size = 0;
  int I=0;
  size =len(ar) ;a(ar,size,&iV,&I); printf("%s: %s\n", ar, iV==1?"Pass":"Fail"); 
}

/*------------------------------------------------------------
                    ** invalid function **
--------------------------------------------------------------
  ar = array              |   #define readObject a                  
  iV = isValid            |   #define len strlen                   
  I = currentIndex        |   printf "pass" if it is working well
--------------------------------------------------------------*/

void invalidTestData(char* ar){
  int iV =1;
  int size = 0;
  int I=0;
  size =len(ar);a(ar,size,&iV,&I); printf("%s: %s\n", ar,iV==0?"Pass":"Fail");
}
