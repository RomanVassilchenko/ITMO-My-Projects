#!/bin/bash

# Настройка пользователей
git config --global user.name "User1"
git config --global user.email "user1@example.com"

# Настройка .gitconfig
echo -e "[merge]\n    tool = nano" >> .gitconfig

# Создание репозитория и начальная ревизия (r0)
git init
git add .gitconfig .gitignore
touch file.txt
git add file.txt
git commit -m "Initial commit (r0)"

# Ревизии r1-r3 (пользователь 1)
git checkout -b branch1
echo "Change1" > file.txt
git commit -am "Revision 1 (r1)"
echo "Change2" > file.txt
git commit -am "Revision 2 (r2)"
echo "Change3" > file.txt
git commit -am "Revision 3 (r3)"

# Ревизии r4-r7 (пользователь 2)
git checkout -b branch2
echo "Change4" > file.txt
git commit --author="User2 <user2@example.com>" -am "Revision 4 (r4)"
echo "Change5" > file.txt
git commit --author="User2 <user2@example.com>" -am "Revision 5 (r5)"
echo "Change6" > file.txt
git commit --author="User2 <user2@example.com>" -am "Revision 6 (r6)"
echo "Change7" > file.txt
git commit --author="User2 <user2@example.com>" -am "Revision 7 (r7)"

# Ревизии r8 (пользователь 1)
git checkout branch1
echo "Change8" > file.txt
git commit -am "Revision 8 (r8)"

# Ревизии r9-r10 (пользователь 2)
git checkout branch2
echo "Change9" > file.txt
git commit --author="User2 <user2@example.com>" -am "Revision 9 (r9)"
echo "Change10" > file.txt
git commit --author="User2 <user2@example.com>" -am "Revision 10 (r10)"

# Ревизии r11
git checkout -b branch3
# Создание файла для ревизии r11 с участием обоих пользователей
echo "Change11" >file.txt
GIT_AUTHOR_NAME="User1" GIT_AUTHOR_EMAIL="user1@example.com" \
GIT_COMMITTER_NAME="User2" GIT_COMMITTER_EMAIL="user2@example.com" \
git commit -am "Collaborative commit r11 by User1 and User2"

# Ревизии r12-r13 (пользователь 1)
echo "Change12" > file.txt
git commit -am "Revision 12 (r12)"
echo "Change13" > file.txt
git commit -am "Revision 13 (r13)"

# Мердж ревизии r8 с r13 (получение r14)
git checkout branch1
git merge branch3 -m "Merging r8 with r13 (r14)"

# check if there is a conflict
if [ $? -ne 0 ]; then
  echo "Merge conflict detected. Opening Nano editor to resolve conflict..."

  # open the conflicted file(s) in the Nano editor
  nano file.txt;

  git add file.txt

  git commit -m "Resolved merge conflict in file.txt"
fi
# git merge branch3 -Xours -m "Merging r8 with r13 (r14)" # Эта опция сохранит вашу версию файла (ветку, из которой выполняется слияние) в случае конфликта
# git merge branch3 -Xtheirs -m "Merging r8 with r13 (r14)" # Эта опция сохранит версию файла из ветки, с которой выполняется слияние, в случае конфликта
