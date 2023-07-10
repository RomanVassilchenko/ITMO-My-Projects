#include <bits/stdc++.h>
using namespace std;

int main() {
  deque<int> left, right;
  int N, i;
  char goblinType;
  cin >> N;
  while (N--) {
    cin >> goblinType;
    if (goblinType == '+') {
      cin >> i;
      right.push_back(i);
    }
    if (goblinType == '*') {
      cin >> i;
      right.push_front(i);
    }
    if (goblinType == '-') {
      cout << left.front() << "\n";
      left.pop_front();
    }

    while (left.size() < right.size()) {
      left.push_back(right.front());
      right.pop_front();
    }
    cout << "LOL" << endl;
    while ((int)(left.size()) - 1 > (int)(right.size())) {
      right.push_front(left.back());
      left.pop_back();
    }
    for (int i = 0; i < left.size(); i++) {
      cout << left[i] << " ";
    }
    cout << endl;
    for (int i = 0; i < right.size(); i++) {
      cout << right[i] << " ";
    }
    cout << endl;
  }
  return 0;
}