#include <bits/stdc++.h>

using namespace std;

int main() {
  int num_toys, max_floor_toys, num_operations, op_counter = 0;
  cin >> num_toys >> max_floor_toys >> num_operations;

  vector<int> sequence(num_operations);
  vector<list<int>> toy_entries(num_toys);

  for (int i = 0; i < num_operations; i++) {
    cin >> sequence[i];
    toy_entries[--sequence[i]].push_back(i);
  }

  unordered_set<int> floor_toys;
  priority_queue<pair<int, int>> pq_toy_ids;

  for (int i = 0; i < num_operations; i++) {
    int curr_toy = sequence[i];
    toy_entries[curr_toy].pop_front();

    if (floor_toys.find(curr_toy) == floor_toys.end()) {
      if (floor_toys.size() >= max_floor_toys) {
        floor_toys.erase(pq_toy_ids.top().second);
        pq_toy_ids.pop();
      }
      op_counter++;
      floor_toys.insert(curr_toy);
    }
    if (toy_entries[curr_toy].empty())
      pq_toy_ids.push({INT_MAX, curr_toy});
    else
      pq_toy_ids.push({toy_entries[curr_toy].front(), curr_toy});
  }
  cout << op_counter << endl;
  return 0;
}