#include <bits/stdc++.h>

using namespace std;

map<int, vector<int>> g;
map<int, int> tin, tout;
int timer;

void dfs(int v) {
  tin[v] = ++timer;
  for (int u : g[v]) {
    dfs(u);
  }
  tout[v] = ++timer;
}

bool is_ancestor(int u, int v) {
  return tin[u] <= tin[v] && tout[u] >= tout[v];
}

int main() {
  int n, id, parent;
  cin >> n;

  for (int i = 0; i < n; ++i) {
    cin >> id >> parent;
    if (parent != -1) {
      g[parent].push_back(id);
    }
  }

  for (auto &p : g) {
    if (tin[p.first] == 0) {
      dfs(p.first);
    }
  }

  int q;
  cin >> q;
  while (q--) {
    int a, b;
    cin >> a >> b;
    if (is_ancestor(a, b)) {
      cout << 1 << "\n";
    } else if (is_ancestor(b, a)) {
      cout << 2 << "\n";
    } else {
      cout << 0 << "\n";
    }
  }

  return 0;
}
