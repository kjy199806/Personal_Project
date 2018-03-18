struct stack_t {
  int MAXSIZE;
 // int* stack;
  int* stack;
  int top;
};

void init(struct stack_t* s, int maxSize);


void delete(struct stack_t* s);

int isempty(struct stack_t* s); 
   
int isfull(struct stack_t* s); 

int peek(struct stack_t* s); 

int pop(struct stack_t* s);

int push(struct stack_t* s, int data); 

