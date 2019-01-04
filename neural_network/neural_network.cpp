#include <algorithm>
#include <cmath>
#include <cstdlib>
#include <iomanip>
#include <iostream>
#include <numeric>
#include <vector>

using namespace std;

vector<vector<double>> T(std::vector<std::vector<double>> myVector){

  //transpose
  std::vector<std::vector<double>> temp(myVector[0].size(), vector<double>(myVector.size(),0));

  int row = 0;
  int col = 0;
  for(int i = 0;  i < myVector.size(); i++){

    for(auto j = myVector[i].begin();  j != myVector[i].end(); j++){
      temp[row++][col] = *j;

      //myVector row and col can be different ex) row = 2 , col = 4
      if(row == temp.size()){
        row = 0;
        col++;
      }

    }

  }

  //resize the myVector
  myVector.resize(temp.size());
  for(int i = 0; i < myVector.size(); i++){
    myVector[i].resize(temp[0].size());
  }

  //reset row and column
  row = 0;
  col = 0;

  for(int i = 0;  i < temp.size(); i++){
    for(auto j = temp[i].begin();  j != temp[i].end(); j++){
      myVector[row][col++] = *j;
    }

    row++;
    col = 0;

  }
  return myVector;
}

void setRandomNumber(std::vector<std::vector<double>>& myVector){

  srand(1);
  //setting random number
  for(int i = 0; i<myVector.size(); i++){
    for(auto j = myVector[i].begin(); j != myVector[i].end(); j++){
       *j = (double)2*rand()/(RAND_MAX)-1;
    }
  }

}

vector<vector<double>> dot(vector<vector<double>> myVector1,vector<vector<double>> myVector2){

  vector<vector<double>> myDot(myVector1.size(),vector<double>(myVector2[0].size()));

  vector<vector<double>>myVector2_T(myVector2);
  myVector2_T = T(myVector2_T);

  int  productRow = 0;

  for (auto& Row : myVector1){

    transform(myVector2_T.begin(), myVector2_T.end(), myDot[productRow].begin(),
      [Row](vector <double> &v){
        double val = inner_product(Row.begin(), Row.end(), v.begin(), (double)0);
        return val;
      }
    );

    productRow++;
  }
  return myDot;
}

void display(std::vector<std::vector<double>> myVector){
  cout << '[';
  for(int i = 0; i < myVector.size(); i++){
    if(i != 0){
      cout << ',';
      cout << endl;
    }
    for(auto j = myVector[i].begin(); j != myVector[i].end(); j++){
      if(j != myVector[i].begin()){
        cout << ' ';
      }
      cout << '[' << std::setprecision(8)<< *j << ']';
    }
  }
  cout << ']' << endl;
}

// matrix operator prototypes
vector<vector<double>> operator  -  (vector<vector<double>> myVector);
vector<vector<double>> exp          (vector<vector<double>> myVector);
vector<vector<double>> operator  +  (int value, vector<vector<double>> myVector);
vector<vector<double>> operator  /  (int value ,vector<vector<double>> myVector);
vector<vector<double>> operator  -  (vector<vector<double>> myVector1, vector<vector<double>> myVector2);
vector<vector<double>> operator  -  (int value, vector<vector<double>> myVector);
vector<vector<double>> operator  *  (vector<vector<double>> myVector1, vector<vector<double>> myVector2);
vector<vector<double>>& operator -= (vector<vector<double>>& myVector1, vector<vector<double>> myVector2);
vector<vector<double>> operator  -  (vector<vector<double>> myVector1, vector<vector<double>> myVector2); 
vector<vector<double>> operator  *  (double value, vector<vector<double>> myVector);


                                                                               ////////////////////////////////////////////////////
int main(){                                                                    //Python 13 Line codes
                                                                               //
  vector<vector<double>> X = {{0, 0, 1}, {0, 1, 1}, {1, 0, 1}, {1, 1, 1}};     //X = np.array([ [0,0,1],[0,1,1],[1,0,1],[1,1,1] ])
  vector<vector<double>> y = {{0}, {1}, {1}, {0}};                             //y = np.array([[0,1,1,0]]).T
  double alpha = 0.5, hidden_dim = 4;                                          //alpha,hidden_dim = (0.5,4)
                                                                               //
  vector<vector<double>> synapse_0(3,vector<double>(hidden_dim,0));            //
  vector<vector<double>> synapse_1(hidden_dim,vector<double>(1,0));            //
                                                                               //
  setRandomNumber(synapse_0);                                                  //synapse_0 = 2*np.random.random((3,hidden_dim)) - 1
  setRandomNumber(synapse_1);                                                  //synapse_1 = 2*np.random.random((hidden_dim,1)) - 1
                                                                               //
  for(int i = 0; i < 60000; i++){                                              //for j in xrange(60000):
    vector<vector<double>> layer_1(X[0].size(),vector<double>(X.size(),0));    //
    layer_1 = 1/(1+exp(-(dot(X,synapse_0))));                                  //layer_1 = 1/(1+np.exp(-(np.dot(X,synapse_0))))
    vector<vector<double>> layer_2(y.size(),vector<double>(1,0));              //   
    layer_2 = 1/(1+exp(-(dot(layer_1,synapse_1))));                            //layer_2 = 1/(1+np.exp(-(np.dot(layer_1,synapse_1))))        
                                                                               //
    vector<vector<double>> layer_2_delta(y.size(),vector<double>(1,0));        //
    layer_2_delta = (layer_2 - y)*(layer_2*(1-layer_2));                       //layer_2_delta = (layer_2 - y)*(layer_2*(1-layer_2))
                                                                               //
    vector<vector<double>> layer_1_delta(y.size(),vector<double>(1,0));        //
    layer_1_delta = dot(layer_2_delta,T(synapse_1)) * (layer_1 * (1-layer_1)); //layer_1_delta = layer_2_delta.dot(synapse_1.T) * 
                                                                               //                (layer_1 * (1-layer_1))
    synapse_1 -= (alpha * dot(T(layer_1),layer_2_delta));                      //synapse_1 -= (alpha * layer_1.T.dot(layer_2_delta))
    synapse_0 -= (alpha * dot(T(X),layer_1_delta));                            //synapse_0 -= (alpha * X.T.dot(layer_1_delta))
                                                                               ////////////////////////////////////////////////////
  }
  return 0;
}

//operators
vector<vector<double>> operator - (vector<vector<double>> myVector){
  for(auto& row: myVector){
    transform(row.begin(),row.end(),row.begin(),[](double value){
      return value * -1;
    });
  }
  return myVector;
}

vector<vector<double>> exp(vector<vector<double>> myVector){
  for(auto& row: myVector){
    transform(row.begin(),row.end(),row.begin(),[](double value){
      return exp(value);
    });
  }
  return myVector;
}

vector<vector<double>> operator + (int value, vector<vector<double>> myVector){
  for(auto& row: myVector){
    transform(row.begin(),row.end(),row.begin(),[value](double vecValue){
      return value + vecValue;
    });
  }
  return myVector;
}
vector<vector<double>> operator / (int value ,vector<vector<double>> myVector){
  for(auto& row: myVector){
    transform(row.begin(),row.end(),row.begin(),[value](double vecValue){
      return value / vecValue;
    });
  }
  return myVector;
}

vector<vector<double>> operator - (vector<vector<double>> myVector1, vector<vector<double>> myVector2){
  vector<vector<double>> temp(myVector1.size(),vector<double>(myVector1[0].size(),0));

  for(int i = 0; i < myVector1.size(); i++){
    for(int j = 0; j < myVector1[i].size(); j++){
      temp[i][j] = myVector1[i][j] - myVector2[i][j];
    }
  }
  return temp;
}

vector<vector<double>> operator - (int value, vector<vector<double>> myVector){
  for(auto& row: myVector){
    transform(row.begin(),row.end(),row.begin(),[value](double vecValue){
      return value - vecValue;
    });
  }
  return myVector;
}

vector<vector<double>> operator * (vector<vector<double>> myVector1, vector<vector<double>> myVector2){
  vector<vector<double>> temp(myVector1.size(),vector<double>(myVector1[0].size(),0));
  for(int i = 0; i < myVector1.size(); i++){
    for(int j = 0; j < myVector1[i].size(); j++){
      temp[i][j] = myVector1[i][j] * myVector2[i][j];
    }
  }
  return temp;
}

vector<vector<double>>& operator -=(vector<vector<double>>& myVector1, vector<vector<double>> myVector2){
  myVector1 = myVector1 - myVector2;
  return myVector1;
}

vector<vector<double>> operator  *  (double value, vector<vector<double>> myVector){
  for(auto& row: myVector){
    transform(row.begin(),row.end(),row.begin(),[value](double vecValue){
      return value * vecValue;
    });
  }
  return myVector;
}
