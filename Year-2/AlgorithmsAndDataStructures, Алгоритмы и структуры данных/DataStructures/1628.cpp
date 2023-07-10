#include <bits/stdc++.h>
using namespace std;

int countWhiteStripes(const set<int> &collection, int max_size,
                      set<int> &single_elements) {
  int prev = 0;
  int result = 0;
  for (int curr : collection) {
    if (curr - prev > 2)
      result++;
    else if (curr - prev == 2)
      single_elements.insert(curr - 1);
    prev = curr;
  }
  if (max_size - prev > 1)
    result++;
  else if (max_size - prev == 1)
    single_elements.insert(max_size);
  return result;
}

int countSingleElementIntersections(const set<int> *rows,
                                    const set<int> *columns, int m, int n) {
  int intersections = 0;
  for (int i = 0; i < m; i++) {
    for (int singleton : rows[i]) {
      if (columns[singleton - 1].find(i + 1) != columns[singleton - 1].end())
        intersections++;
    }
  }
  return intersections;
}

int main() {
  int m, n, k, x, y;
  cin >> m >> n >> k;
  set<int> rows[m], columns[n];
  while (k--) {
    cin >> x >> y;
    rows[x - 1].insert(y);
    columns[y - 1].insert(x);
  }

  int result = 0;
  set<int> single_elements;

  for (set<int> &row : rows) {
    result += countWhiteStripes(row, n, single_elements);
    row = single_elements;
    single_elements.clear();
  }

  for (set<int> &column : columns) {
    result += countWhiteStripes(column, m, single_elements);
    column = single_elements;
    single_elements.clear();
  }

  result += countSingleElementIntersections(rows, columns, m, n);

  cout << result;
  return 0;
}
