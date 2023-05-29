// #include <bits/stdc++.h>
#include <iostream>
#include <map>
#include <string>
#include <vector>
#define endl "\n"
using namespace std;

bool is_num(string &str) {
  if ((str)[0] != '-' && !isdigit((str)[0]))
    return false;
  for (int i = 1; i < (str).size(); ++i) {
    if (!isdigit((str)[i]))
      return false;
  }
  return true;
}

int main() {

  ios_base::sync_with_stdio(false);
  cin.tie(nullptr);

  freopen("output.txt", "w", stdout);
  freopen("input.txt", "r", stdin);

  map<string, vector<int>> variables;
  vector<vector<string>> history_of_variables;
  history_of_variables.emplace_back();

  string str;
  while (cin >> str) {

    int equation_symbol = str.find('=');

    if (equation_symbol >= 0) {
      string write_to_variable = str.substr(0, equation_symbol);
      string read_from_variable = str.substr(equation_symbol + 1);
      history_of_variables.back().push_back(write_to_variable);

      if (is_num(read_from_variable)) {
        variables[write_to_variable].push_back(stoi(read_from_variable));
        continue;
      }

      if (variables.find(read_from_variable) == variables.end() ||
          variables[read_from_variable].empty()) {
        variables[write_to_variable].push_back(0);
      } else
        variables[write_to_variable].push_back(
            variables[read_from_variable].back());

      cout << variables[write_to_variable].back() << endl;
      continue;
    }

    if (str[0] == '{') {
      history_of_variables.emplace_back();
    }

    else {
      vector<string> last_vec = history_of_variables.back();
      history_of_variables.pop_back();

      for (equation_symbol = 0; equation_symbol < last_vec.size();
           equation_symbol++) {
        variables[last_vec.at(equation_symbol)].pop_back();
      }
    }
  }

  return 0;
}