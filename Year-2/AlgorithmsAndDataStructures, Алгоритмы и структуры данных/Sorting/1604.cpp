#include <bits/stdc++.h>
using namespace std;

struct Sign {
  int count;
  int index;
};

pair<int, int> find_min_max(Sign *signs, int k) {
  int min_count = INT_MAX, max_count = INT_MIN;
  int min_index = -1, max_index = -1;
  for (int i = 0; i < k; i++) {
    if (signs[i].count > 0) {
      if (signs[i].count >= max_count) {
        max_count = signs[i].count;
        max_index = i;
      }
      if (signs[i].count < min_count) {
        min_count = signs[i].count;
        min_index = i;
      }
    }
  }

  return make_pair(min_index, max_index);
}

void print_sequence(Sign *signs, int k) {
  int signs_count = 0;
  for (int i = 0; i < k; i++) {
    signs_count += signs[i].count;
  }
  pair<int, int> min_max;
  while (signs_count > 0) {
    min_max = find_min_max(signs, k);

    if (signs[min_max.second].count > 0) {
      cout << signs[min_max.second].index << " ";
      signs[min_max.second].count--;
      signs_count--;
    }

    if (signs[min_max.first].count > 0) {
      cout << signs[min_max.first].index << " ";
      signs[min_max.first].count--;
      signs_count--;
    }
  }
}

int main() {
  int k;
  cin >> k;

  Sign signs[k];
  for (int i = 0; i < k; i++) {
    cin >> signs[i].count;
    signs[i].index = i + 1;
  }

  print_sequence(signs, k);

  return 0;
}