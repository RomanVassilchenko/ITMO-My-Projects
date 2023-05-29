#include <algorithm>
#include <iostream>
#include <vector>

using namespace std;

const int MAXN = 150000;
const int MAXK = 10000;

int n, k;
int a[MAXN];
int log2[MAXN + 1];
int table[MAXN][20];

void build_sparse_table() {
  for (int i = 0; i < n; i++) {
    table[i][0] = a[i];
  }
  for (int j = 1; j <= log2[n]; j++) {
    for (int i = 0; i + (1 << j) <= n; i++) {
      table[i][j] = min(table[i][j - 1], table[i + (1 << (j - 1))][j - 1]);
    }
  }
}

int query_sparse_table(int l, int r) {
  int j = log2[r - l + 1];
  return min(table[l][j], table[r - (1 << j) + 1][j]);
}

int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(nullptr);

  cin >> n >> k;
  for (int i = 0; i < n; i++) {
    cin >> a[i];
  }

  log2[1] = 0;
  for (int i = 2; i <= n; i++) {
    log2[i] = log2[i / 2] + 1;
  }

  build_sparse_table();

  for (int i = 0; i <= n - k; i++) {
    cout << query_sparse_table(i, i + k - 1) << " ";
  }
  cout << endl;

  return 0;
}