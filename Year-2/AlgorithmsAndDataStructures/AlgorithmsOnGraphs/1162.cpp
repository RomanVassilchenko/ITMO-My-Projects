#include <bits/stdc++.h>

using namespace std;

struct Edge {
  int from, to;
  double rate, commission;
};

int main() {
  ios::sync_with_stdio(false);
  cin.tie(0);

  int n, m, s;
  double v;
  cin >> n >> m >> s >> v;
  s--;
  vector<Edge> edges;
  for (int i = 0; i < m; i++) {
    int a, b;
    double rab, cab, rba, cba;
    cin >> a >> b >> rab >> cab >> rba >> cba;
    a--;
    b--;
    edges.push_back({a, b, rab, cab});
    edges.push_back({b, a, rba, cba});
  }
  vector<double> dist(n, 0.0);
  dist[s] = v;
  for (int i = 0; i < n; i++) { // релаксация
    for (Edge e : edges) {
      dist[e.to] = max(dist[e.to], (dist[e.from] - e.commission) * e.rate);
    }
  }
  bool increased = false;
  for (Edge e : edges) {
    if ((dist[e.from] - e.commission) * e.rate > dist[e.to]) {
      increased = true;
      break;
    }
  }
  if (increased)
    cout << "YES\n";
  else
    cout << "NO\n";
  return 0;
}
