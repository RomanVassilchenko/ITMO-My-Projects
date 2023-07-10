#include <bits/stdc++.h>
using namespace std;

struct animal_trap {
  int number;
  char letter;
};

bool letterEqualAndDifferentCase(char &l1, char &l2) {
  return abs(l1 - l2) == 32;
}

int main() {
  string s;
  cin >> s;

  int count = 0;
  list<animal_trap> animal_list;

  for (int i = 0; i < s.size(); i++) {
    if (isupper(s[i]))
      animal_list.push_back({i - count, s[i]});
    else
      animal_list.push_back({++count, s[i]});
  }

  vector<int> results(s.size() / 2);

  while (!animal_list.empty()) {
    auto it = ++animal_list.begin();
    animal_trap animal{}, trap{};
    if (letterEqualAndDifferentCase(animal_list.front().letter,
                                    animal_list.back().letter)) {
      if (isupper(animal_list.front().letter)) {
        trap = animal_list.front();
        animal = animal_list.back();
      } else {
        animal = animal_list.front();
        trap = animal_list.back();
      }
      animal_list.pop_front();
      animal_list.pop_back();

      results[trap.number] = animal.number;
    } else if (letterEqualAndDifferentCase(animal_list.front().letter,
                                           (*it).letter)) {
      if (isupper(animal_list.front().letter)) {
        trap = animal_list.front();
        animal = (*it);
      } else {
        animal = animal_list.front();
        trap = (*it);
      }
      animal_list.pop_front();
      animal_list.pop_front();
      results[trap.number] = animal.number;
    } else {
      break;
    }
  }

  if (!animal_list.empty()) {
    cout << "Impossible\n";
  } else {
    cout << "Possible\n";
    for (int result : results) {
      cout << result << " ";
    }
    cout << "\n";
  }
  return 0;
}
