/* Section 1 : code utilisateur inclus en préambule de   */
/* la classe de l'analyseur lexical : package et import  */
package compil; // nom du paquetage à adapter
%%
/* Section 2 : directives, blocs, définitions régulières */
/* - des directives : "%..."                             */
/* - des blocs : "%...{"  ...  "%...}"                   */
/* - des définitions régulières : Nom = Regexp           */ 
%include Jflex.include
%include JflexCup.include

%{
/* code dans la classe de l'analyseur : attributs et     */
/* méthodes utiles pour les actions                      */
%}

%init{
/* code dans le constructeur : action initiale           */
%init}

%eof{
/* code en action finale à la sortie de l'analyseur      */
System.out.println("Fin de l'analyse lexicale.");
%eof}

// %caseless            /* confondre minuscules/majuscules   */
// %state   ETAT, ETAT2 /* États inclusifs du super-automate */
// %xstate  STATE       /* États exclusifs du super-automate */

WS         = [ \t\f] | \R
EOLComment = "//" .*
C89Comment = "/*" [^*]* ("*" ([^*/] [^*]*)?)* "*/"
Ignore     = {WS} | {EOLComment} | {C89Comment}

%%
/* Section 3 : règles lexicales sous la forme :              */
/* Rexexp  { /* Actions = code Java */ }                     */

//// Ignore, Ramasse Miette
{Ignore}           { }
[^]                { return TOKEN(MOT); }
//[^]                { WARN("Invalid char '" + yytext() + "'"); return TOKEN(error); }
