#include <bits/stdc++.h>
#define ll long long

using namespace std;
int main() {
  ll n, sum = 0;
  cin >> n;
  vector<ll> x(n), y(n);
  for (ll i = 0; i < n; i++) {
    cin >> x[i] >> y[i];
  }

  sort(x.begin(), x.end());
  sort(y.begin(), y.end());

  for (long long i = 1; i < n; i++) {
    ll deltaX = x[i] - x[i - 1];
    ll deltaY = y[i] - y[i - 1];
    sum += ((deltaX + deltaY) * (n - i) * i) * 2;
  }
  sum = sum / (n * (n - 1));
  cout << sum << endl;
  return 0;
}
