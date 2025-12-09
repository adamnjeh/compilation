#! /bin/bash

# on est dans le répertoire du projet gitlab
# le nom de l'archive est construit à partir du nom du répertoire courant
dir=$(basename $(pwd))
archive="$dir".tgz

echo "L'archive de la livraison aura pour nom '$archive'"

echo "Pour livrer, fermez votre IDE puis taper « entrée »"
read x
echo "Nettoyage avant livraison : suppression des répertoires 'target'"
find . -type d -name "target" -exec rm -rf {} \; -print
echo "En cas d'utilisation de Git, effacer à la main le répertoire .git"
echo "Quand c'est fait, taper « entrée »"
read x

# l'archive est « le répertoire courant »
cd ..
echo "Taper « entrée » pour construction l'archive"
read x
tar cfz "$archive" "$dir"
cd $dir

echo "Archive ../$archive construite"
echo "Visualisation du contenu de l'archive (taper « entrée »)"
read x
tar tf ../"$archive"
echo
echo "Prêt pour livrer ../$archive"
echo

exit
