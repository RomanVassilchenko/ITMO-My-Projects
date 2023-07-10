#include <iostream>
#define endl "\n"
using namespace std;
int main() {
  ios_base::sync_with_stdio(false);
  cin.tie(nullptr);
  int a, b, c, d;
  long k;
  cin >> a >> b >> c >> d >> k;
  int loop = a;

  if (a * b - c <= 0) {
    cout << 0 << endl;
    return 0;
  } else if (a * b - c >= d) {
    cout << d << endl;
    return 0;
  } else if (a * b - c == a) {
    cout << a << endl;
    return 0;
  }

  for (long i = 0; i < k; ++i) {
    a = a * b - c;

    if (a <= 0) {
      a = 0;
      break;
    }
    if (a >= d) {
      a = d;
    }
    if (a == loop) {
      break;
    }
    loop = a;
  }
  cout << a << "\n";
}