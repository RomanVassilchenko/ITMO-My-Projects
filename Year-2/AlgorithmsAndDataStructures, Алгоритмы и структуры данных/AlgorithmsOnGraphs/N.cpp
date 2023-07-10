#include <bits/stdc++.h>

using namespace std;

void DFS(int curr_node, vector<pair<vector<int>, int>> &piggy_banks,
         int &break_count) {
  piggy_banks[curr_node].second = 1;
  for (int next_node : piggy_banks[curr_node].first) {
    if (piggy_banks[next_node].second == 0) {
      DFS(next_node, piggy_banks, break_count);
    } else if (piggy_banks[next_node].second == 1) {
      ++break_count;
    }
  }
  piggy_banks[curr_node].second = 2;
}

int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(NULL);

  int n, key;
  cin >> n;

  vector<pair<vector<int>, int>> piggy_banks(n);
  for (int i = 0; i < n; ++i) {
    cin >> key;
    piggy_banks[--key].first.push_back(i);
  }

  int break_count = 0;
  for (int i = 0; i < n; ++i) {
    if (piggy_banks[i].second == 0) {
      DFS(i, piggy_banks, break_count);
    }
  }

  cout << break_count << "\n";

  return 0;
}
