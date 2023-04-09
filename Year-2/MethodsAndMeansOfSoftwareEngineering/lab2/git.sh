#!/bin/bash

# Repl stuff
nix-env -iA nixpkgs.nano

# Настройка пользователей
git config --global user.name "User1"
git config --global user.email "user1@example.com"ф

# Настройка .gitconfig
echo -e "[merge]\n    tool = nano" >> .gitconfig

# Создание репозитория и начальная ревизия (r0)
rm -rf .git src/

git init
git checkout -b branch1
git add .gitconfig .gitignore
mkdir src
cp -f commits/commit0/* src/
git add .
git commit -m "Initial commit (r0)"

# Ревизии r1-r3 (пользователь 1)
cp -f commits/commit1/* src/
git add .
git commit -m "Revision 1 (r1)"
cp -f commits/commit2/* src/
git add .
git commit -m "Revision 2 (r2)"
cp -f commits/commit3/* src/
git add .
git commit -m "Revision 3 (r3)"

# Ревизии r4-r7 (пользователь 2)
git checkout -b branch2
cp -f commits/commit4/* src/
git add .
git commit --author="User2 <user2@example.com>" -m "Revision 4 (r4)"
cp -f commits/commit5/* src/
git add .
git commit --author="User2 <user2@example.com>" -m "Revision 5 (r5)"
cp -f commits/commit6/* src/
git add .
git commit --author="User2 <user2@example.com>" -m "Revision 6 (r6)"
cp -f commits/commit7/* src/
git add .
git commit --author="User2 <user2@example.com>" -m "Revision 7 (r7)"

# Ревизии r8 (пользователь 1)
git checkout branch1
cp -f commits/commit8/* src/
git add .
git commit -m "Revision 8 (r8)"

# Ревизии r9-r10 (пользователь 2)
git checkout branch2
cp -f commits/commit9/* src/
git add .
git commit --author="User2 <user2@example.com>" -m "Revision 9 (r9)"
cp -f commits/commit10/* src/
git add .
git commit --author="User2 <user2@example.com>" -m "Revision 10 (r10)"

# Ревизии r11
git checkout -b branch3
# Создание файла для ревизии r11 с участием обоих пользователей
cp -f commits/commit11/* src/
git add .
git rebase branch2
git commit -m "Revision 11 (r11)"

# Ревизии r12-r13 (пользователь 1)
cp -f commits/commit12/* src/
git add .
git commit -m "Revision 12 (r12)"
# cp -f commits/commit13/* src/
# git add .
# git commit -m "Revision 13 (r13)"

# Мердж ревизии r8 с r13 (получение r14)
git checkout branch1
git merge branch3 -m "Merging r8 with r13 (r14)"

if [[ $(git status --porcelain | grep "^UU") ]]; then
  echo "There are merge conflicts. Please resolve them manually."
  while true; do
    # Ждем, пока пользователь исправит конфликты вручную
    read -p "Press 'c' to continue after resolving conflicts, or 'a' to abort: " input
    case $input in
      [Cc]* )
        # Продолжаем merge после исправления конфликтов
        git add .
        git commit
        break;;
      [Aa]* )
        # Отменяем merge в случае отмены пользователем
        git merge --abort
        exit;;
      * ) echo "Please answer 'c' to continue or 'a' to abort.";;
    esac
  done
fi


# # git merge branch3 -Xours -m "Merging r8 with r13 (r14)" # Эта опция сохранит вашу версию файла (ветку, из которой выполняется слияние) в случае конфликта
# # git merge branch3 -Xtheirs -m "Merging r8 with r13 (r14)" # Эта опция сохранит версию файла из ветки, с которой выполняется слияние, в случае конфликта
