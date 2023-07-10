#include <bits/stdc++.h>
using namespace std;

int n = 20, minDiff = INT_MAX;
vector<int> w(n);

void calculateSize(int i, int stone_heap1, int stone_heap2) {
  if (i >= n)
    minDiff = (minDiff > abs(stone_heap1 - stone_heap2))
                  ? abs(stone_heap1 - stone_heap2)
                  : minDiff;
  else {
    calculateSize(i + 1, stone_heap1 + w[i], stone_heap2);
    calculateSize(i + 1, stone_heap1, stone_heap2 + w[i]);
  }
}

int main() {

  cin >> n;

  for (int i = 0; i < n; i++) {
    cin >> w[i];
  }

  calculateSize(0, 0, 0);

  cout << minDiff;
}