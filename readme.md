# Devoir Maison 1

Sommaire de ce fichier avec le barème à titre indicatif :
- 0. Binôme
- 1. État d'avancement
  - 1.1 Commentaires pour les évaluateurs
- 2. Énoncé
  - 2.1. Grammaire (/4)
  - 2.2. Question 1 : Analyse Syntaxique dite « sans AST » (/4 + 2)
  - 2.3. Question 2 : Analyse Syntaxique dite « avec AST » (/4)
  - 2.4. Question 3 : Visite
    - 2.4.1. Première visite (/4)
    - 2.4.2. Seconde visite (/2)
- Total = 4 + 4 + 2 + 4 + 4 + 2 = 20

## 0. Binôme

- Hassine Zaabar Azer
- Njeh Adam

## 1. État d'avancement

Cocher une case à cocher ([X]) lorsque la tâche est accomplie.

- [x] Récupérer le projet GitLabEnse `csc4251_4252-dm1`
  - Soit télécharger une archive depuis le site Web GitLabEnse.
  - Soit `git clone`.
- [x] Indiquer les noms et prénoms dans la section précédante.
- [x] Indiquer les noms et prénoms dans le champ `<artifactId>` du
     fichier `pom.xml`.
- [] Réaliser l'Analyse syntaxique (JFlex+CUP) première version,
     c'est-à-dire **SANS** la construction de l'AST à l'aide les
     classes du paquetage `compil.ast` fournies dans le squelette.
- [] Ajouter la construction de l'AST, c'est-à-dire **AVEC** la
     construction de l'arbre à l'aide des classes du paquetage
     `compil.ast`.
- [] Réaliser le visiteur.

**Pour livrer :**

1. Mettre à jour ce fichier (Section 1, et si nécessaire, Section 1.1).
2. Utiliser le script bash `livraison.sh`.
3. Déposer l'archive `../csc4251_4252-dm1.tgz` dans Moodle.

### 1.1. Commentaires pour les évaluateurs

TODO : utiliser cette section si nécessaire pour transmettre des
informations aux évaluateurs. Merci d'écrire en Markdown (la syntaxe
est très simple).

## 2. Énoncé

Réaliser (1) une analyse lexicale, (2) une analyse syntaxique, puis
(3) une visite de la grammaire qui suit. Les sections après la section
présentant la grammaire donnent des indications sur le travail à
réaliser.

### 2.1. Grammaire

```
ClasseList     ::= /*vide */
                |  (Class)+
Classe         ::= "class" Identificateur Parent "{" ClasseCorps "}" 
Identificateur ::= <IDENTIFICATEUR>
Parent         ::= ("extends" Identificateur)?
ClasseCorps    ::= /* vide */
                |  (Attribut|Méthode)+
Attributs      ::= /* vide */
                |  Attribut+
Attribut       ::= Type Identificateur ";"
Type           ::= "int"
                |  Identificateur
Méthode        ::= "public" Type Identificateur "(" Paramètres ")"
                   "{" Instructions "return" Expression ";" "}"
Paramètres     ::= /* vide */
                |  Type Identificateur            // défi : ("," Type Identificateur)*
Instructions   ::= /* vide */
                |  Instruction+
Instruction    ::= "System" "." "out" "." "println" "(" Expression ")" ";"
                |  Identificateur "=" Expression ";"
Expression     ::= <INTEGER_LITERAL>
                |  Identificateur
                |  "null"
                |  "new" Identificateur "(" ")"
```

### 2.2. Question 1 : Analyse Syntaxique dite « sans AST »

On commence par réaliser l'analyse syntaxique (JFlex+CUP) sans la
construction de l'AST, c'est-à-dire sans utiliser les classes du
paquetage `compil.ast` fournies dans le squelette.

La version du squelette copiée donne au départ la liste des mots. En
effet, l'exécution du test de la classe `TestFileCompiler` donne
l'affichage suivant lors de l'analyse du fichier
`src/test/resources/dm1.txt` :

```
Début de l'analyse syntaxique.
Fin de l'analyse lexicale.
= Token Debug
[Symbol: MOT (:1/1(1) - :1/2(2)), Symbol: MOT (:1/2(2) - :1/3(3)), [...] ]

Nombre de problèmes (intéressant si on fait de la gestion d'erreurs) : 0
```

Le résultat précédant ne comporte que l'analyse lexicale : dans la
classe du compilateur `Compiler`, les lignes 105 à 115 sont dans des
commentaires. Ces commentaires sont à retirer au fur et à mesure de
l'avancement du développement du compilateur.

Dans la grammaire, on remarque que les non-terminaux `Attribut` et
`Paramètre` utilisent des règles similaires commençant par `Type
Identificateur`. Avoir deux non-terminaux différents facilite la
construction du visiteur dans la dernière question, même si cela
ajoute un peu de travail, mais pas trop, dans la construction de l'AST
à la question suivante.

Par ailleurs, on a appliqué les simplifications suivantes pour garder
une grammaire simple :

- les déclarations des variables des méthodes sont sans définition
  (pas de valeur par défaut) ;
- les déclarations des variables des méthodes sont écrites avant les
  instructions (pas de mélange entre variables et instructions) ;
- les méthodes n'ont qu'un paramètre (en guise de défi, on en
  autorise plusieurs) ;
- les méthodes sont sans variable ;
- les seules instructions sont des instructions `System.out.println`
  et des affectations de variables ;
- parmi les types primitifs, seul le type entier est proposé, et
  lorsque le type n'est pas primitif, c'est une classe qui est
  spécifiée par son identificateur ;
- les expressions sont limitées : entier littéral, identificateur,
  "null", et l'instanciation d'objet.

En ce qui concerne l'analyse lexicale, on observe que seules deux
expressions régulières sont à ajouter : l'une pour les entiers et
l'autre pour les identificateurs. Par ailleurs, changer l'expression
pour le ramasse miette (`[^]`) afin de ne plus transmettre le *token*
`MOT`, c'est-à-dire utiliser plutôt l'expression qui a été mise en
commentaire avec l'appel à la méthode `WARN`.

À la fin de la réalisation de l'analyse lexicale, et sans retirer les
commentaires des lignes 105—115 de la classe `Compiler`, on obtient
une exécution similaire à la suivante lors l'analyse du fichier
`src/test/resources/dm1.txt` :

```
Début de l'analyse syntaxique.
Fin de l'analyse lexicale.
= Token Debug
[Symbol: CLASS (:1/1(1) - :1/6(6)), Symbol: IDENT (:1/7(7) - :1/12(12)), Symbol: LBRACE (:1/13(13) - :1/14(14)), Symbol: INT (:2/3(17) - :2/6(20)), Symbol: IDENT (:2/7(21) - :2/8(22)), Symbol: SEMI (:2/8(22) - :2/9(23)), [...] ]
Nombre de problèmes (intéressant si on fait de la gestion d'erreurs) : 0
```

En ce qui concerne l'analyse syntaxique, la grammaire est assez simple
pour ne pas requérir l'utilisation de directive `precedence` dans la
spécification CUP.

Par ailleurs, on suggère de commencer avec la règle
`axiome ::= classeList` en remplacement de la règle du squelette 
`axiome     ::= /* vide */ |  axiome motpoursquelette`.
Cela permet, à la question qui suit, d'utiliser la classe `Axiome` pour
la construction de l'AST, par exemple en écrivant :

```
axiome     ::= classeList:a
                 {: RESULT = new Axiome(a);
                    RESULT.addPosition(axleft, axright); :}
```

### 2.3. Question 2 : Analyse Syntaxique dite « avec AST »

On continue en ajoutant la construction de l'AST en utilisant les
classes du paquetage `compil.ast` fournies dans le squelette.

Dans cette question, créer les classes pour les non-terminaux ajoutés
dans la question précédante. Penser à utiliser la classe `AstList`
lorsque les nœuds enfants sont tous du même type, par exemple pour les
« liste de classes », « liste d'attributs », etc.

En ce qui concerne les types, faire la différence entre le type
primitif (entier) et le type classe. Remarquer que la classe
`compil.ast.Type` utilise dans son constructeur l'énumération
`compil.util.DMPrimitiveTypes` ainsi que la classe
`compil.util.DMTypeClass`.

Par ailleurs, dans les classes JAVA de l'AST, penser à ajouter des
attributs JAVA pour accéder directement aux nœuds enfants, ceci en vue
de la visite : par exemple, dans la classe JAVA `Classe` qui modélise
le concept de classe, ajouter un attribut pour l'identificateur de la
classe, un autre pour la liste des attributs, etc. Ensuite, dans les
constructeurs de ces classes JAVA, penser à affecter ces attributs aux
paramètres du constructeur en plus d'ajouter les nœuds enfants avec la
méthode `addFils`. Par exemple, pour la classe JAVA `Classe`, le
constructeur aura comme prototype la forme suivante :

```
Classe(Ident ident, « etc. », List<Attribut> attributs, « etc. »)
```

Le constructeur contiendra alors les instructions suivantes :

```
super(ident, « etc. »)          // ajout du nœud ident comme enfant
this.ident = ident;             // pour pouvoir y accéder « directement » dans le visiteur
...
for (Attribut attribut : attributs) { // parcours de la liste des attributs
  this.addFils(attribut);             // pour les ajouter en nœuds enfants
}
this.attributs = attributs;    // pour pouvoir y accéder « directement » dans le visiteur
...
```

À la fin de la réalisation de l'analyse syntaxique, retirer les
commentaires des lignes 106—108 et 110—114 de la classe `Compiler` pour
afficher l'AST. On obtient une exécution similaire à
la suivante lors de l'analyse du fichier
`src/test/resources/dm1.txt` :

```
Début de l'analyse syntaxique.
Fin de l'analyse lexicale.
= Token Debug
[Symbol: CLASS (:1/1(1) - :1/6(6)), Symbol: IDENT (:1/7(7) - :1/12(12)), [...]]
Analyse syntaxique , Axiome = Axiome[-24/2(382)]
Axiome[-24/2(382)]
|-Classe[1/1(1)-14/2(199)]
| |-Ident[1/7(7)-1/12(12)]
| |-Ident
| |-Attribut[2/3(17)-2/9(23)]
| | |-Type[2/3(17)-2/6(20)]
| | \-Ident[2/7(21)-2/8(22)]
...
```

Noter que le second nœud enfant `Ident` de l'affichage précédant
correspond à l'absence de « `"extends" Identificateur` » dans la
déclaration de la classe `Test1` du fichier en entrée, c'est-à-dire
que ce nœud `Ident` est un nœud enfant avec comme identificateur de la
classe parente l'identificateur « Object » (la classe parente de toute
classe) et que cette absence correspond à l'absence de position dans
le texte (pas de `[1/xx(xx)-1/yy(yy)]`).

### 2.4. Question 3 : Visite

On termine par la réalisation de deux visiteurs, le second étant un défi.

#### 2.4.1. Première visite

Le premier visiteur présente les attributs et les paramètres dans leur
contexte, la classe pour un attribut et la méthode pour un paramètre.
À titre d'exemple illustratif, l'analyse du fichier
`src/test/resources/dm1.txt` donne l'affichage qui suit :

```
Affichage des identificateurs dans leur portée : 
Test1 (classe) {
  a (attribut), 
  b (attribut), 
  calculer (méthode) {
    i (paramètre), 
  }
  un (méthode) {
  }
}
Test2 (classe) {
  attribut1 (attribut), 
  zero (méthode) {
    variable1 (paramètre), 
  }
}
```

Le squelette de la classe pour la première visite est déjà présent
dans le code : c'est la classe
`VisiteurAttributsParametresVariablesAvecPortee`. La fin du fichier
est à compléter avec les surcharges de la méthode `visit`.

Par ailleurs, utiliser les instructions
« `out.indent()` » et
« `out.outdent()` »
pour *beautifier* l'affichage avec des indentations lors de la visite.

Lorsque la classe de la visite est écrite, retirer le
commentaire de la ligne 109 de la classe `Compiler` pour
afficher le résultat de la visite. 

#### 2.4.2. Seconde visite

Dupliquer la classe de la première visite dans une nouvelle classe
`VisiteurAttributsParametresAvecPorteeEtReferenceClasse`. Créer cette
classe uniquement si la première visite est opérationnelle.

Le seconde visiteur ajoute les types des références, c'est-à-dire les
classes référencées. À titre d'exemple illustratif, l'analyse du
fichier `src/test/resources/dm1.txt` donne l'affichage qui suit :

```
Affichage des identificateurs dans leur portée et leur référence de classe : 
Test1 (classe) {
  a (attribut), 
  b (attribut), 
  calculer (méthode) {
    i (paramètre), 
  }
  un (méthode) {
  }
}
Test2 (classe) {
  attribut1 (réf classe `Test1') (attribut), 
  zero (méthode) {
    variable1 (réf classe `Test1') (paramètre), 
  }
}

Nombre de problèmes (intéressant si on fait de la gestion d'erreurs) : 0
```

**FIN de l'énoncé**
