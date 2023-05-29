#include <iostream>
#include <vector>

using namespace std;

vector<vector<int>> flight_costs;
vector<vector<bool>> possible_flights;
vector<bool> cities_visited;

void DFS(int city, bool reverse) {
  cities_visited[city] = true;
  for (int i = 0; i < flight_costs.size(); ++i) {
    if (reverse) {
      if (possible_flights[i][city] && !cities_visited[i])
        DFS(i, reverse);
    } else {
      if (possible_flights[city][i] && !cities_visited[i])
        DFS(i, reverse);
    }
  }
}

bool AllCitiesVisited() {
  for (bool visited : cities_visited)
    if (!visited)
      return false;
  return true;
}

int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(NULL);

  int n;
  cin >> n;

  flight_costs.resize(n, vector<int>(n));
  possible_flights.resize(n, vector<bool>(n));
  cities_visited.resize(n);

  for (auto &city : flight_costs)
    for (int &cost : city)
      cin >> cost;

  int left = 0, right = 1e9;

  while (left < right) {
    int mid = (left + right) / 2;

    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        possible_flights[i][j] = flight_costs[i][j] <= mid;
      }
    }

    fill(cities_visited.begin(), cities_visited.end(), false);

    DFS(0, false);

    if (AllCitiesVisited()) {
      fill(cities_visited.begin(), cities_visited.end(), false);
      DFS(0, true);

      if (AllCitiesVisited())
        right = mid;
      else
        left = mid + 1;
    } else {
      left = mid + 1;
    }
  }

  cout << left << endl;

  return 0;
}
