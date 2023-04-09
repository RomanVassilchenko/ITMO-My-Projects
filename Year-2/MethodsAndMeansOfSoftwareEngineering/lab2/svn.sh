#!/bin/bash

# Создание локального репозитория
svnadmin create repo
REPO_URL="file://$(pwd)/repo"

# Создание рабочей копии
svn co $REPO_URL wc

# Начальная ревизия (r0)
cd wc
touch file.txt
svn add file.txt
svn commit -m "Initial commit (r0)"

# Создание ветки branch1 и ревизии r1-r3 (пользователь 1)
svn copy $REPO_URL/trunk $REPO_URL/branches/branch1 -m "Creating branch1"
svn update
svn switch $REPO_URL/branches/branch1

echo "Change1" > file.txt
svn commit -m "Revision 1 (r1)"

echo "Change2" > file.txt
svn commit -m "Revision 2 (r2)"

echo "Change3" > file.txt
svn commit -m "Revision 3 (r3)"

# Создание ветки branch2 и ревизии r4-r7 (пользователь 2)
svn copy $REPO_URL/trunk $REPO_URL/branches/branch2 -m "Creating branch2"
svn update
svn switch $REPO_URL/branches/branch2

echo "Change4" > file.txt
svn commit -m "Revision 4 (r4)"

echo "Change5" > file.txt
svn commit -m "Revision 5 (r5)"

echo "Change6" > file.txt
svn commit -m "Revision 6 (r6)"

echo "Change7" > file.txt
svn commit -m "Revision 7 (r7)"

# Ревизии r8 (пользователь 1)
svn update
svn switch $REPO_URL/branches/branch1

echo "Change8" > file.txt
svn commit -m "Revision 8 (r8)"

# Ревизии r9-r10 (пользователь 2)
svn update
svn switch $REPO_URL/branches/branch2

echo "Change9" > file.txt
svn commit -m "Revision 9 (r9)"

echo "Change10" > file.txt
svn commit -m "Revision 10 (r10)"

# Создание ветки branch3 и ревизии r11
svn copy $REPO_URL/branches/branch2 $REPO_URL/branches/branch3 -m "Creating branch3"
svn update
svn switch $REPO_URL/branches/branch3

echo "Change11" >file.txt
svn commit -m "Collaborative commit r11 by User1 and User2"

# Ревизии r12-r13 (пользователь 1)
echo "Change12" > file.txt
svn commit -m "Revision 12 (r12)"

echo "Change13" > file.txt
svn commit -m "Revision 13 (r13)"

# Мердж ревизии r8 с r13 (получение r14)
svn update
svn switch $REPO_URL/branches/branch1
svn merge $REPO_URL/branches/branch3 --accept=postpone
if svn status | grep -q '^C'; then
  echo "Merge conflict detected. Opening Nano editor to resolve conflict..."
  nano file.txt
  svn resolved file.txt
  svn commit -m "Resolved merge conflict in file.txt"
else
  svn commit -m "Merged branch3 into branch1"
fi
