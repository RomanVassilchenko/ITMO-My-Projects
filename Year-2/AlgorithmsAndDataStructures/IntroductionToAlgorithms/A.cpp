
//
// Created by rossilman on 25.02.23.
//

#include <iostream>
#include <vector>

using namespace std;

int main() {
  int n;
  cin >> n;
  vector<int> array(n);

  for (int i = 0; i < n; i++)
    cin >> array[i];

  int max_size = 0, curr_size = 0, last = 1, count = 1;

  for (int i = 0; i < n; i++) {
    curr_size++;

    if (i >= n - 1) {
      if (curr_size > max_size) {
        max_size = curr_size;
        last = i + 1;
      }
    } else {
      if (array[i] != array[i + 1])
        count = 1;
      else
        count++;

      if (count == 3) {
        count = (array[i] == array[i + 1]) ? 2 : 1;
        if (curr_size > max_size) {
          max_size = curr_size;
          last = i + 1;
        }
        curr_size = 1;
      }
    }
  }

  cout << last + 1 - max_size << " " << last << endl;
  return 0;
}