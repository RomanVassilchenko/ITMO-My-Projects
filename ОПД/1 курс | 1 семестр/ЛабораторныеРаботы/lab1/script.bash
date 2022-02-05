# Часть 1

mkdir lab0

mkdir lab0/goldeen3 lab0/goldeen3/boldore lab0/goldeen3/jumpluff
mkdir lab0/vigoroth4 lab0/vigoroth4/lotad lab0/vigoroth4/octillery
mkdir lab0/zubat6 lab0/zubat6/watchog lab0/zubat6/trubbish lab0/zubat6/electivire

echo -e "Возможности Overland=7 Surface=5 Jump=3 Power=2 Intelligence=4\nTracker=0 Zapper=0" > lab0/goldeen3/luxio
echo -e "Ходы  Bind Body Slam Counter Dark Pulse\nDouble-Edge Dynamicpunch Fire Punch Focus Punch Gravity Ice Punch Icy\nWind Mega Kick Mega Punch Metronome Mud-Slap Ominous Wind Pain Split\nSeismic Toss Skill Swap Sleep Talk Snatch Snore Spite Sucker Punch\nThunderpunch Trick Wonder Room" > lab0/goldeen3/dusclops
echo -e "Тип покемона  BUG\nSTEEL" > lab0/goldeen3/escavalier
echo -e "Ходы  Covet Dark Pulse Foul Play Gunk Shot Hyper Voice\nIron Tail Knock Off Role Play Seed Bomb Sleep Talk Snatch Shore Spite\nTrick" > lab0/goldeen3/purrloin
echo -e "satk=9 sdef=5 spd=7" > lab0/mothim3
echo -e "Способности  Leer Peck\nFury Attack Wing Attack Hone Claws Scary Face Aerial Ace Slash Defog\nTailwind Air Slash Crush Claw Sky Drop Whirlwind Brave Bird\nThrash" > lab0/rufflet4
echo -e "Способности  Mountain Peak Unbreakable Sturdy Rock\nHead" > lab0/shieldon3
echo -e "satk=4 sdef=4 spd=4" > lab0/vigoroth4/phanpy
echo -e "Тип диеты\nCarnviore." > lab0/vigoroth4/arbok
echo -e "Развитые способности  Prankster" > lab0/vigoroth4/darumaka
echo -e "Тип\nпокемона  WATER NONE" > lab0/vigoroth4/seel
echo -e "Живет  Cave Mountain" > lab0/zubat6/magcargo
echo -e "Ходы  Air\nCutter Body Slam Double-Edge Draco Meteor Dragon Pulse Heal Bell Heat Wave Hyper Voice Iron Tail Mud-Slap Ominous Wind Outrage Pluck Roost\nSky Attack Sleep Talk Snore Steel Wing Swift Tailwind Twister Uproar\nWonder Room" > lab0/zubat6/altaria

# Часть 2

chmod 753 lab0/goldeen3
chmod 440 lab0/goldeen3/luxio
chmod 315 lab0/goldeen3/boldore
chmod 400 lab0/goldeen3/dusclops
chmod 400 lab0/goldeen3/escavalier
chmod 770 lab0/goldeen3/jumpluff
chmod 440 lab0/goldeen3/purrloin
chmod 404 lab0/mothim3
chmod 444 lab0/rufflet4
chmod 400 lab0/shieldon3
chmod 770 lab0/vigoroth4
chmod 005 lab0/vigoroth4/phanpy
chmod 330 lab0/vigoroth4/lotad
chmod 440 lab0/vigoroth4/arbok
chmod 500 lab0/vigoroth4/darumaka
chmod 755 lab0/vigoroth4/octillery
chmod 004 lab0/vigoroth4/seel
chmod 555 lab0/zubat6
chmod 751 lab0/zubat6/watchog
chmod 315 lab0/zubat6/trubbish
chmod 504 lab0/zubat6/magcargo
chmod 520 lab0/zubat6/altaria
chmod 735 lab0/zubat6/electivire

# Часть 3

ln -s mothim3 lab0/vigoroth4/darumakamothim
cp lab0/shieldon3 lab0/goldeen3/jumpluff

#Проблема #1
#скопировать рекурсивно директорию zubat6 в директорию lab0/zubat6/watchog
#$ cp -R lab0/zubat6 lab0/zubat6/watchog
#Данная команда приведет к ошибке
#cp: cannot copy a directory, 'lab0/zubat6', into itself, 'lab0/zubat6/watchog/zubat6'
#Проблема заключается в том, что мы пытаемся рекурсивно копировать директорию в саму себя, что приводит к бесконечному циклу
#Для решения предлагаю изменить конечную директорию "lab0/zubat6/watchog" на "lab0/vigoroth4/lotad"


# Проблема #2
# скопировать рекурсивно директорию zubat6 в директорию lab0/vigoroth4/lotad
# Данная команда приведет к ошибке
# cp: cannot access 'lab0/zubat6/trubbish': Permission denied
# Проблема заключается в том, что файл trubbish в директории lab0/zubat6 имеет права 330
# которое означает, что никто не может читать файл, что приводит к этой ошибке
# Для решения предлагаю изменить права файла на 730, чтобы можно было читать, записывать и запускать файл
chmod +r lab0/zubat6/trubbish
cp -R lab0/zubat6 lab0/vigoroth4/lotad

ln lab0/rufflet4 lab0/goldeen3/luxiorufflet
cat lab0/rufflet4 > lab0/goldeen3/dusclopsrufflet
cat lab0/goldeen3/purrloin lab0/vigoroth4/arbok > lab0/shieldon3_60
ln -s lab0/goldeen3 lab0/Copy_90

# Часть 4
cd lab0/
(wc -m `ls | grep '4$'` | sort -r) 2> /dev/null
ls -lR 2>/dev/null | sort -r -n -k 2  | grep "cti" | head -3 $1
cat -n shieldon3 | sort 2>&1
ls -lRtr 2>/tmp/err | grep "ma" | tail -4 $1
cat -n shieldon3 | grep -v Hype
ls -R --sort=size | grep 'a$' | sort -r
cd ..

# Часть 5
rm -f lab0/rufflet4
rm -f lab0/vigoroth4/darumaka
rm lab0/vigoroth4/darumakamoth*
rm -rf lab0/goldeen3/luxioruffl*

# Проблема #3
# Удалить директорию zubat6
# $ rm -rf lab0/zubat6
# rm: cannot remove 'lab0/zubat6/magcargo': Permission denied
# rm: cannot remove 'lab0/zubat6/trubbish': Permission denied
# rm: cannot remove 'lab0/zubat6/electivire': Permission denied
# rm: cannot remove 'lab0/zubat6/watchog': Permission denied
# Проблема заключается в том, что данные файлы не имеют прав на "write"
# Для решения предлагаю добавить права на изменение файлов
chmod +w lab0/zubat6
chmod +w lab0/zubat6/magcargo
chmod +w lab0/zubat6/trubbish
chmod +w lab0/zubat6/electivire
chmod +w lab0/zubat6/watchog

rm -rf lab0/zubat6

rm -rf lab0/zubat6/electivire
