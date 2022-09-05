#include <bits/stdc++.h>
using namespace std;

void calcMult(){
    cout << "Введите размеры первой матрицы (количество-строк количество-столбцов): ";
    int a,b;
    cin >> a >> b;
    int aMatrix[a][b];
    cout << "Ввод первой матрицы:" << endl;
    for(int i = 0; i < a; i++) {
        for(int j = 0; j < b; j++) {
            cin >> aMatrix[i][j];
        }
    }
    cout << "Введите размеры второй матрицы (количество-строк количество-столбцов): ";
    int c,d;
    cin >> c >> d;
    int bMatrix[c][d];
    cout << "Ввод второй матрицы:" << endl;
    for(int i = 0; i < c; i++) {
        for(int j = 0; j < d; j++) {
            cin >> bMatrix[i][j];
        }
    }
    int product[a][d];

    for(int i = 0; i < a; i++){
        for(int j = 0; j < d; j++) product[i][j] = 0;
    }

    cout << "\nПроизведение матриц: \n" << endl;
    for (int row = 0; row < a; row++) {
        for (int col = 0; col < d; col++) {
            for (int inner = 0; inner < b; inner++) {
                product[row][col] += aMatrix[row][inner] * bMatrix[inner][col];
            }
            cout << product[row][col] << "  ";
        }
        cout << "\n";
    }
    
    
}

void printMatrix(vector<vector<int> > a){
    for(int i = 0; i < a.size(); i++){
        vector<int> b = a.at(i);
        for(int j = 0; j < b.size(); j++){
            cout << b.at(j) << " ";
        }
        cout << endl;
    }
}

void printVector(vector<int> a){
    for(int i = 0; i < a.size(); i++){
        cout << a.at(i) << " ";
    }
    cout << endl;
}

int calcDet(vector<vector<int> > aMatrix){
    int a = aMatrix.size();
    if(a == 2) return aMatrix.at(0).at(0) * aMatrix.at(1).at(1) - aMatrix.at(0).at(1) * aMatrix.at(1).at(0);
    
    int result = 0;

    int curr = 0;
    for(curr = 0; curr < a; curr++){
        vector<vector<int> > bMatrix;
        for(int i = 1; i < a; i++){
            vector<int> tempVector;
            for(int j = 0; j < a; j++){
                if(j != curr){
                    tempVector.push_back(aMatrix.at(i).at(j));
                    if(tempVector.size() >= a - 1) break;
                }
            }
            bMatrix.push_back(tempVector);
        }
        result += pow(-1, curr) * aMatrix.at(0).at(curr) * calcDet(bMatrix);
    }
    return result;
}

int main(){
    while(true){
        cout << "Выберите: Произведение матриц или Поиск определителя? (1 или 2)" << endl;
    int input;
    cin >> input;
    if(input == 1) {
        calcMult();
        break;
    } else if(input == 2) {
        cout << "Введите размер матрицы (число): ";
        int a;
        cin >> a;
        vector<vector<int> > aMatrix;
        cout << "Ввод матрицы:" << endl;
        for(int i = 0; i < a; i++) {
            vector<int> tempVector;
            for(int j = 0; j < a; j++) {
                int q;
                cin >> q;
                tempVector.push_back(q);
            }
            aMatrix.push_back(tempVector);
        }
        cout << "Детерминант = " << calcDet(aMatrix) << endl;
        break;
    } else {
        continue;
    }
    }
    return 0;
}