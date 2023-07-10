#include <bits/stdc++.h>

using namespace std;

vector<int> student_relations[100];
int student_group[100];

bool DFS(int student, int group) {
  student_group[student] = group;
  for (auto &related_student : student_relations[student]) {
    if (student_group[related_student] == -1) {
      if (!DFS(related_student, !group)) {
        return false;
      }
    } else if (student_group[related_student] == student_group[student]) {
      return false;
    }
  }
  return true;
}

void TestCheating() {
  int n, m;
  cin >> n >> m;

  memset(student_group, -1, sizeof(student_group));

  for (int i = 0; i < m; ++i) {
    int student1, student2;
    cin >> student1 >> student2;
    student1--;
    student2--;
    student_relations[student1].push_back(student2);
    student_relations[student2].push_back(student1);
  }

  bool can_divide = true;
  for (int i = 0; i < n; ++i) {
    if (student_group[i] == -1) {
      can_divide &= DFS(i, 0);
    }
  }

  if (can_divide) {
    cout << "YES" << endl;
  } else {
    cout << "NO" << endl;
  }
}

int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(NULL);

  TestCheating();

  return 0;
}
