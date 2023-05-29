#include <bits/stdc++.h>
using namespace std;

bool compare(const string &a, const string &b) { return a + b > b + a; }

int main() {
  vector<string> parts;
  string input;

  while (cin >> input)
    parts.push_back(input);

  sort(parts.begin(), parts.end(), compare);

  for (const auto &part : parts) {
    cout << part;
  }
  cout << endl;

  return 0;
}