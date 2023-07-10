#include <bits/stdc++.h>
using namespace std;

bool check_distance(const vector<int> &stalls, int distance, int cows) {
  int count = 1;
  int last_stall = stalls[0];

  for (size_t i = 1; i < stalls.size(); ++i) {
    if (stalls[i] - last_stall >= distance) {
      ++count;
      last_stall = stalls[i];
    }
  }

  return count >= cows;
}

int main() {
  int n, k;
  cin >> n >> k;
  vector<int> stalls(n);

  for (int i = 0; i < n; ++i) {
    cin >> stalls[i];
  }

  int left = 1;
  int right = stalls[n - 1] - stalls[0] + 1;
  int max_distance = 0;

  while (left <= right) {
    int mid = left + (right - left) / 2;

    if (check_distance(stalls, mid, k)) {
      max_distance = mid;
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }

  cout << max_distance << endl;

  return 0;
}
