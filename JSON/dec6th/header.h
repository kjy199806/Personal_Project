
/*----------------------------------------
        Function Prototypes

------------------------------------------*/
void skipSpace(char* array, int* currentIndex);
void readObject(char* array, int size, int * isValidJSON, int* currentIndex);
void readString(char* array, int* currentIndex, int size, int *isValidJSON);
void readValue(char* array, int* currentIndex, int *isObject, int *isValidJSON,int size);
void readArray(char* array, int* currentIndex, int* isObject, int *isValidJSON, int size);
void readNumber(char* array, int* currentIndex);
void validTestData(char* ar);
void invalidTestData(char* ar);

