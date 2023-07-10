#include <bits/stdc++.h>
using namespace std;

int main() {
  int N, M, start_x, start_y, goal_x, goal_y;
  cin >> N >> M >> start_x >> start_y >> goal_x >> goal_y;

  vector<string> grid(N);
  for (int i = 0; i < N; i++)
    cin >> grid[i];

  vector<vector<int>> cost(N, vector<int>(M, -1));
  vector<vector<char>> dir(N, vector<char>(M, ' '));
  int dx[] = {-1, 0, 1, 0};
  int dy[] = {0, 1, 0, -1};
  char dc[] = {'N', 'E', 'S', 'W'};

  queue<pair<int, int>> q;
  q.push({start_x - 1, start_y - 1});
  cost[start_x - 1][start_y - 1] = 0;

  while (!q.empty()) {
    auto [x, y] = q.front();
    q.pop();

    for (int i = 0; i < 4; i++) {
      int nx = x + dx[i];
      int ny = y + dy[i];

      if (nx < 0 || nx >= N || ny < 0 || ny >= M || grid[nx][ny] == '#')
        continue;

      int new_cost = cost[x][y] + (grid[nx][ny] == 'W' ? 2 : 1);

      if (cost[nx][ny] == -1 || cost[nx][ny] > new_cost) {
        cost[nx][ny] = new_cost;
        dir[nx][ny] = dc[i];
        q.push({nx, ny});
      }
    }
  }

  if (cost[goal_x - 1][goal_y - 1] == -1) {
    cout << -1 << endl;
    return 0;
  }

  string path = "";
  int x = goal_x - 1, y = goal_y - 1;
  while (x != start_x - 1 || y != start_y - 1) {
    path = dir[x][y] + path;
    switch (dir[x][y]) {
    case 'N':
      x++;
      break;
    case 'S':
      x--;
      break;
    case 'E':
      y--;
      break;
    case 'W':
      y++;
      break;
    }
  }

  cout << cost[goal_x - 1][goal_y - 1] << endl;
  cout << path << endl;

  return 0;
}
