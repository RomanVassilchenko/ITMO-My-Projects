//
// Created by roman on 24.02.2023.
//

#include <iostream>
#include <vector>
using namespace std;
int main() {
  int n;
  cin >> n;
  vector<int> p(n);
  for (int i = 0; i < n; i++)
    cin >> p[i];
  int tmp = 0, res = 0;
  for (int i = 0; i < n; i++) {
    tmp += p[i];
    tmp = (tmp > 0) ? tmp : 0;
    res = (tmp > res) ? tmp : res;
  }

  cout << res << "\n";

  return 0;
}