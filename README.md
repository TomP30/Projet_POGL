# Projet_POGL
####Abel Henry-Lapassat & Tom Pleskoff
LDD2-IM2

---

######    Description du Jeu :
        Dans ce Jeu les joueurs travailleront ensemble afin de collecter les 4 artefacts en utilisant les 
    clefs qu'ils trouveront. Et cela avant que l'Ile ne soit completement submergée ! 
        Il y a 4 types de cartes correspondant chacun a un Artefact (Fire, Wind, Wave, Stone), et un joueur
    doit récolter 4 clefs pour pouvoir essayer de récuperer un artefact.

---
>    Règles du jeu :
*      Chaque joueur possède 3 actions par tour :
        - Se déplacer d'une case
        - Assecher une Case adjacente
*      Il peut aussi à volonté donner des clefs à un autre joueur situé sur la meme case.
*      Un joueur peut decider aussi de ne rien faire.
*      A la fin du tour d'un joueur, des cases de l'Ile sont submergées :
        - Une case est soit, Assechée, Submergée, Noyée.
        - Si une case est Submergée, les joueurs peuvent encore s'y rendre et l'assécher.
        - Si une case est Noyée, les joueurs ne peuvent plus s'y rendre.
*      Les Joueurs ont perdus si : 
        - Un des artefacts se trouve sur une case noyée
        - L'Heliport est noyée
    
  * Précisions utiles : 
        Si un joueur est noyé, il peut se rendre sur une Case adjacente afin de continuer de jouer.
---

    Comment Jouer : 
*     Pour se deplacer -> Cliquer sur la case souhaitée ,le nombre d'actions utilisées sera déduit du nombre actuel.
*     Pour echanger une carte -> Utiliser le bouton d'échange puis sélectionner une clef en sa possession et pour 
      finir cliquer sur le joueur à qui on souhaite donner cette clef.
*     Pour assecher une case -> Utiliser le bouton d'assèchement puis sélectionner l'une des  cases proposées 
      afin de l'assécher.
*     Pour Prendre un artefact -> Il faut se situer sur une case contenant un Artefact et utiliser le bouton 
      "Claim", et si l'on possède 4 clefs du type correspondant à l'artefact, alors on le récupère.

---
    Presentation de l'interface de Jeu :

- La grille contenant les pions des joueurs ainsi que les artefacts est cliquable afin d'effectuer les actions des joueurs.
- Les 4 joueurs sont visibles et cliquables dans le coin superieur gauche, le joueur actif est entourée en blanc
- La main du joueur selectionné est affichée a gauche de la grille en dessous de l'affichage des joueurs.
- Les boutons "Claim" "Drain" "Exchange" "End Turn" permettant aux joueurs d'effectuer les différentes actions
sont en dessous de la grille.


