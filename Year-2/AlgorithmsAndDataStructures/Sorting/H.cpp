#include <bits/stdc++.h>
using namespace std;

int main() {
  int n, k;
  cin >> n >> k;
  vector<int> prices(n);

  for (int i = 0; i < n; ++i) {
    cin >> prices[i];
  }

  sort(prices.rbegin(), prices.rend());

  int total_sum = 0;

  for (int i = 0; i < n; ++i) {
    if ((i + 1) % k != 0) {
      total_sum += prices[i];
    }
  }

  cout << total_sum << endl;

  return 0;
}
