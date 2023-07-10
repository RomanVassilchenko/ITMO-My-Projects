#include <bits/stdc++.h>

using namespace std;

struct SymbolIndexPair {
  char symbol;
  int value;
};

bool compare(SymbolIndexPair l, SymbolIndexPair r) { return l.value > r.value; }

string getMaxWeightString(string s, vector<int> weights) {

  vector<SymbolIndexPair> alphabet;
  char symbol = 'a';
  for (int weight : weights) {
    alphabet.push_back({symbol, weight});
    symbol++;
  }

  sort(alphabet.begin(), alphabet.end(), compare);

  vector<SymbolIndexPair> symbolPairs;
  for (SymbolIndexPair pair : alphabet) {
    if (count(s.begin(), s.end(), pair.symbol) >= 2) {
      symbolPairs.push_back(pair);
    }
  }

  int left = 0;
  int right = s.size() - 1;
  for (SymbolIndexPair pair : symbolPairs) {
    int positionLeft = s.find(pair.symbol);
    if (s[left] != pair.symbol) {
      swap(s[left], s[positionLeft]);
    }
    int positionRight = s.substr(left + 1).find(pair.symbol) + left + 1;
    if (s[right] != pair.symbol) {
      swap(s[right], s[positionRight]);
    }
    left++;
    right--;
  }

  return s;
}

int main() {
  string s;
  cin >> s;

  vector<int> weights(26);
  for (int &weight : weights) {
    cin >> weight;
  }

  cout << getMaxWeightString(s, weights) << endl;
  return 0;
}
