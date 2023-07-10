#include <bits/stdc++.h>
using namespace std;

int main() {
  int numBalls, highestBall = 0;
  cin >> numBalls;
  stack<int> ballStack;

  for (int i = 0; i < numBalls; i++) {
    int currentBall;
    cin >> currentBall;

    if (currentBall > highestBall) {
      for (int j = highestBall + 1; j < currentBall; j++) {
        ballStack.push(j);
      }
      highestBall = currentBall;
    } else {
      if (currentBall == ballStack.top()) {
        ballStack.pop();
      } else {
        cout << "Cheater" << endl;
        return 0;
      }
    }
  }
  cout << "Not a proof" << endl;
  return 0;
}
